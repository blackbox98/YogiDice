package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.CafeGameList;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("CafeGameResponse")
public class CafeGameResponse {
    private Long id;

    private Long cafeId;

    private String cafeName;

    private Long gameId;

    private String gameTitle;

    public static CafeGameResponse response(CafeGameList cafeGameList) {
        return new CafeGameResponse(
                cafeGameList.getId(),
                cafeGameList.getBoardGameCafe().getId(),
                cafeGameList.getBoardGameCafe().getName(),
                cafeGameList.getBoardGame().getId(),
                cafeGameList.getBoardGame().getTitleKr()
        );
    }
}
