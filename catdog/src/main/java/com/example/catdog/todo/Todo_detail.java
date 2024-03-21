package com.example.catdog.todo;

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
public class Todo_detail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int todo_detail_num;

    private String comp_id;

    @JsonFormat(pattern = "yyyy/MM/dd HH:ss:mm")
    private LocalDateTime end_date;

    @Column(nullable = false)
    private int todo_num;
}
