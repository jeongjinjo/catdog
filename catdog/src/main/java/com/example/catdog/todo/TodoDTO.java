package com.example.catdog.todo;

import com.example.catdog.enum_column.Comp_yn;
import com.example.catdog.enum_column.Resign_yn;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class TodoDTO {
    @NotBlank(message = "필수 입력 값")
    private int todo_num;

    @Size(max = 30, message = "todo는 최대 30자까지만 입력이 가능합니다.")
    private String todo;

    private String start_id;

    private Comp_yn complete_yn;

    private LocalDateTime start_date;

    private Resign_yn resign_yn;

    @NotBlank
    private int pet_num;

    private String complete_by;

    private LocalDateTime complete_at;
}
