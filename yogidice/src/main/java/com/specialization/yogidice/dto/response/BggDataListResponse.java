package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BggDataListResponse")
public class BggDataListResponse extends BaseResponseBody {
    private List<BggDataResponse> responses;

    public static BggDataListResponse of(Integer statusCode, String message, List<BggDataResponse> bggDataResponses) {
        BggDataListResponse response = new BggDataListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(bggDataResponses);
        return response;
    }
}
