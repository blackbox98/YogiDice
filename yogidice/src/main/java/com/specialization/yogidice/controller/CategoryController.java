package com.specialization.yogidice.controller;

import com.specialization.yogidice.dto.request.category.CategoryRequest;
import com.specialization.yogidice.dto.request.category.MechanismRequest;
import com.specialization.yogidice.dto.request.category.TypeRequest;
import com.specialization.yogidice.dto.response.BaseResponseBody;
import com.specialization.yogidice.dto.response.category.*;
import com.specialization.yogidice.service.category.CategoryService;
import com.specialization.yogidice.service.category.MechanismService;
import com.specialization.yogidice.service.category.TypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@Api(tags = {"카테고리 API"})
public class CategoryController {
    private final CategoryService categoryService;
    private final TypeService typeService;
    private final MechanismService mechanismService;

    // 카테고리(테마) 추가
    @PostMapping("/theme")
    @ApiOperation(value = "카테고리(테마) 추가", notes = "카테고리(테마)에 필요한 정보를 입력하고 추가합니다.")
    public ResponseEntity<?> createCategory(
            @Valid @RequestBody CategoryRequest request
    ) {
        Long cateId = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.OK).body(CategoryCreateResponse.of(200, "Success", cateId));
    }

    // 카테고리(테마) 목록 조회
    @GetMapping("/theme")
    @ApiOperation(value = "카테고리(테마) 목록 조회", notes = "카테고리(테마) 목록을 조회합니다.")
    public ResponseEntity<?> readCategoryList() {
        return ResponseEntity.status(HttpStatus.OK).body(CategoryListResponse.of(200, "Success", categoryService.readCategoryList()));
    }

    // 카테고리(테마) 상세 조회
    @GetMapping("/theme/{cateId}")
    @ApiOperation(value = "카테고리(테마) 상세 조회", notes = "카테고리(테마)을 상세 조회합니다.")
    public ResponseEntity<?> readCategory(
            @PathVariable Long cateId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(CategoryDetailResponse.of(200, "Success", categoryService.readCategory(cateId)));
    }

    // 카테고리(테마) 수정
    @PutMapping("/theme/{cateId}")
    @ApiOperation(value = "카테고리(테마) 정보 수정", notes = "카테고리(테마)의 정보를 수정합니다.")
    public ResponseEntity<?> updateCategory(
            @PathVariable Long cateId,
            @Valid @RequestBody CategoryRequest request
    ) {
        categoryService.updateCategory(cateId, request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 카테고리(테마) 삭제
    @DeleteMapping("/theme/{cateId}")
    @ApiOperation(value = "카테고리(테마) 삭제", notes = "카테고리(테마)을 삭제합니다.")
    public ResponseEntity<?> deleteCategory(
            @PathVariable Long cateId
    ) {
        categoryService.deleteCategory(cateId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 타입 추가
    @PostMapping("/type")
    @ApiOperation(value = "타입 추가", notes = "타입에 필요한 정보를 입력하고 추가합니다.")
    public ResponseEntity<?> createType(
            @Valid @RequestBody TypeRequest request
    ) {
        Byte typeId = typeService.createType(request);
        return ResponseEntity.status(HttpStatus.OK).body(TypeCreateResponse.of(200, "Success", typeId));
    }

    // 타입 목록 조회
    @GetMapping("/type")
    @ApiOperation(value = "타입 목록 조회", notes = "타입 목록을 조회합니다.")
    public ResponseEntity<?> readTypeList() {
        return ResponseEntity.status(HttpStatus.OK).body(TypeListResponse.of(200, "Success", typeService.readTypeList()));
    }

    // 타입 상세 조회
    @GetMapping("/type/{typeId}")
    @ApiOperation(value = "타입 상세 조회", notes = "타입을 상세 조회합니다.")
    public ResponseEntity<?> readType(
            @PathVariable Byte typeId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(TypeDetailResponse.of(200, "Success", typeService.readType(typeId)));
    }

    // 타입 수정
    @PutMapping("/type/{typeId}")
    @ApiOperation(value = "타입 정보 수정", notes = "타입의 정보를 수정합니다.")
    public ResponseEntity<?> updateType(
            @PathVariable Byte typeId,
            @Valid @RequestBody TypeRequest request
    ) {
        typeService.updateType(typeId, request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 타입 삭제
    @DeleteMapping("/type/{typeId}")
    @ApiOperation(value = "타입 삭제", notes = "타입을 삭제합니다.")
    public ResponseEntity<?> deleteType(
            @PathVariable Byte typeId
    ) {
        typeService.deleteType(typeId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 진행방식 추가
    @PostMapping("/mechanism")
    @ApiOperation(value = "진행방식 추가", notes = "진행방식에 필요한 정보를 입력하고 추가합니다.")
    public ResponseEntity<?> createMechanism(
            @Valid @RequestBody MechanismRequest request
    ) {
        Long mechanismId = mechanismService.createMechanism(request);
        return ResponseEntity.status(HttpStatus.OK).body(MechanismCreateResponse.of(200, "Success", mechanismId));
    }

    // 진행방식 목록 조회
    @GetMapping("/mechanism")
    @ApiOperation(value = "진행방식 목록 조회", notes = "진행방식 목록을 조회합니다.")
    public ResponseEntity<?> readMechanismList() {
        return ResponseEntity.status(HttpStatus.OK).body(MechanismListResponse.of(200, "Success", mechanismService.readMechanismList()));
    }

    // 진행방식 상세 조회
    @GetMapping("/mechanism/{mechanismId}")
    @ApiOperation(value = "진행방식 상세 조회", notes = "진행방식을 상세 조회합니다.")
    public ResponseEntity<?> readMechanism(
            @PathVariable Long mechanismId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(MechanismDetailResponse.of(200, "Success", mechanismService.readMechanism(mechanismId)));
    }

    // 진행방식 수정
    @PutMapping("/mechanism/{mechanismId}")
    @ApiOperation(value = "진행방식 정보 수정", notes = "진행방식의 정보를 수정합니다.")
    public ResponseEntity<?> updateMechanism(
            @PathVariable Long mechanismId,
            @Valid @RequestBody MechanismRequest request
    ) {
        mechanismService.updateMechanism(mechanismId, request);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }

    // 진행방식 삭제
    @DeleteMapping("/mechanism/{mechanismId}")
    @ApiOperation(value = "진행방식 삭제", notes = "진행방식을 삭제합니다.")
    public ResponseEntity<?> deleteMechanism(
            @PathVariable Long mechanismId
    ) {
        mechanismService.deleteMechanism(mechanismId);
        return ResponseEntity.status(HttpStatus.OK).body(BaseResponseBody.of(200, "Success"));
    }
}
