package com.example.catdog.careGroup.target;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "care_target")
public class CareTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int care_num;

    private int group_num;

    private int pet_num;

    // 생성자
    public CareTarget() {}

    public CareTarget(int group_num) {
        this.group_num = group_num;
    }

}
