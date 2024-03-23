package com.example.catdog.careGroup.member;

import com.example.catdog.careGroup.CareGroup;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareGroupMemberDTO {
    private int group_member_num;

    private CareGroup groupNum;

    private String member_id;

    private Role role;

    private Resign_yn resign_yn;
}
