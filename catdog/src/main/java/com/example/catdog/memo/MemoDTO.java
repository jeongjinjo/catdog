package com.example.catdog.memo;

import com.example.catdog.enum_column.Resign_yn;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MemoDTO {
    private int memo_num;

    private String memo;

    @JsonFormat(pattern = "yyyy/MM/dd HH:ss:mm")
    private LocalDateTime date;

    private int pet_num;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;
}
