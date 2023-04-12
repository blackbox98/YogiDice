package com.specialization.yogidice.domain.repository;

import com.specialization.yogidice.domain.entity.BoardGameCafe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardGameCafeRepository extends JpaRepository<BoardGameCafe, Long> {
    Optional<BoardGameCafe> findBoardGameCafeByNameAndAddress(String name, String address);

    Optional<BoardGameCafe> findBoardGameCafeByAddressContaining(String address);
}
