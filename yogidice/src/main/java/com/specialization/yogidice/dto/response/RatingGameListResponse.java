package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("RatingGameListResponse")
public class RatingGameListResponse extends BaseResponseBody {
    private List<RatingGameResponse> responses;

    public static RatingGameListResponse of(Integer statusCode, String message, List<RatingGameResponse> ratingGameResponses) {
        RatingGameListResponse response = new RatingGameListResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setResponses(ratingGameResponses);
        return response;
    }
}
