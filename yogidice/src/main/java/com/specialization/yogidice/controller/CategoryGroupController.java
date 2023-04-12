package com.specialization.yogidice.controller;

import com.specialization.yogidice.dto.request.category.*;
import com.specialization.yogidice.dto.response.BaseResponseBody;
import com.specialization.yogidice.dto.response.category.*;
import com.specialization.yogidice.service.category.CategoryGroupService;
import com.specialization.yogidice.service.category.MechanismGroupService;
import com.specialization.yogidice.service.category.TypeGroupService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categorygroups")
@RequiredArgsConstructor
@Api(tags = {"카테고리 목록 API"})
public class CategoryGroupController {
    private final CategoryGroupService categoryGroupService;
    private final TypeGroupService typeGroupService;
    private final MechanismGroupService mechanismGroupService;

    // 카테고리(테마) 추가
    @PostMapping("/theme")
    @ApiOperation(value = "카테고리(테마) 목록 추가", notes = "카테고리(테마) 목록에 필요한 정보를 입력하고 추가합니다.")
    public ResponseEntity<?> createCategoryGroup(
            @Valid @RequestBody CategoryGroupCreateRequest request
    ) {
        Long cateGroupId = categoryGroupService.createCategoryGroup(request);
        return ResponseEntity.status(HttpStatus.OK).body(CategoryGroupCreateResponse.of(200, "Success", cateGroupId));
    }

