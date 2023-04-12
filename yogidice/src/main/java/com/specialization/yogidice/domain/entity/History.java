package com.specialization.yogidice.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class History extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_id")
    private Long id;

    @NotNull
    private double rating;

    @Column(length = 1000)
    private String review;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private BoardGame boardGame;

    public static History create(User user, BoardGame boardGame) {
        History history = new History();
        history.rating = 0;
        history.user = user;
        history.boardGame = boardGame;
        return history;
    }

    public void update(double rating, String review) {
        this.rating = rating;
        this.review = review;
    }
}
