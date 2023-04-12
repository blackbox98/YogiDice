package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.type.Reviewed;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("UserReviewedResponse")
public class UserReviewedResponse extends BaseResponseBody {
    private Reviewed reviewed;

    public static UserReviewedResponse of(Integer statusCode, String message, Reviewed reviewed) {
        UserReviewedResponse response = new UserReviewedResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setReviewed(reviewed);
        return response;
    }
}
