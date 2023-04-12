package com.specialization.yogidice.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialization.yogidice.common.config.web.LoginUser;
import com.specialization.yogidice.domain.entity.User;
import com.specialization.yogidice.dto.request.*;
import com.specialization.yogidice.dto.response.*;
import com.specialization.yogidice.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * 유저 관련 API 요청 처리를 위한 컨트롤러 정의.
 */

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Api(tags = {"유저 API"})
public class UserController {
    private final UserService userService;
    private final BookmarkService bookmarkService;
    private final HistoryService historyService;
    private final RecommendService recommendService;


    // 카카오 계정 정보 가져오기
    @GetMapping("/callback")
    @ApiOperation(value = "카카오 계정 정보 가져오기", notes = "카카오 계정 정보를 가져옵니다.")
    public ResponseEntity<?> getUserInfo(
            @RequestParam String code
    ) throws JsonProcessingException {
        String accessToken = userService.getAccessToken(code);
        KakaoUserResponse kakaoUserInfo = userService.getUserInfo(accessToken);
        return ResponseEntity.status(HttpStatus.OK).body(KakaoUserResponse.of(200, "Success", kakaoUserInfo));
    }

    @PostMapping("/check")
    @ApiOperation(value = "카카오 계정의 회원가입 여부 확인", notes = "카카오 계정의 회원가입 여부를 확인합니다.")
    public ResponseEntity<?> checkUser(
            @Valid @RequestBody KakaoUserResponse kakaoUser
    ) {
        User user = userService.checkUser(kakaoUser);
        UserRegistResponse response = new UserRegistResponse();
        // user가 null이면 false(회원가입 필요), null이 아니면 true(이미 회원가입된 유저)
        response.setRegisted(user != null);
        return ResponseEntity.status(HttpStatus.OK).body(UserRegistResponse.of(200, "Success", response));
    }

