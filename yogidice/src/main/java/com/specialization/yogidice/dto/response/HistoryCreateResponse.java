package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("HistoryCreateResponse")
public class HistoryCreateResponse extends BaseResponseBody {
    private Long Id;

    public static HistoryCreateResponse of(Integer statusCode, String message, Long historyId) {
        HistoryCreateResponse response = new HistoryCreateResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(historyId);
        return response;
    }
}
