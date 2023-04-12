package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TypeDetailResponse")
public class TypeDetailResponse extends BaseResponseBody {
    private Byte id;

    private String name;

    public static TypeDetailResponse of(Integer statusCode, String message, TypeResponse typeResponse) {
        TypeDetailResponse response = new TypeDetailResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(typeResponse.getId());
        response.setName(typeResponse.getName());
        return response;
    }
}
