package com.example.catdog.careGroup.member;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.member.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CareGroupMemberDTO {
    private int group_num;

    private Member member;

    private Role role;

    private Resign_yn resign_yn;
}
