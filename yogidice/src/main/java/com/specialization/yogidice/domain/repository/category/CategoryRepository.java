package com.specialization.yogidice.domain.repository.category;

import com.specialization.yogidice.domain.entity.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}
