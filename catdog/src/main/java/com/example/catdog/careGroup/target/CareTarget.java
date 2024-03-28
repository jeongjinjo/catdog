package com.example.catdog.careGroup.target;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "care_target")
@Schema(description = "그룹에 속해있는 반려동물에 대한 정보입니다.")
public class CareTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Schema(title = "식별번호", description = "자동으로 1씩 증가합니다.")
    private int care_num;

    @Column(nullable = false)
    @Schema(title = "그룹 번호", description = "사용자가 등록한 그룹의 고유번호입니다.")
    private int group_num;

    @Column(nullable = false)
    @Schema(title = "그룹 대상(반려동물) 번호", description = "사용자가 등록한 반려동물의 고유번호입니다.")
    private int pet_num;

    // 생성자
    public CareTarget(int group_num) {
        this.group_num = group_num;
    }

}
