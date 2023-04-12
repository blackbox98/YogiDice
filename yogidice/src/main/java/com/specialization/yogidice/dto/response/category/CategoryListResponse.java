package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CategoryListResponse")
public class CategoryListResponse extends BaseResponseBody {
    private List<CategoryResponse> responses;

    public static CategoryListResponse of(Integer statusCode, String message, List<CategoryResponse> categoryResponses) {
        CategoryListResponse response = new CategoryListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(categoryResponses);
        return response;
    }
}
