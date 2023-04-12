package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.dto.response.category.CategoryGroupResponse;
import com.specialization.yogidice.dto.response.category.MechanismGroupResponse;
import com.specialization.yogidice.dto.response.category.TypeGroupResponse;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BoardGameResponse")
public class BoardGameResponse {
    private Long id;

    private String titleKr;

    private String titleEng;

    private int publishYear;

    private String thumbUrl;

    private double ratingBl;

    private double ratingUser;

    private Long bggCode;

    private Byte minPlayers;

    private Byte maxPlayers;

    private int playingTime;

    private int minTime;

    private int maxTime;

    private int age;

    private double difficulty;

    private String youtubeUrl;

    private String contents;

    private String contentsImgUrl;

    private List<CategoryGroupResponse> categoryGroupResponses;

    private List<TypeGroupResponse> typeGroupResponses;

    private List<MechanismGroupResponse> mechanismGroupResponses;

    public static BoardGameResponse response(
            BoardGame boardGame,
            List<CategoryGroupResponse> categoryLists,
            List<TypeGroupResponse> typeLists,
            List<MechanismGroupResponse> mechanismLists
    ) {
        return new BoardGameResponse(
                boardGame.getId(),
                boardGame.getTitleKr(),
                boardGame.getTitleEng(),
                boardGame.getPublishYear(),
                boardGame.getThumbUrl(),
                boardGame.getRatingBl(),
                boardGame.getRatingUser(),
                boardGame.getBggCode(),
                boardGame.getMinPlayers(),
                boardGame.getMaxPlayers(),
                boardGame.getPlayingTime(),
                boardGame.getMinTime(),
                boardGame.getMaxTime(),
                boardGame.getAge(),
                boardGame.getDifficulty(),
                boardGame.getYoutubeUrl(),
                boardGame.getContents(),
                boardGame.getContentsImgUrl(),
                categoryLists,
                typeLists,
                mechanismLists
        );
    }
}
