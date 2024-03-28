package com.example.catdog.careGroup.member;

import com.example.catdog.careGroup.CareGroup;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;

@Data
@Entity
@Component
@Table(name = "care_group_member")
@Schema(description = "그룹에 속해있는 회원에 대한 정보입니다.")
public class CareGroupMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Schema(title = "식별변호", description = "자동으로 1씩 증가합니다.")
    private int group_member_num;

    @OneToOne
    @JoinColumn(name="group_num", unique = true)
    @Schema(title = "회원이 속해있는 그룹의 정보")
    private CareGroup careGroup;

    @OneToOne
    @JoinColumn(name="member_id", unique = true)
    @Schema(title = "그룹에 속해있는 회원의 정보")
    private Member member;

    @Enumerated(EnumType.STRING)
    @Schema(title = "그룹에 속해있는 회원의 권한")
    private Role role;

    @Enumerated(EnumType.STRING)
    @Schema(title = "회원 삭제여부")
    private Resign_yn resign_yn;
}
