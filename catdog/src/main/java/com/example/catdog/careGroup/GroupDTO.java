package com.example.catdog.careGroup;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Schema(description = "그룹 유효성 검사를 위한 객체입니다.")
public class GroupDTO {
    @Schema(title = "그룹 고유 번호")
    private int group_num;

    @NotBlank(message = "필수 입력 값")
    @Size(min = 1, max = 100, message = "1~100자로 지정해주세요.")
    @Schema(title = "그룹명")
    private String group_name;

    @Schema(title = "그룹 사용여부")
    private Resign_yn resign_yn;
}
