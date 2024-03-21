package com.example.catdog.care_group;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
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

    @Column(length = 100)
    private String member_id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    private int group_key;

    // member JOIN시 필요한 필드명
    private String name;
    private String nickname;

    public Care_group() {}

    public Care_group(int group_num, Role role, String member_id, int group_key
                        , String name, String nickname, String group_name, Resign_yn resign_yn) {
        this.group_num = group_num;
        this.role = role;
        this.member_id = member_id;
        this.group_key = group_key;
        this.name = name;
        this.nickname = nickname;
        this.group_name = group_name;
    }

}
