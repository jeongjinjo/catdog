package com.example.catdog.careTarget;

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
public class CareTarget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int key;
    private int group_num;
    private int pet_num;
}
