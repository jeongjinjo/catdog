package com.example.catdog.memo;

import com.example.catdog.enum_column.Resign_yn;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Schema(description = "메모 유효성 검사를 위한 객체입니다.")
public class MemoDTO {

    @Schema(description = "메모 고유 번호")
    private int memo_num;

    @Schema(description = "메모 내용")
    @Size(max = 30, message = "메모는 최대 30자까지 가능합니다.")
    private String memo;

    @Schema(description = "메모 등록일자")
    @JsonFormat(pattern = "yyyy/MM/dd HH:ss:mm")
    private LocalDateTime date;

    @Schema(description = "메모 대상 반려동물 고유번호")
    private int pet_num;

    @Schema(description = "사용여부")
    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;
}
