package com.specialization.yogidice.domain.repository;

import com.specialization.yogidice.domain.entity.Recommend;
import com.specialization.yogidice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
    List<Recommend> findByUser(User user);

    void deleteByUser(User user);

}
