package com.example.catdog.care_group;

import com.example.catdog.common.Resign_yn;
import com.example.catdog.common.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Schema(description = "Group Table에 대한 내용입니다.")
public class Care_group {
    @Id
    @Column(nullable = false)
    private int group_num;

    @Column(length = 100)
    private String group_name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    @Column(length = 100)
    private String member_id;

    @Column(nullable = false)
    private int pet_num;

    private int group_key;
    private String name;
    private String nickname;

    public Care_group() {}

    public Care_group(int group_num, Role role, String member_id, int group_key, String name, String nickname) {
        this.group_num = group_num;
        this.role = role;
        this.member_id = member_id;
        this.group_key = group_key;
        this.name = name;
        this.nickname = nickname;
    }

}
