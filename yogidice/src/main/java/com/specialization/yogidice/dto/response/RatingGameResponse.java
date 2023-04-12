package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.BoardGame;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("RatingGameResponse")
public class RatingGameResponse {
    private Long gameId;

    private String gameTitle;

    private double ratingUser;

    public static RatingGameResponse response(BoardGame boardGame) {
        return new RatingGameResponse(
                boardGame.getId(),
                boardGame.getTitleKr(),
                boardGame.getRatingUser()
        );
    }
}
