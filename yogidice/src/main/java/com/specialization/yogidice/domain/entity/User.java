package com.specialization.yogidice.domain.entity;

import com.specialization.yogidice.domain.entity.type.Reviewed;
import com.specialization.yogidice.domain.entity.type.Role;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 유저 모델 정의.
 */
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotNull
    @Column(length = 100)
    private String kakaoId;

    @NotNull
    @Column(length = 50)
    private String nickName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Reviewed reviewed;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<History> histories = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recommend> recommends = new ArrayList<>();

    public static User create(String kakaoId, String nickName){
        User user = new User();
        user.kakaoId = kakaoId;
        user.nickName = nickName;
        user.role = Role.ROLE_USER;
        user.reviewed = Reviewed.F;
        return user;
    }

    public void update(String nickName){
        this.nickName = nickName;
    }

    public void completeReview() {
        this.reviewed = Reviewed.T;
    }
}
