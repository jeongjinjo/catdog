package com.example.catdog.careGroup.dto;

import com.example.catdog.careGroup.GroupDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
@Schema(description = "그룹, 그룹에 속할 멤버, 그룹에 속할 반려동물을 위해 만들어진 DTO입니다.")
public class RequestDTO {
    @Schema(title = "그룹 정보", description = "care_group에 대한 유효성 검사를 진행한 후 Entity로 전달")
    private GroupDTO groupDTO;
    // 그룹 등록을 위한 필드
    @Size(min = 1, max = 4)
    @Schema(title = "그룹에 속한 회원 수", description = "care_group_member 테이블과 직결되어있는 필드입니다.")
    private List<String> member_id;

    @Size(max = 5)
    @Schema(title = "그룹에 속한 반려동물 수", description = "care_target 테이블과 직결되어있는 필드입니다.")
    private List<Integer> pet_num;
    private String current_member_id;
}
