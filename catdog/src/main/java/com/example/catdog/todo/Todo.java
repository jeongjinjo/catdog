package com.example.catdog.todo;

import com.example.catdog.common.Resign_yn;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Todo {
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int todo_num;

    private String todo;

    @Column(length = 50)
    private String comp_yn;

    private String start_id;

    @JsonFormat(pattern = "yyyy/MM/dd HH:ss:mm")
    private LocalDateTime start_date;

    @Column(length = 50)
    private Resign_yn resign_yn;

    @Column(nullable = false)
    private int group_num;
}
