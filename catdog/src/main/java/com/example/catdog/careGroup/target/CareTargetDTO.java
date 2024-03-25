package com.example.catdog.careGroup.target;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CareTargetDTO {
    private int care_num;
    private int group_num;
    private int pet_num;
}
