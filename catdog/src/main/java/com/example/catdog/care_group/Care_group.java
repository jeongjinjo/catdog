package com.example.catdog.care_group;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Schema(description = "Group Table에 대한 내용입니다.")
public class Care_group {
    @Id
    @Column(nullable = false)
    private int group_num;

    @Column(length = 100)
    private String group_name;

//    @Column(length = 100)
//    private String member_id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    private int group_key;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;


    public Care_group() {}

    // 다른 방법이 있을 거 같은데.. ㅠ.ㅠ
    public Care_group(String group_name, Member member, Role role, Resign_yn resign_yn, int group_key) {
        this.group_name = group_name;
//        this.member_id = member;
        this.member = member;
        this.role = role;
        this.resign_yn = resign_yn;
        this.group_key = group_key;
    }


}
