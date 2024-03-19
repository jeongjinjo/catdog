package com.example.catdog.member;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
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
    private String nickname;
    private String name;

//    @Enumerated(EnumType.STRING)
    private resign_yn resignYn;

}
