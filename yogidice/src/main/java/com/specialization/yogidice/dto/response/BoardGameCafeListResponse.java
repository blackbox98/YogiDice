package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BoardGameCafeListResponse")
public class BoardGameCafeListResponse extends BaseResponseBody {
    private List<BoardGameCafeResponse> responses;

    public static BoardGameCafeListResponse of(Integer statusCode, String message, List<BoardGameCafeResponse> boardGameCafeResponses) {
        BoardGameCafeListResponse response = new BoardGameCafeListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(boardGameCafeResponses);
        return response;
    }
}
