package com.example.catdog.careGroup;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class GroupDTO {
    private int group_num;

    @NotBlank(message = "필수 입력 값")
    @Size(max = 255)
    private String group_name;

    @NotBlank(message = "필수 입력 값")
    @Size(max = 50 ,message = "최대 50자 입력 가능합니다.")
    private String member_id;

    private Role role;

    private Resign_yn resign_yn;

    private List<Integer> group_key;
}
