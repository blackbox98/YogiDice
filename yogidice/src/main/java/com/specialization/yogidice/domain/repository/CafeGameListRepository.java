package com.specialization.yogidice.domain.repository;

import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.BoardGameCafe;
import com.specialization.yogidice.domain.entity.CafeGameList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CafeGameListRepository extends JpaRepository<CafeGameList, Long> {
    List<CafeGameList> findCafeGameListByBoardGameCafe(BoardGameCafe boardGameCafe);

    List<CafeGameList> findCafeGameListByBoardGame(BoardGame boardGame);

    Optional<CafeGameList> findCafeGameListByBoardGameCafeAndBoardGame(BoardGameCafe boardGameCafe, BoardGame boardGame);
}
