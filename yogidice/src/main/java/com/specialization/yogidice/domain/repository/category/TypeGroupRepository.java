package com.specialization.yogidice.domain.repository.category;

import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.category.Type;
import com.specialization.yogidice.domain.entity.category.TypeGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TypeGroupRepository extends JpaRepository<TypeGroup, Long> {
    List<TypeGroup> findByBoardGame(BoardGame boardGame);

    List<TypeGroup> findByType(Type type);

    Optional<TypeGroup> findByBoardGameAndType(BoardGame boardGame, Type type);
}
