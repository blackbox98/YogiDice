package com.specialization.yogidice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.specialization.yogidice.common.config.web.LoginUser;
import com.specialization.yogidice.domain.entity.User;
import com.specialization.yogidice.domain.entity.type.Reviewed;
import com.specialization.yogidice.dto.request.BoardGamePickRequest;
import com.specialization.yogidice.dto.request.BoardGameRequest;
import com.specialization.yogidice.dto.request.NumOfReviewRequest;
import com.specialization.yogidice.dto.response.*;
import com.specialization.yogidice.service.BoardGameService;
import com.specialization.yogidice.service.BookmarkService;
import com.specialization.yogidice.service.HistoryService;
import com.specialization.yogidice.service.NumOfReviewService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
@Api(tags = {"보드게임 API"})
public class BoardGameController {
    private final BoardGameService boardGameService;
    private final NumOfReviewService numOfReviewService;

    private final HistoryService historyService;
    private final BookmarkService bookmarkService;

    // 보드게임 추가
    @PostMapping
    @ApiOperation(value = "보드게임 추가", notes = "보드게임에 필요한 정보를 입력하고 추가합니다.")
    public ResponseEntity<?> createBoardGame(
            @Valid @RequestBody BoardGameRequest request
    ) {
        Long gameId = boardGameService.createBoardGame(request);
        NumOfReviewRequest numOfReviewRequest = new NumOfReviewRequest(gameId);
        numOfReviewService.createNumOfReview(numOfReviewRequest);
        return ResponseEntity.status(HttpStatus.OK).body(BoardGameCreateResponse.of(200, "Success", gameId));
    }

    // 보드게임 목록 조회
    @GetMapping
    @ApiOperation(value = "보드게임 목록 조회", notes = "보드게임 목록을 조회합니다.")
    public ResponseEntity<?> readBoardGameList(@PageableDefault(size = 30) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(BoardGameListResponse.of(200, "Success", boardGameService.readBoardGameList(pageable)));
    }

    // 보드게임 상세 조회
    @GetMapping("/{gameId}")
    @ApiOperation(value = "보드게임 상세 조회", notes = "보드게임을 상세 조회합니다.")
    public ResponseEntity<?> readBoardGame(
            @PathVariable Long gameId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(BoardGameDetailResponse.of(200, "Success", boardGameService.readBoardGame(gameId)));
    }