    // 보드게임을 통한 카테고리(테마) 목록 조회
    @GetMapping("/theme/game/{gameId}")
    @ApiOperation(value = "보드게임을 통한 카테고리(테마) 목록 조회", notes = "보드게임을 통해 카테고리(테마) 목록을 조회합니다.")
    public ResponseEntity<?> readCategoryGroupListOfBoardGame(
            @PathVariable Long gameId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CategoryGroupListResponse.of(200, "Success", categoryGroupService.readCategoryGroupListOfBoardGame(gameId)));
    }

    // 카테고리를 통한 카테고리(테마) 목록 조회
    @GetMapping("/theme/cate/{cateId}")
    @ApiOperation(value = "카테고리를 통한 카테고리(테마) 목록 조회", notes = "카테고리를 통해 카테고리(테마) 목록을 조회합니다.")
    public ResponseEntity<?> readCategoryGroupListOfCategory(
            @PathVariable Long cateId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CategoryGroupListResponse.of(200, "Success", categoryGroupService.readCategoryGroupListOfCategory(cateId)));
    }

    // 카테고리(테마) 목록 수정
    @PutMapping("/theme/{cateGroupId}")
    @ApiOperation(value = "카테고리(테마) 목록 정보 수정", notes = "카테고리(테마) 목록의 정보를 수정합니다.")
    public ResponseEntity<?> updateCategoryGroup(
            @PathVariable Long cateGroupId,
            @Valid @RequestBody CategoryGroupUpdateRequest request
    ) {
        categoryGroupService.updateCategoryGroup(cateGroupId, request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 카테고리(테마) 목록 삭제
    @DeleteMapping("/theme/{cateGroupId}")
    @ApiOperation(value = "카테고리(테마) 목록 삭제", notes = "카테고리(테마) 목록을 삭제합니다.")
    public ResponseEntity<?> deleteCategoryGroup(
            @PathVariable Long cateGroupId
    ) {
        categoryGroupService.deleteCategoryGroup(cateGroupId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 타입 목록 추가
    @PostMapping("/type")
    @ApiOperation(value = "타입 목록 추가", notes = "타입 목록에 필요한 정보를 입력하고 추가합니다.")
    public ResponseEntity<?> createTypeGroup(
            @Valid @RequestBody TypeGroupCreateRequest request
    ) {
        Long typeGroupId = typeGroupService.createTypeGroup(request);
        return ResponseEntity.status(HttpStatus.OK).body(TypeGroupCreateResponse.of(200, "Success", typeGroupId));
    }

    // 보드게임을 통한 타입 목록 조회
    @GetMapping("/type/game/{gameId}")
    @ApiOperation(value = "보드게임을 통한 타입 목록 조회", notes = "보드게임을 통해 타입 목록을 조회합니다.")
    public ResponseEntity<?> readTypeGroupListOfBoardGame(
            @PathVariable Long gameId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(TypeGroupListResponse.of(200, "Success", typeGroupService.readTypeGroupListOfBoardGame(gameId)));
    }

    // 타입을 통한 타입 목록 조회
    @GetMapping("/type/cate/{typeId}")
    @ApiOperation(value = "타입을 통한 타입 목록 조회", notes = "타입을 통해 타입 목록을 조회합니다.")
    public ResponseEntity<?> readTypeGroupListOfType(
            @PathVariable Byte typeId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(TypeGroupListResponse.of(200, "Success", typeGroupService.readTypeGroupListOfType(typeId)));
    }

    // 타입 목록 수정
    @PutMapping("/type/{typeGroupId}")
    @ApiOperation(value = "타입 목록 정보 수정", notes = "타입 목록의 정보를 수정합니다.")
    public ResponseEntity<?> updateTypeGroup(
            @PathVariable Long typeGroupId,
            @Valid @RequestBody TypeGroupUpdateRequest request
    ) {
        typeGroupService.updateTypeGroup(typeGroupId, request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 타입 목록 삭제
    @DeleteMapping("/type/{typeGroupId}")
    @ApiOperation(value = "타입 목록 삭제", notes = "타입 목록을 삭제합니다.")
    public ResponseEntity<?> deleteTypeGroup(
            @PathVariable Long typeGroupId
    ) {
        typeGroupService.deleteTypeGroup(typeGroupId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 진행방식 목록 추가
    @PostMapping("/mechanism")
    @ApiOperation(value = "진행방식 목록 추가", notes = "진행방식 목록에 필요한 정보를 입력하고 추가합니다.")
    public ResponseEntity<?> createMechanismGroup(
            @Valid @RequestBody MechanismGroupCreateRequest request
    ) {
        Long mechanismGroupId = mechanismGroupService.createMechanismGroup(request);
        return ResponseEntity.status(HttpStatus.OK).body(MechanismGroupCreateResponse.of(200, "Success", mechanismGroupId));
    }

    // 보드게임을 통한 진행방식 목록 조회
    @GetMapping("/mechanism/game/{gameId}")
    @ApiOperation(value = "보드게임을 통한 진행방식 목록 조회", notes = "보드게임을 통해 진행방식 목록을 조회합니다.")
    public ResponseEntity<?> readMechanismGroupListOfBoardGame(
            @PathVariable Long gameId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(MechanismGroupListResponse.of(200, "Success", mechanismGroupService.readMechanismGroupListOfBoardGame(gameId)));
    }

    // 진행방식을 통한 진행방식 목록 조회
    @GetMapping("/mechanism/cate/{mechanismId}")
    @ApiOperation(value = "진행방식을 통한 진행방식 목록 조회", notes = "진행방식을 통해 진행방식 목록을 조회합니다.")
    public ResponseEntity<?> readMechanismGroupListOfMechanism(
            @PathVariable Long mechanismId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(MechanismGroupListResponse.of(200, "Success", mechanismGroupService.readMechanismGroupListOfMechanism(mechanismId)));
    }

    // 진행방식 목록 수정
    @PutMapping("/mechanism/{mechanismGroupId}")
    @ApiOperation(value = "진행방식 목록 정보 수정", notes = "진행방식 목록의 정보를 수정합니다.")
    public ResponseEntity<?> updateMechanismGroup(
            @PathVariable Long mechanismGroupId,
            @Valid @RequestBody MechanismGroupUpdateRequest request
    ) {
        mechanismGroupService.updateMechanismGroup(mechanismGroupId, request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 진행방식 목록 삭제
    @DeleteMapping("/mechanism/{mechanismGroupId}")
    @ApiOperation(value = "진행방식 목록 삭제", notes = "진행방식 목록을 삭제합니다.")
    public ResponseEntity<?> deleteMechanismGroup(
            @PathVariable Long mechanismGroupId
    ) {
        mechanismGroupService.deleteMechanismGroup(mechanismGroupId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }
}