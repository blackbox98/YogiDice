package com.specialization.yogidice.domain.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CafeGameList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="game_List_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cafe_id")
    private BoardGameCafe boardGameCafe;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private BoardGame boardGame;

    public static CafeGameList create(BoardGameCafe boardGameCafe, BoardGame boardGame) {
        CafeGameList cafeGamelist = new CafeGameList();
        cafeGamelist.boardGameCafe = boardGameCafe;
        cafeGamelist.boardGame = boardGame;
        return cafeGamelist;
    }

    public void update(BoardGame boardGame) {
        this.boardGame = boardGame;
    }
}
