package com.example.catdog.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class Member {

    @Id
    private String id = UUID.randomUUID().toString();

    private String password;

    @Column(unique = true)
    private String nickname;


    private String name;

//    @Enumerated(EnumType.STRING)
    private String resign_yn;

}
