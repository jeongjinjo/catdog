package com.example.catdog.member;

import com.example.catdog.care_group.Care_group;
import com.example.catdog.enum_column.Resign_yn;
import jakarta.persistence.*;
import lombok.Builder;
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

    private String phone_num;


    // 비밀번호 변경될 값
//    private String passwordUpdate;

    public Member() {}

    public Member(String member_id, String nickname, String name) {
        this.member_id = member_id;
        this.nickname = nickname;
        this.name = name;
    }

    public Member(String member_id, String password, String name, String nickname, String phone_num, Resign_yn resign_yn) {
        this.member_id = member_id;
        this.password = password;
        this.name = name;
        this.nickname = nickname;
        this.phone_num = phone_num;
        this.resign_yn = resign_yn;
    }

}
