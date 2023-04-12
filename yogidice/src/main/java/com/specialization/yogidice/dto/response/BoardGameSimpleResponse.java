package com.specialization.yogidice.dto.response;

import com.specialization.yogidice.domain.entity.BoardGame;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("BoardGameSimpleResponse")
public class BoardGameSimpleResponse {
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


    public static BoardGameSimpleResponse response(
            BoardGame boardGame
    ) {
        return new BoardGameSimpleResponse(
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
                boardGame.getContentsImgUrl()
        );
    }
}
