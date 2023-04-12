package com.specialization.yogidice.dto.response;

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
@ApiModel("BoardGameDetailResponse")
public class BoardGameDetailResponse extends BaseResponseBody {
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

    public static BoardGameDetailResponse of(Integer statusCode, String message, BoardGameResponse boardGameResponse) {
        BoardGameDetailResponse response = new BoardGameDetailResponse();
        response.setStatusCode(statusCode);
        response.setMessage(message);
        response.setId(boardGameResponse.getId());
        response.setTitleKr(boardGameResponse.getTitleKr());
        response.setTitleEng(boardGameResponse.getTitleEng());
        response.setPublishYear(boardGameResponse.getPublishYear());
        response.setThumbUrl(boardGameResponse.getThumbUrl());
        response.setRatingBl(boardGameResponse.getRatingBl());
        response.setRatingUser(boardGameResponse.getRatingUser());
        response.setBggCode(boardGameResponse.getBggCode());
        response.setMinPlayers(boardGameResponse.getMinPlayers());
        response.setMaxPlayers(boardGameResponse.getMaxPlayers());
        response.setPlayingTime(boardGameResponse.getPlayingTime());
        response.setMinTime(boardGameResponse.getMinTime());
        response.setMaxTime(boardGameResponse.getMaxTime());
        response.setAge(boardGameResponse.getAge());
        response.setDifficulty(boardGameResponse.getDifficulty());
        response.setYoutubeUrl(boardGameResponse.getYoutubeUrl());
        response.setContents(boardGameResponse.getContents());
        response.setContentsImgUrl(boardGameResponse.getContentsImgUrl());
        response.setCategoryGroupResponses(boardGameResponse.getCategoryGroupResponses());
        response.setTypeGroupResponses(boardGameResponse.getTypeGroupResponses());
        response.setMechanismGroupResponses(boardGameResponse.getMechanismGroupResponses());
        return response;
    }
}
