package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("HistoryListResponse")
public class HistoryListResponse extends BaseResponseBody {
    private List<HistoryResponse> responses;

    public static HistoryListResponse of(Integer statusCode, String message, List<HistoryResponse> historyResponses) {
        HistoryListResponse response = new HistoryListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(historyResponses);
        return response;
    }
}
