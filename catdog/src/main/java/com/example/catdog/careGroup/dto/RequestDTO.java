package com.example.catdog.careGroup.dto;

import com.example.catdog.careGroup.GroupDTO;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class RequestDTO {
    private GroupDTO groupDTO;
    // 그룹 등록을 위한 필드
    @Size(min = 1, max = 4)
    private List<String> member_id;
    @Size(max = 5)
    private List<Integer> pet_num;
    private String current_member_id;
}
