package com.specialization.yogidice.domain.entity.category;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cate_id")
    private Long id;

    @NotNull
    @Column(length = 50)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CategoryGroup> categoryGroups = new ArrayList<>();

    public static Category create(String name) {
        Category category = new Category();
        category.name = name;
        return category;
    }

    public void update(String name) {
        this.name = name;
    }
}
