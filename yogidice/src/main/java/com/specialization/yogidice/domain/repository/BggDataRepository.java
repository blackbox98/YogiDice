package com.specialization.yogidice.domain.repository;

import com.specialization.yogidice.domain.entity.BggData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BggDataRepository extends JpaRepository<BggData, Long> {
    Optional<BggData> findByBggCodeAndNickNameAndRatingUser(Long bggCode, String nickName, double ratingUser);
}
