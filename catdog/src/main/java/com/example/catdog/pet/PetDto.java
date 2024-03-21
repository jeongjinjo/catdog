package com.example.catdog.pet;

import com.example.catdog.enum_column.Gender;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDto {

    private int pet_num;

    private String type;

    private String pet_name;

    private int pet_age;

    private float pet_kg;

    private String disease;

    private String resign_yn;

    private Gender gender;
}
