package com.techeer.checkIt.domain.user.entity;

import com.techeer.checkIt.domain.reading.entity.Reading;
import com.techeer.checkIt.domain.readingVolume.entity.ReadingVolume;
import com.techeer.checkIt.entity.BaseEntity;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false, unique = true, length = 30)
    private String username;

    @Column(nullable = false, length = 30)
    private String nickname;

    @Column(nullable = false, length = 250)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Column(name = "is_active")
    private boolean isActive = true; // 활성 여부

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reading> readingList = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReadingVolume> readingVolumeList = new ArrayList<>();

    @Builder
    private User(String username, String nickname, String password, Role role) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
    }

    public void inActive() {
        this.isActive = false;
    }

    public void setEncryptedPassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }
}
