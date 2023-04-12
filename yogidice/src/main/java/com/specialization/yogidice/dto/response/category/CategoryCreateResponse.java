package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CategoryCreateResponse")
public class CategoryCreateResponse extends BaseResponseBody {
    private Long id;

    public static CategoryCreateResponse of(Integer statusCode, String message, Long categoryId) {
        CategoryCreateResponse response = new CategoryCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(categoryId);
        return response;
    }
}
