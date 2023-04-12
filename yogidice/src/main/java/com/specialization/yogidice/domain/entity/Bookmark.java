package com.specialization.yogidice.domain.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private BoardGame boardGame;

    public static Bookmark create(User user, BoardGame boardGame){
        Bookmark bookmark = new Bookmark();
        bookmark.user = user;
        bookmark.boardGame = boardGame;
        return bookmark;
    }
}
