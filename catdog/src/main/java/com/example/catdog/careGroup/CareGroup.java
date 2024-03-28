package com.example.catdog.careGroup;

import com.example.catdog.enum_column.Resign_yn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "care_group")
@Schema(description = "그룹에 대한 정보입니다.")
public class CareGroup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Schema(title = "그룹 고유 번호", description = "그룹에 대한 고유 식별키를 표현합니다. **회원가입시, 그룹 1개가 생성됩니다.")
    private int group_num;

    @Column(length = 100)
    @Schema(title = "그룹명", description = "자유롭게 그룹명을 지정할 수 있습니다. **회원가입 시, 그룹명은 닉네임으로 지정됩니다.")
    private String group_name;

    @Schema(title = "그룹 사용여부", description = "그룹을 삭제하면 사용여부는 Y로 지정됩니다.")
    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    public CareGroup(int group_num) {
        this.group_num = group_num;
    }
}
