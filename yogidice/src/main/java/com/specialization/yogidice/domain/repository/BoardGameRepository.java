package com.specialization.yogidice.domain.repository;

import com.specialization.yogidice.domain.entity.BoardGame;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BoardGameRepository extends JpaRepository<BoardGame, Long> {
    Page<BoardGame> findAll(Pageable pageable);

    Optional<BoardGame> findByTitleKr(String titleKr);

    List<BoardGame> findTop10ByOrderById();

    List<BoardGame> findTop10ByOrderByRatingUserDesc();

    Page<BoardGame> findAllByOrderByRatingUserDesc(Pageable pageable);

    List<BoardGame> findTop10ByOrderByPublishYearDesc();

    Page<BoardGame> findAllByOrderByPublishYearDesc(Pageable pageable);

    List<BoardGame> findByBggCode(Long bggCode);

    List<BoardGame> findAllByTitleKrContains(String titleKr);

    List<BoardGame> findAllByIdIn(List<Long> ids);
}
