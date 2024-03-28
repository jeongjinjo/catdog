package com.example.catdog.careGroup.member;

import com.example.catdog.careGroup.CareGroup;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "그룹에 속한 회원 유효성 검사를 위한 객체입니다.")
public class CareGroupMemberDTO {
    @Schema(title = "식별변호")
    private int group_member_num;

    @Schema(title = "회원이 속해있는 그룹의 정보")
    private CareGroup groupNum;

    @Schema(title = "그룹에 속해있는 회원")
    private String member_id;

    @Schema(title = "그룹에 속해있는 회원의 권한")
    private Role role;

    @Schema(title = "회원 삭제여부")
    private Resign_yn resign_yn;
}
