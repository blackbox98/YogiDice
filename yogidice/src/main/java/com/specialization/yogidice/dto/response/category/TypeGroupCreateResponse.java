package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TypeGroupCreateResponse")
public class TypeGroupCreateResponse extends BaseResponseBody {
    private Long id;

    public static TypeGroupCreateResponse of(Integer statusCode, String message, Long typyGroupId) {
        TypeGroupCreateResponse response = new TypeGroupCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(typyGroupId);
        return response;
    }
}
