package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BoardGameSimpleListResponse")
public class BoardGameSimpleListResponse extends BaseResponseBody {
    private List<BoardGameSimpleResponse> responses;

    public static BoardGameSimpleListResponse of(Integer statusCode, String message, List<BoardGameSimpleResponse> responses) {
        BoardGameSimpleListResponse response = new BoardGameSimpleListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(responses);
        return response;
    }
}
