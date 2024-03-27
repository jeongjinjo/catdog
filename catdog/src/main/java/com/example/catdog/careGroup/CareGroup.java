package com.example.catdog.careGroup;

import com.example.catdog.enum_column.Resign_yn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Schema(description = "Group Table에 대한 내용입니다.")
@Table(name = "care_group")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CareGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int group_num;

    @Column(length = 100)
    private String group_name;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    public CareGroup(int group_num) {
        this.group_num = group_num;
    }
}
