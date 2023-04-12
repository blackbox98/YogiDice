package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BoardGameListResponse")
public class BoardGameListResponse extends BaseResponseBody {
    private List<BoardGameResponse> responses;

    public static BoardGameListResponse of(Integer statusCode, String message, List<BoardGameResponse> boardGameResponses) {
        BoardGameListResponse response = new BoardGameListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(boardGameResponses);
        return response;
    }
}
