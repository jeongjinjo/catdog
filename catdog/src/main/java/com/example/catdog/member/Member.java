package com.example.catdog.member;

import com.example.catdog.enum_column.Resign_yn;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
@Schema(description = "회원에 대한 정보입니다.")
public class Member {
    @Id
    @Column(nullable = false)
    @Schema(title = "사용자 ID", description = "최대 50자까지이며, 고유한 값을 등록해주어야 합니다.")
    private String member_id = UUID.randomUUID().toString();

    @JsonIgnore
    @Schema(title = "사용자 PW", description = "최대 100자까지이며, 4글자 이상이면 됩니다.")
    private String password;

    @Schema(title = "사용자 닉네임", description = "최대 50자까지이며, 고유한 값을 등록해주어야 합니다.")
    @Column(unique = true)
    private String nickname;

    @Schema(title = "사용자 이름", description = "50자까지 가능합니다.")
    private String name;

    @Schema(title = "사용자 탈퇴여부", description = "탈퇴가 되었다면 Y, 서비스 이용중이라면 N으로 표시한다.")
    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    @Column(unique = true)
    @Schema(title = "사용자 전화번호", description = "고유한 값을 등록해주어야 합니다.")
    private String phone_num;
}
