package com.example.catdog.member;

import com.example.catdog.care_group.Care_group;
import com.example.catdog.enum_column.Resign_yn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
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

}
