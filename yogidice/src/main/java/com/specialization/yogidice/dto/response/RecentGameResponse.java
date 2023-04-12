package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.BoardGame;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("RecentGameResponse")
public class RecentGameResponse {
    private Long gameId;

    private String gameTitle;

    private int publishYear;

    public static RecentGameResponse response(BoardGame boardGame) {
        return new RecentGameResponse(
                boardGame.getId(),
                boardGame.getTitleKr(),
                boardGame.getPublishYear()
        );
    }
}
