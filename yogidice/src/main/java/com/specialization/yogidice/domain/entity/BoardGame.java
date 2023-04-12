package com.specialization.yogidice.domain.entity;

import com.specialization.yogidice.domain.entity.category.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BoardGame {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private long id;

    @NotNull
    @Column(length = 100)
    private String titleKr;

    @NotNull
    @Column(length = 100)
    private String titleEng;

    @NotNull
    private int publishYear;

    @NotNull
    @Column(length = 200)
    private String thumbUrl;

    private double ratingBl;

    @NotNull
    private double ratingUser;

    @NotNull
    private long bggCode;

    private Byte minPlayers;

    private Byte maxPlayers;

    private int playingTime;

    private int minTime;

    private int maxTime;

    private int age;

    private double difficulty;

    @Column(length = 200)
    private String youtubeUrl;

    @Column(length = 500)
    private String contents;

    @Column(length = 200)
    private String contentsImgUrl;

    @OneToMany(mappedBy = "boardGame", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryGroup> categoryGroups = new ArrayList<>();

    @OneToMany(mappedBy = "boardGame", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeGroup> typeGroups = new ArrayList<>();

    @OneToMany(mappedBy = "boardGame", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MechanismGroup> mechanismGroups = new ArrayList<>();

    @OneToMany(mappedBy = "boardGame", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "boardGame", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToOne(mappedBy = "boardGame", cascade = CascadeType.ALL, orphanRemoval = true)
    private NumOfReview numOfReview;

    public static BoardGame create(String titleKr, String titleEng, int publishYear, String thumbUrl, double ratingBl, double ratingUser, Long bggCode, Byte minPlayers, Byte maxPlayers, int playingTime, int minTime, int maxTime, int age, double difficulty, String youtubeUrl, String contents, String contentsImgUrl) {
        BoardGame boardGame = new BoardGame();
        boardGame.titleKr = titleKr;
        boardGame.titleEng = titleEng;
        boardGame.publishYear = publishYear;
        boardGame.thumbUrl = thumbUrl;
        boardGame.ratingBl = ratingBl;
        boardGame.ratingUser = ratingUser;
        boardGame.bggCode = bggCode;
        boardGame.minPlayers = minPlayers;
        boardGame.maxPlayers = maxPlayers;
        boardGame.playingTime = playingTime;
        boardGame.minTime = minTime;
        boardGame.maxTime = maxTime;
        boardGame.age = age;
        boardGame.difficulty = difficulty;
        boardGame.youtubeUrl = youtubeUrl;
        boardGame.contents = contents;
        boardGame.contentsImgUrl = contentsImgUrl;
        return boardGame;
    }

    public void update(String titleKr, String titleEng, int publishYear, String thumbUrl, double ratingBl, double ratingUser, Long bggCode, Byte minPlayers, Byte maxPlayers, int playingTime, int minTime, int maxTime, int age, double difficulty, String youtubeUrl, String contents, String contentsImgUrl) {
        this.titleKr = titleKr;
        this.titleEng = titleEng;
        this.publishYear = publishYear;
        this.thumbUrl = thumbUrl;
        this.ratingBl = ratingBl;
        this.ratingUser = ratingUser;
        this.bggCode = bggCode;
        this.minPlayers = minPlayers;
        this.maxPlayers = maxPlayers;
        this.playingTime = playingTime;
        this.minTime = minTime;
        this.maxTime = maxTime;
        this.age = age;
        this.difficulty = difficulty;
        this.youtubeUrl = youtubeUrl;
        this.contents = contents;
        this.contentsImgUrl = contentsImgUrl;
    }

    public void updateRatingUser(double ratingUser) {
        this.ratingUser = ratingUser;
    }
}
