package com.specialization.yogidice.domain.repository;

import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.NumOfReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NumOfReviewRepository extends JpaRepository<NumOfReview, Long> {
    Optional<NumOfReview> findByBoardGame(BoardGame boardGame);
    
    List<NumOfReview> findTop100ByOrderByNumberDesc();
    Page<NumOfReview> findAllByOrderByNumberDesc(Pageable pageable);
}
