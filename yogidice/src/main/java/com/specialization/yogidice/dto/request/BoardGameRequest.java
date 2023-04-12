package com.specialization.yogidice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardGameRequest {
    @NotNull
    private String titleKr;

    @NotNull
    private String titleEng;

    @NotNull
    private int publishYear;

    @NotNull
    private String thumbUrl;

    private double ratingBl;

    @NotNull
    private double ratingUser;

    @NotNull
    private Long bbgCode;

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
}
