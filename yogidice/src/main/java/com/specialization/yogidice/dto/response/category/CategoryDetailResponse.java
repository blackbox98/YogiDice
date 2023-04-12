package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CategoryDetailResponse")
public class CategoryDetailResponse extends BaseResponseBody {
    private Long id;

    private String name;

    public static CategoryDetailResponse of(Integer statusCode, String message, CategoryResponse categoryResponse) {
        CategoryDetailResponse response = new CategoryDetailResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(categoryResponse.getId());
        response.setName(categoryResponse.getName());
        return response;
    }
}
