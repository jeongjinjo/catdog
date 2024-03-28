package com.example.catdog.todo;

import com.example.catdog.enum_column.Comp_yn;
import com.example.catdog.enum_column.Resign_yn;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "todo")
@Schema(description = "할 일에 대한 정보입니다.")
public class Todo {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "할 일 고유번호", description = "자동으로 1씩 증가합니다.")
    private int todo_num;

    @Schema(title = "할 일 내용")
    private String todo;

    @Schema(title = "작성자", description = "최초로 등록한 회원 아이디를 나타냅니다.")
    private String start_id;

    @Schema(title = "완료여부", description = "할 일의 완료여부를 나타냅니다.")
    @Enumerated(EnumType.STRING)
    private Comp_yn complete_yn;

    @Schema(title = "작성일", description = "최초로 등록한 날짜를 나타냅니다.")
    @JsonFormat(pattern = "yyyy/MM/dd HH:ss:mm")
    private LocalDateTime start_date;

    @Schema(title = "사용여부", description = "할 일에 대한 사용여부를 결정합니다.")
    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    @Schema(title = "할 일 대상 반려동물 번호", description = "할 일과 반려동물은 1:1 관계입니다.")
    @Column(nullable = false)
    private int pet_num;

    @Schema(title = "완료자", description = "할 일을 완료한 사용자의 아이디를 나타냅니다.")
    private String complete_by;

    @Schema(title = "완료날짜", description = "할 일을 완료한 날짜를 나타냅니다.")
    @JsonFormat(pattern = "yyyy/MM/dd HH:ss:mm")
    private LocalDateTime complete_at;
}