    // 카카오 회원가입 후 로그인
    @PostMapping(path = "/regist")
    @ApiOperation(value = "카카오 계정 회원가입 후 로그인", notes = "카카오 계정으로 회원가입 후 로그인합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> kakaoLogin(
            @Valid @RequestBody UserCreateRequest request
    ) {
        User user = userService.registUser(request);
        String authentication = userService.LoginUser(user);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, authentication)
                .build();
    }

    // 카카오 로그인
    @PostMapping("/login")
    @ApiOperation(value = "카카오 로그인", notes = "카카오 계정으로 로그인합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> kakaoLogin(
            @Valid @RequestBody KakaoUserResponse kakaoUser
    ) {
        User user = userService.findUser(kakaoUser);
        String authentication = userService.LoginUser(user);
        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.AUTHORIZATION, authentication)
                .build();
    }

    @GetMapping
    @ApiOperation(value = "회원 정보 조회", notes = "회원의 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> readUser(
            @ApiIgnore @LoginUser User user
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(UserDetailResponse.of(200, "Success", userService.readUser(user.getId())));
    }

    // 회원 정보 수정
    @PutMapping
    @ApiOperation(value = "회원 정보 수정", notes = "회원의 정보를 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> updateUser(
            @ApiIgnore @LoginUser User user,
            @Valid @RequestBody UserUpdateRequest request
    ) {
        userService.updateUser(user.getId(), request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 회원 탈퇴
    @DeleteMapping
    @ApiOperation(value = "회원 탈퇴", notes = "회원의 정보를 삭제하고 회원을 탈퇴합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteUser(
            @ApiIgnore @LoginUser User user
    ) {
        historyService.deleteHistoryByUser(user.getId());
        userService.deleteUser(user.getId());
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 북마크 추가
    @PostMapping("/bookmark")
    @ApiOperation(value = "북마크 추가", notes = "북마크를 추가합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> createBookmark(
            @ApiIgnore @LoginUser User user,
            @Valid @RequestBody BookmarkRequest request
    ) {
        Long bookmarkId = bookmarkService.createBookmark(user.getId(), request);
        return ResponseEntity.status(HttpStatus.OK).body(BookmarkCreateResponse.of(200, "Success", bookmarkId));
    }

    // 북마크 목록 조회
    @GetMapping("/bookmark")
    @ApiOperation(value = "북마크 목록 조회", notes = "북마크 목록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> readBookmark(
            @ApiIgnore @LoginUser User user
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(BookmarkListResponse.of(200, "Success", bookmarkService.readBookmarkListOfUser(user.getId())));
    }

    // 북마크 삭제
    @DeleteMapping("/bookmark/{gameId}")
    @ApiOperation(value = "북마크 삭제", notes = "북마크를 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteBookmark(
            @ApiIgnore @LoginUser User user,
            @PathVariable Long gameId
    ) {
        bookmarkService.deleteBookmark(user.getId(), gameId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    @PostMapping("/history")
    @ApiOperation(value = "게임 기록 추가", notes = "게임 기록을 추가합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> createHistory(
            @ApiIgnore @LoginUser User user,
            @Valid @RequestBody HistoryCreateRequest request
    ) {
        Long historyId = historyService.createHistory(user.getId(), request);
        return ResponseEntity.status(HttpStatus.OK).body(HistoryCreateResponse.of(200, "Success", historyId));
    }

    @GetMapping("/history")
    @ApiOperation(value = "게임 기록 조회", notes = "게임 기록을 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> readHistory(
            @ApiIgnore @LoginUser User user
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(HistoryDetailListResponse.of(200, "Success", historyService.readHistoryListOfUser(user.getId())));
    }

    @GetMapping("/history/{gameId}")
    @ApiOperation(value = "게임 기록 상세 조회", notes = "게임 기록을 상세 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> readHistoryDetail(
            @ApiIgnore @LoginUser User user,
            @PathVariable Long gameId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(HistoryListResponse.of(200, "Success", historyService.readHistoryDetail(user.getId(), gameId)));
    }

    @PutMapping("/history/{historyId}")
    @ApiOperation(value = "게임 기록 수정(평점 및 리뷰)", notes = "게임 기록(평점 및 리뷰)을 수정합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> updateHistory(
            @ApiIgnore @LoginUser User user,
            @PathVariable Long historyId,
            @Valid @RequestBody HistoryUpdateRequest request
    ) {
        historyService.updateHistory(user, historyId, request);
        //장고로부터 추천 받기
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://172.18.0.1:8000/analyze/recommend/"+user.getId();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", user.getId());

        String boardGameList = restTemplate.getForObject(url, String.class);


        try {
            Map<String, ArrayList<String>> mapping = new ObjectMapper().readValue(boardGameList, HashMap.class);

            ArrayList<String> list = mapping.get("game");
            ArrayList<Long> boardGameIds = new ArrayList<>();
            for(String id : list){
                boardGameIds.add(Long.parseLong(id));
            }
            //추천 받아서 아이디 리스트 받기 완료
            //유저 아이디 기반으로 삭제
            recommendService.updateRecommend(user.getId(), boardGameIds);

        }catch (JsonProcessingException e){
            return ResponseEntity.status(HttpStatus.OK).body(JsonResponse.of(400, "No data", ""));
        }

        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    @DeleteMapping("/history/{historyId}")
    @ApiOperation(value = "게임 기록 삭제", notes = "게임 기록을 삭제합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> deleteHistory(
            @ApiIgnore @LoginUser User user,
            @PathVariable Long historyId
    ) {
        historyService.deleteHistory(historyId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/review")
    @ApiOperation(value = "리뷰 유무 조회", notes = "리뷰 유무를 조회합니다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<?> checkReview(
            @ApiIgnore @LoginUser User user
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(UserReviewedResponse.of(200, "Success", userService.checkReview(user.getId())));
    }
}