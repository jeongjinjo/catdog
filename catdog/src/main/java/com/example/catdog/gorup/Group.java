package com.example.catdog.gorup;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
@Schema(description = "Group Table에 대한 내용입니다.")
public class Group {
    @Id
    @Column(nullable = false)
    private int group_num;

    @Column(length = 255)
    private String group_name;

    @Column(length = 50)
    private String role;

    @Column(length = 10)
    private String resign_yn;

    @Column(length = 200, nullable = false)
    private String id;

    @Column(nullable = false)
    private int pet_num;
}
