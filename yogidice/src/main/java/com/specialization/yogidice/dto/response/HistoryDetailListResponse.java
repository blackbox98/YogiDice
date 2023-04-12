package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("HistoryDetailListResponse")
public class HistoryDetailListResponse extends BaseResponseBody {
    private List<HistoryDetailResponse> responses;

    public static HistoryDetailListResponse of(Integer statusCode, String message, List<HistoryDetailResponse> historyResponses) {
        HistoryDetailListResponse response = new HistoryDetailListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(historyResponses);
        return response;
    }
}
