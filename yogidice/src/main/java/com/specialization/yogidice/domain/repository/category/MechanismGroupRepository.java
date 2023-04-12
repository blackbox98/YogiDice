package com.specialization.yogidice.domain.repository.category;

import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.category.Mechanism;
import com.specialization.yogidice.domain.entity.category.MechanismGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MechanismGroupRepository extends JpaRepository<MechanismGroup, Long> {
    List<MechanismGroup> findByBoardGame(BoardGame boardGame);

    List<MechanismGroup> findByMechanism(Mechanism mechanism);

    Optional<MechanismGroup> findByBoardGameAndMechanism(BoardGame boardGame, Mechanism mechanism);
}
