package com.example.catdog.todo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class TodoDetailDTO {
    @NotBlank(message = "필수 입력 값")
    private int todo_detail_num;

    @NotBlank(message = "필수 입력 값")
    private String comp_id;

    private LocalDateTime end_date;

    @NotBlank(message = "필수 입력 값")
    private int todo_num;
}
