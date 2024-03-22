package com.example.catdog.pet;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PetRepositoryTest {
    @Autowired
    private PetRepository petRepository;

    @Test
    public void test() {
        List<Pet> list = petRepository.getGroupInfoPet("123123");

        for(Pet o : list) {
            System.out.println(o);

        }

    }
}