    // 보드게임 정보 수정
    @PutMapping("/{gameId}")
    @ApiOperation(value = "보드게임 정보 수정", notes = "보드게임의 정보를 수정합니다.")
    public ResponseEntity<?> updateBoardGame(
            @PathVariable Long gameId,
            @Valid @RequestBody BoardGameRequest request
    ) {
        boardGameService.updateBoardGame(gameId, request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 보드게임 삭제
    @DeleteMapping("/{gameId}")
    @ApiOperation(value = "보드게임 삭제", notes = "보드게임을 삭제합니다.")
    public ResponseEntity<?> deleteBoardGame(
            @PathVariable Long gameId
    ) {
        boardGameService.deleteBoardGame(gameId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 상위 10개 게임 리스트 조회 (평점 순)
    @GetMapping("/top10/rating")
    @ApiOperation(value = "보드게임 상위 10개 게임 리스트 조회 (평점 순)", notes = "상위 10개 게임 리스트를 평점 순으로 조회합니다.")
    public ResponseEntity<?> readTop10ListByRatingUser() {
        return ResponseEntity.status(HttpStatus.OK).body(RatingGameListResponse.of(200, "Success", boardGameService.readTop10ListByRatingUser()));
    }

    // 평점 순 전체 보드게임 리스트 조회
    @GetMapping("/rating")
    @ApiOperation(value = "평점 순 전체 보드게임 리스트 조회", notes = "평점 순 전체 보드게임 리스트를 조회합니다.")
    public ResponseEntity<?> readAllListByRatingUser(@PageableDefault(size = 30) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(RatingGameListResponse.of(200, "Success", boardGameService.readAllListByRatingUser(pageable)));
    }

    // 상위 10개 게임 리스트 조회 (최신 게임 순)
    @GetMapping("/top10/recent")
    @ApiOperation(value = "보드게임 상위 10개 게임 리스트 조회 (최신 게임 순)", notes = "상위 10개 게임 리스트를 최신 게임 순으로 조회합니다.")
    public ResponseEntity<?> readTop10ListByPublishYear() {
        return ResponseEntity.status(HttpStatus.OK).body(RecentGameListResponse.of(200, "Success", boardGameService.readTop10ListByPublishYear()));
    }

    // 최신 게임 순 전체 보드게임 리스트 조회
    @GetMapping("/recent")
    @ApiOperation(value = "최신 게임 순 전체 보드게임 리스트 조회", notes = "최신 게임 순 전체 보드게임 리스트를 조회합니다.")
    public ResponseEntity<?> readAllListByPublishYear(@PageableDefault(size = 30) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(RecentGameListResponse.of(200, "Success", boardGameService.readAllListByPublishYear(pageable)));
    }

    // 상위 10개 게임 리스트 조회 (리뷰 많은 순)
    @GetMapping("/top10/review")
    @ApiOperation(value = "보드게임 상위 10개 게임 리스트 조회 (리뷰 많은 순)", notes = "상위 10개 게임 리스트를 리뷰가 많은 순으로 조회합니다.")
    public ResponseEntity<?> readTop10ListByNumOfReview() {
        return ResponseEntity.status(HttpStatus.OK).body(NumOfReviewListResponse.of(200, "Success", numOfReviewService.readTop10ListByNumOfReview()));
    }

    // 리뷰 많은 순 전체 보드게임 리스트 조회
    @GetMapping("/review")
    @ApiOperation(value = "리뷰 많은 순 전체 보드게임 리스트 조회", notes = "리뷰 많은 순 전체 보드게임 리스트를 조회합니다.")
    public ResponseEntity<?> readAllListByNumOfReview(@PageableDefault(size = 30) Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(NumOfReviewListResponse.of(200, "Success", numOfReviewService.readAllListByNumOfReview(pageable)));
    }

    // 보드게임 확장판 리스트 조회
    @GetMapping("/extend/{gameId}")
    @ApiOperation(value = "보드게임 확장판 리스트 조회", notes = "보드게임 확장판 리스트를 조회합니다.")
    public ResponseEntity<?> readExtendedEditionGameList(
            @PathVariable Long gameId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(BoardGameListResponse.of(200, "Success", boardGameService.readExtendedEditionGameList(gameId)));
    }

    @PostMapping("/recommend")
    @ApiOperation(value = "pick 추천", notes = "질문 답변에 따른 추천 보드게임을 분석합니다.")
    public ResponseEntity<?> pickRecommend(@RequestBody BoardGamePickRequest boardGamePickRequest) {
        return ResponseEntity.status(HttpStatus.OK).body(BoardGameListResponse.of(200, "Success", boardGameService.readPickBoardGame(boardGamePickRequest)));
    }


    @GetMapping("/recommend/play/{gameId}")
    @ApiOperation(value = "게임이 끝난 후 다음 게임 추천", notes = "게임이 끝난 후 연관된 다음 게임을 추천합니다.")
    public ResponseEntity<?> nextRecommend(
            @ApiIgnore @LoginUser User user,
            @PathVariable Long gameId
    ) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8000/analyze/recommend/play";

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", user.getId());
        jsonObject.put("gameId", gameId);

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        String boardGameList = restTemplate.postForObject(url, request, String.class);

        return ResponseEntity.status(HttpStatus.OK).body(JsonResponse.of(200, "Success", boardGameList));
    }

    @GetMapping("/recommend/main")
    @ApiOperation(value = "메인 추천", notes = "메인 페이지에서 게임을 추천합니다.")
    public ResponseEntity<?> mainRecommend(
            @ApiIgnore @LoginUser User user
    ) {
        //리뷰를 했다면
        if (user.getReviewed() == Reviewed.T) {
            if (boardGameService.mainRecommend(user.getId()).size() == 0) {
                List<BookmarkResponse> bookmarkResponses = bookmarkService.readBookmarkListOfUser(user.getId());
                if (!bookmarkResponses.isEmpty()) {
                    try {
                        List<BoardGameSimpleResponse> boardGames = boardGameService.recommendByBookmark(bookmarkResponses);
                        return ResponseEntity.status(HttpStatus.OK).body(BoardGameSimpleListResponse.of(200, "Success", boardGames));
                    } catch (JsonProcessingException e) {
                        return ResponseEntity.status(HttpStatus.OK).body(JsonResponse.of(400, "No data", ""));
                    }
                } else {
                    return ResponseEntity.status(HttpStatus.OK).body(BoardGameListResponse.of(200, "Success", boardGameService.mainRecommend(user.getId())));
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(BoardGameListResponse.of(200, "Success", boardGameService.mainRecommend(user.getId())));
        } else {
            //리뷰를 하지 않았다면
            List<BookmarkResponse> bookmarkResponses = bookmarkService.readBookmarkListOfUser(user.getId());
            if (!bookmarkResponses.isEmpty()) {
                try {
                    List<BoardGameSimpleResponse> boardGames = boardGameService.recommendByBookmark(bookmarkResponses);
                    return ResponseEntity.status(HttpStatus.OK).body(BoardGameSimpleListResponse.of(200, "Success", boardGames));
                } catch (JsonProcessingException e) {
                    return ResponseEntity.status(HttpStatus.OK).body(JsonResponse.of(400, "No data", ""));
                }
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(BoardGameListResponse.of(200, "Success", boardGameService.readTop10ListByBoardGameLife()));
            }
        }
    }

    @GetMapping("/recommend/detail/{gameId}")
    @ApiOperation(value = "보드게임 상세 페이지 하단 추천", notes = "보드게임 상세 페이지 하단에서 보드게임을 추천합니다.")
    public ResponseEntity<?> detailRecommend(
            @PathVariable Long gameId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://172.18.0.1:8000/analyze/recommend/detail/" + gameId;
//        String url = "http://localhost:8000/analyze/recommend/detail/"+gameId;  //로컬에서

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("gameId", gameId);

        HttpEntity<String> request = new HttpEntity<>(jsonObject.toString(), httpHeaders);
        String boardGameList = restTemplate.getForObject(url, String.class);
        try {
            Map<String, Object> mapping = new ObjectMapper().readValue(boardGameList, HashMap.class);
            Map<Integer, Long> boardMap = new HashMap<>();
            for (String key : mapping.keySet()) {
                boardMap.put(Integer.parseInt(key), Long.parseLong((String) mapping.get(key)));
            }
            List<Long> boardGameIds = new ArrayList<>(boardMap.values());
            for (Integer key : boardMap.keySet()) {
                boardGameIds.add(boardMap.get(key));
            }
            List<BoardGameSimpleResponse> boardGames = boardGameService.detailRecommend(boardGameIds);

            return ResponseEntity.status(HttpStatus.OK).body(BoardGameSimpleListResponse.of(200, "Success", boardGames));
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(HttpStatus.OK).body(JsonResponse.of(400, "No data", ""));
        }
    }

    @GetMapping("/search/{title}")
    @ApiOperation(value = "보드게임 이름으로 검색", notes = "보드게임 타이틀로 게임을 검색합니다.")
    public ResponseEntity<?> searchBoardGame(
            @PathVariable String title) {
        return ResponseEntity.status(HttpStatus.OK).body(BoardGameListResponse.of(200, "Success", boardGameService.searchBoardGame(title)));

    }

    @GetMapping("reviewAll/{gameId}")
    @ApiOperation(value = "보드게임 리뷰 조회", notes = "보드게임 아이디로 리뷰를 조회합니다")
    public ResponseEntity<?> readReviewAll(
            @PathVariable Long gameId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(HistoryListResponse.of(200, "Success", historyService.readHistoryListById(gameId)));
    }

    @GetMapping("/chemi/{gameId}")
    @ApiOperation(value = "사용자와 게임의 케미 조사", notes = "메카니즘을 기반으로 사용자가 이 게임을 얼마나 좋아할 지 계산해줍니다.")
    public ResponseEntity<?> anlayzeChemi(
            @ApiIgnore @LoginUser User user,
            @PathVariable Long gameId
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(ChemiResponse.of(200, "success", boardGameService.analyzeChemi(user, gameId)));
    }
}