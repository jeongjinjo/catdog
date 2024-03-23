package com.example.catdog.member;

import com.example.catdog.enum_column.Resign_yn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberDTO {
    @NotBlank(message = "아이디 값이 없습니다.")
    @Size(min = 2, max = 50, message = "아이디는 최소 2글자 이상 ~ 50글자 이하로 입력해야합니다.")
    private String member_id;

    @NotBlank(message = "패스워드 값이 없습니다.")
    @Size(min = 4, max = 100 ,message = "패스워드의 크기는 4에서 100 사이여야 합니다.")
    private String password;

    @NotBlank(message = "닉네임 값이 없습니다.")
    @Size(min = 2, max = 50, message = "닉네임의 크기는 2에서 50 사이여야 합니다.")
    private String nickname;

    @NotBlank(message = "이름 값이 없습니다.")
    @Size(max = 50)
    private String name;

    private Resign_yn resign_yn;

    private String phone_num;

    // 비밀번호 변경될 값
    private String passwordUpdate;
}
//    @Pattern(regexp="^[a-zA-Z]+$", message="영어만 사용이 가능합니다.")
//    @Min(0) // 양수확인
//    private Integer mgr;
//    @JsonFormat(pattern = "yyyy/MM/dd")
//    private Date hiredate;
