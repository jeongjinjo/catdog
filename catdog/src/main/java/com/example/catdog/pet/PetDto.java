package com.example.catdog.pet;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PetDto {

    private Long pet_num;

    @NotBlank(message = "필수 입력 값")
    private String type;

    @NotBlank(message = "필수 입력 값")
    private String pet_name;

    @NotBlank(message = "필수 입력 값")
    private int pet_age;

    @NotBlank(message = "필수 입력 값")
    private float pet_kg;

    //빈칸이 되면 건강한 뭔가ㅏ... 아이콘이 뜰 수 있나?
    private String disease;

    private String resign_yn;

    @NotBlank(message = "필수 입력 값")
    private Gender gender;

}
