package com.specialization.yogidice.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialization.yogidice.common.config.security.util.JwtUtil;
import com.specialization.yogidice.common.exception.NotFoundException;
import com.specialization.yogidice.domain.entity.Bookmark;
import com.specialization.yogidice.domain.entity.User;
import com.specialization.yogidice.domain.entity.type.Reviewed;
import com.specialization.yogidice.domain.repository.BookmarkRepository;
import com.specialization.yogidice.domain.repository.HistoryRepository;
import com.specialization.yogidice.domain.repository.UserRepository;
import com.specialization.yogidice.domain.repository.category.MechanismGroupRepository;
import com.specialization.yogidice.dto.request.UserCreateRequest;
import com.specialization.yogidice.dto.request.UserUpdateRequest;
import com.specialization.yogidice.dto.response.BookmarkResponse;
import com.specialization.yogidice.dto.response.HistoryResponse;
import com.specialization.yogidice.dto.response.KakaoUserResponse;
import com.specialization.yogidice.dto.response.UserResponse;
import com.specialization.yogidice.dto.response.category.MechanismGroupResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.specialization.yogidice.common.exception.NotFoundException.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;
    private final BookmarkRepository bookmarkRepository;
    private final HistoryRepository historyRepository;
    private final MechanismGroupRepository mechanismGroupRepository;
    private final JwtUtil jwtUtil;

    @Value("${api-key.kakao-rest-api}")
    private String kakaoRestApiKey;

    // 카카오 "인가 코드"로 "액세스 토큰" 요청
    public String getAccessToken(String code) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP Body 생성
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", kakaoRestApiKey);
        body.add("redirect_uri", "https://yogidice.site/kakaologin");
        body.add("code", code);

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(body, headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        // HTTP 응답 (JSON) -> 액세스 토큰 파싱
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("access_token").asText();
    }

    // 토큰으로 카카오 API 호출
    public KakaoUserResponse getUserInfo(String accessToken) throws JsonProcessingException {
        // HTTP Header 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HTTP 요청 보내기
        HttpEntity<MultiValueMap<String, String>> kakaoUserInfoRequest = new HttpEntity<>(headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoUserInfoRequest,
                String.class
        );

        // responseBody에 있는 정보를 꺼냄
        String responseBody = response.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(responseBody);

        String kakaoId = jsonNode.get("kakao_account").get("email").asText();
        String nickName = jsonNode.get("kakao_account").get("profile").get("nickname").asText();
        return new KakaoUserResponse(kakaoId, nickName);
    }

    // 이미 가입된 카카오 계정인지 확인
    @Transactional
    public User checkUser(KakaoUserResponse response) {
        return userRepository.findByKakaoId(response.getKakaoId())
                .orElse(null);
    }

    // 카카오 계정으로 회원가입
    @Transactional
    public User registUser(UserCreateRequest request) {
        User user = User.create(request.getKakaoId(), request.getNickName());
        userRepository.save(user);
        return user;
    }

    // 로그인 처리 및 토큰 발급
    public String LoginUser(User user) {
        return jwtUtil.createToken(user.getId());
    }

    // 로그인을 위한 회원 조회
    public User findUser(KakaoUserResponse kakaoUser) {
        return userRepository.findByKakaoId(kakaoUser.getKakaoId())
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
    }

    // 회원 정보 조회
    @Transactional
    public UserResponse readUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        List<HistoryResponse> historyResponses = historyRepository.findByUser(user).stream()
                .map(HistoryResponse::response)
                .collect(Collectors.toList());
        List<Bookmark> bookmarks = bookmarkRepository.findByUser(user);
        if (bookmarks.isEmpty()) {
            return UserResponse.response(user, historyResponses, new ArrayList<>());
        }
        List<BookmarkResponse> bookmarkResponses = new ArrayList<>();
        for (Bookmark bookmark : bookmarks) {
            List<MechanismGroupResponse> mechanismGroupResponses = mechanismGroupRepository.findByBoardGame(bookmark.getBoardGame()).stream()
                    .map(MechanismGroupResponse::response)
                    .collect(Collectors.toList());
            bookmarkResponses.add(BookmarkResponse.response(bookmark, mechanismGroupResponses));
        }
        return UserResponse.response(user, historyResponses, bookmarkResponses);
    }

    // 리뷰 여부 조회
    @Transactional
    public Reviewed checkReview(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        if (user.getReviewed().equals(Reviewed.T)) {
            return Reviewed.T;
        }
        return Reviewed.F;
    }

    // 회원 정보 수정
    @Transactional
    public void updateUser(Long userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        user.update(
                request.getNickName()
        );
        userRepository.save(user);
    }

    // 회원 탈퇴
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
