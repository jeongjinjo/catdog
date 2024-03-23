package com.example.catdog.member;

import com.example.catdog.enum_column.Resign_yn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Table(name = "member")
public class Member {
    @Id
    @Column(nullable = false)
    private String member_id = UUID.randomUUID().toString();

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String nickname;

    private String name;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    private String phone_num;


    public Member() {}

    public Member(String member_id, String password, String nickname, String name, Resign_yn resign_yn, String phone_num) {
        this.member_id = member_id;
        this.password = password;
        this.nickname = nickname;
        this.name = name;
        this.resign_yn = resign_yn;
        this.phone_num = phone_num;
    }
}
