package com.specialization.yogidice.domain.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class BoardGameCafe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cafe_id")
    private Long id;

    @Column(length = 100)
    @NotNull
    private String name;

    @Column(length = 200)
    @NotNull
    private String address;

    @OneToMany(mappedBy = "boardGameCafe", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CafeGameList> cafeGameLists = new ArrayList<>();

    public static BoardGameCafe create(String name, String address) {
        BoardGameCafe boardgameCafe = new BoardGameCafe();
        boardgameCafe.name = name;
        boardgameCafe.address = address;
        return boardgameCafe;
    }

    public void update(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
