package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BoardGameCafeCreateResponse")
public class BoardGameCafeCreateResponse extends BaseResponseBody {
    private Long id;

    public static BoardGameCafeCreateResponse of(Integer statusCode, String message, Long cafeId) {
        BoardGameCafeCreateResponse response = new BoardGameCafeCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(cafeId);
        return response;
    }
}
