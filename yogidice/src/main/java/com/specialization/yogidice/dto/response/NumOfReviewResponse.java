package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.NumOfReview;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("NumOfReviewResponse")
public class NumOfReviewResponse {
    private Long id;

    private Long gameId;

    private String gameTitle;

    private Long bggCode;

    private int number;

    public static NumOfReviewResponse response(NumOfReview numOfReview) {
        return new NumOfReviewResponse(
                numOfReview.getId(),
                numOfReview.getBoardGame().getId(),
                numOfReview.getBoardGame().getTitleKr(),
                numOfReview.getBoardGame().getBggCode(),
                numOfReview.getNumber()
        );
    }
}
