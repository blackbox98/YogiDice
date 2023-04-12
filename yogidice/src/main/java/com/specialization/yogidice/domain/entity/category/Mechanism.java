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
public class Mechanism {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "mechanism_id")
    private Long id;

    @NotNull
    @Column(length = 50)
    private String name;

    @NotNull
    @Column(length = 50)
    private String parentsMec;

    @NotNull
    @Column(columnDefinition = "MEDIUMTEXT")
    private String description;

    @OneToMany(mappedBy = "mechanism", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MechanismGroup> mechanismGroups = new ArrayList<>();

    public static Mechanism create(String name, String parentsMec, String description) {
        Mechanism mechanism = new Mechanism();
        mechanism.name = name;
        mechanism.parentsMec = parentsMec;
        mechanism.description = description;
        return mechanism;
    }

    public void update(String name, String parentsMec, String description) {
        this.name = name;
        this.parentsMec = parentsMec;
        this.description = description;
    }
}
