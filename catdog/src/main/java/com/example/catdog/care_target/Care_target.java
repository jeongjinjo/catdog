package com.example.catdog.care_target;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
public class Care_target {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int care_num;
    private int group_num;
    private int pet_num;
}
