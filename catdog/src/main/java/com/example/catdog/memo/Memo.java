package com.example.catdog.memo;

import com.example.catdog.enum_column.Resign_yn;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "memo")
@Schema(description = "반려동물에 대한 당일 메모입니다.")
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "메모 고유번호", description = "자동으로 1씩 증가합니다.")
    private int memo_num;

    @Schema(title = "메모 내용", description = "메모의 내용을 작성합니다.")
    private String memo;

    @Schema(title = "메모 작성일", description = "최초 작성 시, 자동으로 등록되게 해두었고, 수정할 땐 직접 값을 넣어주어야 합니다.")
    @JsonFormat(pattern = "yyyy/MM/dd HH:ss:mm")
    private LocalDateTime date;

    @Schema(title = "메모 대상 반려동물 번호", description = "반려동물과 메모는 1:1 관계입니다.")
    private int pet_num;

    @Schema(title = "사용여부", description = "메모 사용에 대한 여부를 표현합니다.")
    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;
}
