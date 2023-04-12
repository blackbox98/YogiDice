package com.specialization.yogidice.domain.repository.category;

import com.specialization.yogidice.domain.entity.category.Mechanism;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MechanismRepository extends JpaRepository<Mechanism, Long> {
    Optional<Mechanism> findByName(String name);
}
