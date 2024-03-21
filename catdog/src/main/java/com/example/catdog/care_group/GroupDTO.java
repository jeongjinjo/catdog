package com.example.catdog.care_group;

import com.example.catdog.common.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GroupDTO {
    @NotBlank(message = "필수 입력 값")
    private int group_num;

    @NotBlank(message = "필수 입력 값")
    @Size(max = 255)
    private String group_name;

    @NotBlank(message = "필수 입력 값")
    @Size(max = 50)
    private Role role;

    @Size(max = 10)
    private String resign_yn;

    @NotBlank(message = "필수 입력 값")
    @Size(max = 50 ,message = "최대 50자 입력 가능합니다.")
    private String id;

    @NotBlank(message = "필수 입력 값")
    private int pet_num;
}
