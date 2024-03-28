package com.example.catdog.todo;

import com.example.catdog.enum_column.Comp_yn;
import com.example.catdog.enum_column.Resign_yn;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Schema(description = "할 일 유효성 검사를 위한 객체입니다.")
public class TodoDTO {
    @NotBlank(message = "필수 입력 값")
    @Schema(description = "할 일 고유번호")
    private int todo_num;

    @Size(max = 30, message = "todo는 최대 30자까지만 입력이 가능합니다.")
    @Schema(description = "할 일 내용")
    private String todo;

    @Schema(title = "작성자", description = "최초로 등록한 회원 아이디를 나타냅니다.")
    private String start_id;

    @Schema(title = "완료여부", description = "할 일의 완료여부를 나타냅니다.")
    private Comp_yn complete_yn;

    @Schema(title = "작성일", description = "최초로 등록한 날짜를 나타냅니다.")
    @JsonFormat(pattern = "yyyy/MM/dd HH:ss:mm")
    private LocalDateTime start_date;

    @Schema(title = "사용여부", description = "할 일에 대한 사용여부를 결정합니다.")
    private Resign_yn resign_yn;

    @NotBlank(message = "필수 입력 값")
    @Schema(title = "할 일 대상 반려동물 번호", description = "할 일과 반려동물은 1:1 관계입니다.")
    private int pet_num;

    @Schema(title = "완료자", description = "할 일을 완료한 사용자의 아이디를 나타냅니다.")
    private String complete_by;

    @Schema(title = "완료날짜", description = "할 일을 완료한 날짜를 나타냅니다.")
    @JsonFormat(pattern = "yyyy/MM/dd HH:ss:mm")
    private LocalDateTime complete_at;
}
