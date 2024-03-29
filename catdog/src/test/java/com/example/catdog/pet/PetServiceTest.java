package com.example.catdog.pet;

import com.example.catdog.enum_column.Gender;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class PetServiceTest {
    @Autowired PetService service;

    @Test
    void createPet() {
        String member_id = "jjanu";
        Pet pet = Pet.builder()
                    .type(Type.CAT)
                    .pet_name("chch")
                    .age(10)
                    .kg(10.2f)
                    .resign_yn(Resign_yn.N)
                    .member_id(member_id)
                    .gender(Gender.M).build();
        service.createPet(pet);
    }
}