package com.specialization.yogidice.domain.repository.category;

import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.category.Category;
import com.specialization.yogidice.domain.entity.category.CategoryGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryGroupRepository extends JpaRepository<CategoryGroup, Long> {
    List<CategoryGroup> findByBoardGame(BoardGame boardGame);

    List<CategoryGroup> findByCategory(Category category);

    Optional<CategoryGroup> findByBoardGameAndCategory(BoardGame boardGame, Category category);
}
