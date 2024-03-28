package com.example.catdog.careGroup.target;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "그룹 대상 유효성 검사를 위한 객체입니다.")
public class CareTargetDTO {
    @Schema(title = "식별번호")
    private int care_num;

    @NotBlank(message = "필수 입력 값")
    @Schema(title = "그룹 번호")
    private int group_num;

    @NotBlank(message = "필수 입력 값")
    @Schema(title = "그룹 대상(반려동물) 번호")
    private int pet_num;
}
