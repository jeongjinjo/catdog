package com.example.catdog.pet;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetServiceTest {

    @Autowired private PetService service;
    @Test
    void petCreate() {
        Pet pet = Pet.builder()
                .pet_name("clcl")
                .age(10)
                .member_id("ninano")
                .resign_yn(Resign_yn.N)
                .type(Type.DOG)
                .build();

//        service.petCreate(pet);
    }
}