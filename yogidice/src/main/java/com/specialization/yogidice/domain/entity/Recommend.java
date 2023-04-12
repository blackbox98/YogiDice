package com.specialization.yogidice.domain.entity;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private BoardGame boardGame;

    public static Recommend create(User user, BoardGame boardGame){
        Recommend recommend = new Recommend();
        recommend.user = user;
        recommend.boardGame = boardGame;
        return recommend;
    }
}
