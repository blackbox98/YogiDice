package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CategoryGroupCreateResponse")
public class CategoryGroupCreateResponse extends BaseResponseBody {
    private Long id;

    public static CategoryGroupCreateResponse of(Integer statusCode, String message, Long categoryGroupId) {
        CategoryGroupCreateResponse response = new CategoryGroupCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(categoryGroupId);
        return response;
    }
}
