package com.specialization.yogidice.dto.response.category;

import com.specialization.yogidice.dto.response.BaseResponseBody;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("TypeCreateResponse")
public class TypeCreateResponse extends BaseResponseBody {
    private Byte id;

    public static TypeCreateResponse of(Integer statusCode, String message, Byte typeId) {
        TypeCreateResponse response = new TypeCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(typeId);
        return response;
    }
}
