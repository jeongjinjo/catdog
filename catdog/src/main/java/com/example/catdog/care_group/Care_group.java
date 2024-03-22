package com.example.catdog.care_group;

import com.example.catdog.care_target.Care_target;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToOne
    @JoinColumn(name="member_id", unique = true)
    private Member member;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    private int group_key;
}
