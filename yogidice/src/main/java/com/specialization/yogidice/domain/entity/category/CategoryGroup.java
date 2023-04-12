package com.specialization.yogidice.domain.entity.category;

import com.specialization.yogidice.domain.entity.BoardGame;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class CategoryGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private BoardGame boardGame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cate_id")
    private Category category;

    public static CategoryGroup create(BoardGame boardGame, Category category) {
        CategoryGroup categoryGroup = new CategoryGroup();
        categoryGroup.boardGame = boardGame;
        categoryGroup.category = category;
        return categoryGroup;
    }

    public void update(Category category) {
        this.category = category;
    }
}
