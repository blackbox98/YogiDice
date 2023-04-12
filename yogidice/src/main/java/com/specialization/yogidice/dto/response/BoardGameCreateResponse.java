package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BoardGameCreateResponse")
public class BoardGameCreateResponse extends BaseResponseBody {
    private Long id;

    public static BoardGameCreateResponse of(Integer statusCode, String message, Long gameId) {
        BoardGameCreateResponse response = new BoardGameCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(gameId);
        return response;
    }
}
