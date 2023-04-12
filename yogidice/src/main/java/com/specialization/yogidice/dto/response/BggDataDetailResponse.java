package com.specialization.yogidice.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BggDataDetailResponse")
public class BggDataDetailResponse extends BaseResponseBody {
    private Long id;

    private Long bggCode;

    private String nickName;

    private double ratingUser;

    public static BggDataDetailResponse of(Integer statusCode, String message, BggDataResponse bggDataResponse) {
        BggDataDetailResponse response = new BggDataDetailResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(bggDataResponse.getId());
        response.setBggCode(bggDataResponse.getBggCode());
        response.setNickName(bggDataResponse.getNickName());
        response.setRatingUser(bggDataResponse.getRatingUser());
        return response;
    }
}
