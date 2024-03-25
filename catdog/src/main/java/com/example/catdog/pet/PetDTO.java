package com.example.catdog.pet;

import com.example.catdog.enum_column.Gender;
import com.example.catdog.member.Member;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class PetDTO {

    private int pet_num;

    private String type;

    private String pet_name;

    private int pet_age;

    private float pet_kg;

    private String disease;

    private String resign_yn;

    private Gender gender;

    private int photo_num;
}
