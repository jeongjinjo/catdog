package com.example.catdog.pet.photo;

import com.example.catdog.enum_column.Resign_yn;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "photo")
@Schema(description = "반려동물 사진에 대한 정보입니다.")
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @Schema(title = "사진 고유번호", description = "자동으로 1씩 증가합니다.")
    private int photo_num;

    @Schema(title = "사진 경로", description = "사진이 저장된 경로를 저장합니다.")
    private String route;

    @Schema(title = "사진 사용여부")
    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;
}
