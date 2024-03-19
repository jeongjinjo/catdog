package com.example.catdog.gorup;

import com.example.catdog.common.Resign_yn;
import com.example.catdog.common.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Schema(description = "Group Table에 대한 내용입니다.")
public class Group {
    @Id
    @Column(nullable = false)
    private int group_num;

    private String group_name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    @Column(length = 200, nullable = false)
    private String id;

    @Column(nullable = false)
    private int pet_num;
}
