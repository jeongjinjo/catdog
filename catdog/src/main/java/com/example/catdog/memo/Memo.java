package com.example.catdog.memo;

import com.example.catdog.enum_column.Resign_yn;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Memo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int memo_num;

    private String memo;

    @JsonFormat(pattern = "yyyy/MM/dd HH:ss:mm")
    private LocalDateTime date;

    private int pet_num;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;
}
