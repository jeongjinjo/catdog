package com.example.catdog.member;

import com.example.catdog.enum_column.Resign_yn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Schema(description = "회원 유효성 검사를 위한 객체입니다.")
public class MemberDTO {
    @NotBlank(message = "아이디 값이 없습니다.")
    @Size(min = 2, max = 50, message = "아이디는 최소 2글자 이상 ~ 50글자 이하로 입력해야합니다.")
    @Schema(description = "사용자의 고유 아이디")
    private String member_id;

    @NotBlank(message = "패스워드 값이 없습니다.")
    @Size(min = 4, max = 100, message = "패스워드의 크기는 4에서 100 사이여야 합니다.")
    @Schema(description = "아이디에 대한 패스워드")
    private String password;

    @Size(min = 2, max = 50, message = "닉네임의 크기는 2에서 50 사이여야 합니다.")
    @Schema(description = "사용자의 닉네임")
    private String nickname;

    @Size(max = 50, message = "이름은 50자까지 입력이 가능합니다.")
    @Schema(description = "사용자의 이름")
    private String name;

    @Schema(description = "사용자의 탈퇴여부")
    private Resign_yn resign_yn;

    @Schema(description = "사용자의 전화번호", example = "010-1524-5151")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "휴대폰 번호 양식에 맞지 않습니다.")
    private String phone_num;

    // 비밀번호 변경될 값
    @Schema(description = "아이디에 대한 새로운 비밀번호")
    @Size(min = 4, max = 100, message = "패스워드의 크기는 4에서 100 사이여야 합니다.")
    private String passwordUpdate;
}
//    @Pattern(regexp="^[a-zA-Z]+$", message="영어만 사용이 가능합니다.")
//    @Min(0) // 양수확인
//    private Integer mgr;
//    @JsonFormat(pattern = "yyyy/MM/dd")
//    private Date hiredate;
