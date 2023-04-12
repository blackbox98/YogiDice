package com.specialization.yogidice.controller;

import com.specialization.yogidice.dto.request.CafeGameListCreateRequest;
import com.specialization.yogidice.dto.request.CafeGameListUpdateRequest;
import com.specialization.yogidice.dto.response.BaseResponseBody;
import com.specialization.yogidice.dto.response.CafeGameCreateResponse;
import com.specialization.yogidice.dto.response.CafeGameListResponse;
import com.specialization.yogidice.service.CafeGameListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cafegamelists")
@RequiredArgsConstructor
@Api(tags = {"보드게임 카페 보유 보드게임 목록 API"})
public class CafeGameListController {
    private final CafeGameListService cafeGameListService;

    // 보드게임 카페 보유 보드게임 목록 추가
    @PostMapping
    @ApiOperation(value = "보드게임 카페 보유 보드게임 목록 추가", notes = "보드게임 카페 보유 보드게임 목록에 필요한 정보를 입력하고 추가합니다.")
    public ResponseEntity<?> createCafeGameList(
            @Valid @RequestBody CafeGameListCreateRequest request
    ) {
        Long gameListId = cafeGameListService.createCafeGameList(request);
        return ResponseEntity.status(HttpStatus.OK).body(CafeGameCreateResponse.of(200, "Success", gameListId));
    }

    // 보드게임 카페를 통한 보드게임 카페 보유 보드게임 목록 조회
    @GetMapping("/cafe/{cafeId}")
    @ApiOperation(value = "보드게임 카페를 통한 보드게임 카페 보유 보드게임 목록 조회", notes = "보드게임 카페를 통해 보드게임 카페 보유 보드게임 목록 조회을 조회합니다.")
    public ResponseEntity<?> readCafeGameListOfBoardGameCafe(
            @PathVariable Long cafeId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CafeGameListResponse.of(200, "Success", cafeGameListService.readCafeGameListOfBoardGameCafe(cafeId)));
    }

    // 보드게임을 통한 보드게임 카페 보유 보드게임 목록 조회
    @GetMapping("/game/{gameId}")
    @ApiOperation(value = "보드게임을 통한 보드게임 카페 보유 보드게임 목록 조회", notes = "보드게임을 통해 보드게임 카페 보유 보드게임 목록 조회을 조회합니다.")
    public ResponseEntity<?> readCafeGameListOfBoardGame(
            @PathVariable Long gameId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CafeGameListResponse.of(200, "Success", cafeGameListService.readCafeGameListOfBoardGame(gameId)));
    }

    // 보드게임 카페 보유 보드게임 목록 수정
    @PutMapping("/{gameListId}")
    @ApiOperation(value = "보드게임 카페 보유 보드게임 목록 수정", notes = "보드게임 카페 보유 보드게임 목록의 정보를 수정합니다.")
    public ResponseEntity<?> updateCafeGameList(
            @PathVariable Long gameListId,
            @Valid @RequestBody CafeGameListUpdateRequest request
    ) {
        cafeGameListService.updateCafeGameList(gameListId, request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 보드게임 카페 보유 보드게임 목록 삭제
    @DeleteMapping("/{gameListId}")
    @ApiOperation(value = "보드게임 카페 보유 보드게임 목록 삭제", notes = "보드게임 카페 보유 보드게임 목록을 삭제합니다.")
    public ResponseEntity<?> deleteCafeGameList(
            @PathVariable Long gameListId
    ) {
        cafeGameListService.deleteCafeGameList(gameListId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping("/cafe/address/{address}")
    @ApiOperation(value = "보드게임 카페를 통한 보드게임 카페 보유 보드게임 목록 조회", notes = "보드게임 카페를 통해 보드게임 카페 보유 보드게임 목록 조회을 조회합니다.")
    public ResponseEntity<?> readCafeGameListOfBoardGameCafeByAddress(
            @PathVariable String address
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CafeGameListResponse.of(200, "Success", cafeGameListService.readCafeGameListOfBoardGameCafeByAddress(address)));
    }
}
