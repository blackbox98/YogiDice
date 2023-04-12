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
public class NumOfReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_review_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "game_id")
    private BoardGame boardGame;

    @NotNull
    private int number;

    public static NumOfReview create(BoardGame boardGame) {
        NumOfReview numOfReview = new NumOfReview();
        numOfReview.boardGame = boardGame;
        numOfReview.number = 0;
        return numOfReview;
    }

    public void update(int number) {
        this.number = number;
    }

    public void addReview() {
        this.number += 1;
    }

    public void deleteReview() {
        this.number -= 1;
    }
}
