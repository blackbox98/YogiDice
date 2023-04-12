package com.specialization.yogidice.controller;

import com.specialization.yogidice.dto.request.BoardGameCafeRequest;
import com.specialization.yogidice.dto.response.*;
import com.specialization.yogidice.service.BoardGameCafeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cafes")
@RequiredArgsConstructor
@Api(tags = {"보드게임 카페 API"})
public class BoardGameCafeController {
    private final BoardGameCafeService boardGameCafeService;

    // 보드게임 카페 추가
    @PostMapping
    @ApiOperation(value = "보드게임 카페 추가", notes = "보드게임 카페에 필요한 정보를 입력하고 추가합니다.")
    public ResponseEntity<?> createBoardGameCafe(
            @Valid @RequestBody BoardGameCafeRequest request
    ) {
        Long cafeId = boardGameCafeService.createBoardGameCafe(request);
        return ResponseEntity.status(HttpStatus.OK).body(BoardGameCafeCreateResponse.of(200, "Success", cafeId));
    }

    // 보드게임 카페 목록 조회
    @GetMapping
    @ApiOperation(value = "보드게임 카페 목록 조회", notes = "보드게임 카페 목록을 조회합니다.")
    public ResponseEntity<?> readBoardGameCafeList() {
        return ResponseEntity.status(HttpStatus.OK).body(BoardGameCafeListResponse.of(200, "Success", boardGameCafeService.readBoardGameCafeList()));
    }

    // 보드게임 카페 상세 조회
    @GetMapping("/{cafeId}")
    @ApiOperation(value = "보드게임 카페 상세 조회", notes = "보드게임 카페를 상세 조회합니다.")
    public ResponseEntity<?> readBoardGameCafe(
            @PathVariable Long cafeId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(BoardGameCafeDetailResponse.of(200, "Success", boardGameCafeService.readBoardGameCafe(cafeId)));
    }

    // 보드게임 카페 수정
    @PutMapping("/{cafeId}")
    @ApiOperation(value = "보드게임 카페 정보 수정", notes = "보드게임 카페 정보를 수정합니다.")
    public ResponseEntity<?> updateBoardGameCafe(
            @PathVariable Long cafeId,
            @Valid @RequestBody BoardGameCafeRequest request
    ) {
        boardGameCafeService.updateBoardGameCafe(cafeId, request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 보드게임 카페 삭제
    @DeleteMapping("/{cafeId}")
    @ApiOperation(value = "보드게임 카페 삭제", notes = "보드게임 카페를 삭제합니다.")
    public ResponseEntity<?> deleteBoardGameCafe(
            @PathVariable Long cafeId
    ) {
        boardGameCafeService.deleteBoardGameCafe(cafeId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }
}
