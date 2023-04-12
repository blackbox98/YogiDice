package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CafeGameCreateResponse")
public class CafeGameCreateResponse extends BaseResponseBody {
    private Long id;

    public static CafeGameCreateResponse of(Integer statusCode, String message, Long gameListId) {
        CafeGameCreateResponse response = new CafeGameCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(gameListId);
        return response;
    }
}
