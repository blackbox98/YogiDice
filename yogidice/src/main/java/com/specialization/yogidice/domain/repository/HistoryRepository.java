package com.specialization.yogidice.domain.repository;

import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.History;
import com.specialization.yogidice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HistoryRepository extends JpaRepository<History, Long> {
    List<History> findByUser(User user);

    Optional<History> findByUserAndBoardGame(User user, BoardGame boardGame);

    List<History> findAllByBoardGame(BoardGame boardGame);
}
