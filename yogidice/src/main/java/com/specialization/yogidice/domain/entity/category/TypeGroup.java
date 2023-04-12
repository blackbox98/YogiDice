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
public class TypeGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private BoardGame boardGame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "type_id")
    private Type type;

    public static TypeGroup create(BoardGame boardGame, Type type) {
        TypeGroup typeGroup = new TypeGroup();
        typeGroup.boardGame = boardGame;
        typeGroup.type = type;
        return typeGroup;
    }

    public void update(Type type) {
        this.type = type;
    }
}
