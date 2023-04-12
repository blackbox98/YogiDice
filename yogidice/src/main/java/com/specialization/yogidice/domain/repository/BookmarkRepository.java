package com.specialization.yogidice.domain.repository;

import com.specialization.yogidice.domain.entity.BoardGame;
import com.specialization.yogidice.domain.entity.Bookmark;
import com.specialization.yogidice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    List<Bookmark> findByUser(User user);

    Optional<Bookmark> findByUserAndBoardGame(User user, BoardGame boardGame);
}
