package com.example.catdog.member;

import com.example.catdog.common.Resign_yn;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
public class Member {
    @Id
    @Column(nullable = false)
    private String member_id = UUID.randomUUID().toString();

    private String password;

    @Column(unique = true)
    private String nickname;

    private String name;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    // 비밀번호 변경될 값
    private String passwordUpdate;

}
