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
public class MechanismGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mechanism_group_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private BoardGame boardGame;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mechanism_id")
    private Mechanism mechanism;

    public static MechanismGroup create(BoardGame boardGame, Mechanism mechanism) {
        MechanismGroup mechanismGroup = new MechanismGroup();
        mechanismGroup.boardGame = boardGame;
        mechanismGroup.mechanism = mechanism;
        return mechanismGroup;
    }

    public void update(Mechanism mechanism) {
        this.mechanism = mechanism;
    }
}
