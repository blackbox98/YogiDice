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
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_id")
    private Byte id;

    @NotNull
    @Column(length = 50)
    private String name;

    @OneToMany(mappedBy = "type", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TypeGroup> typeGroups = new ArrayList<>();

    public static Type create(String name) {
        Type type = new Type();
        type.name = name;
        return type;
    }

    public void update(String name) {
        this.name = name;
    }
}
