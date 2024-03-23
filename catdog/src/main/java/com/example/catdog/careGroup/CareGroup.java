package com.example.catdog.careGroup;

import com.example.catdog.enum_column.Resign_yn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Schema(description = "Group Table에 대한 내용입니다.")
@Table(name = "care_group")
public class CareGroup {
    @Id
    @Column(nullable = false)
    private int group_num;

    @Column(length = 100)
    private String group_name;

//    @OneToOne
//    @JoinColumn(name="member_id", unique = true)
//    private Member member;

//    @Enumerated(EnumType.STRING)
//    private Role role;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

//    private int group_key;
}
