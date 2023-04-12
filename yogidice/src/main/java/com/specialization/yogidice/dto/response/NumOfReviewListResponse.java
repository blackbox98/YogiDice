package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("NumOfReviewListResponse")
public class NumOfReviewListResponse extends BaseResponseBody {
    private List<NumOfReviewResponse> responses;

    public static NumOfReviewListResponse of(Integer statusCode, String message, List<NumOfReviewResponse> numOfReviewResponses) {
        NumOfReviewListResponse response = new NumOfReviewListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(numOfReviewResponses);
        return response;
    }
}
