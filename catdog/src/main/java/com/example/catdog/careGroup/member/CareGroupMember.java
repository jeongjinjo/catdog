package com.example.catdog.careGroup.member;

import com.example.catdog.careGroup.CareGroup;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.member.Member;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "care_group_member")
public class CareGroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int group_member_num;

    @OneToOne
    @JoinColumn(name="group_num")
    private CareGroup careGroup;

    @OneToOne
    @JoinColumn(name="member_id")
    private Member member;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;
}
