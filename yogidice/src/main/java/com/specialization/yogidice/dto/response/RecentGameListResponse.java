package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("RecentGameListResponse")
public class RecentGameListResponse extends BaseResponseBody {
    private List<RecentGameResponse> responses;

    public static RecentGameListResponse of(Integer statusCode, String message, List<RecentGameResponse> recentGameResponses) {
        RecentGameListResponse response = new RecentGameListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(recentGameResponses);
        return response;
    }
}
