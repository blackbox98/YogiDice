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
@ApiModel("CategoryGroupListResponse")
public class CategoryGroupListResponse extends BaseResponseBody {
    private List<CategoryGroupResponse> responses;

    public static CategoryGroupListResponse of(Integer statusCode, String message, List<CategoryGroupResponse> categoryGroupResponses) {
        CategoryGroupListResponse response = new CategoryGroupListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(categoryGroupResponses);
        return response;
    }
}
