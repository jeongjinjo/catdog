package com.example.catdog.pet;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PetRepository extends JpaRepository<Pet,Long> {
    //save,findById,findAll,deletedById 등의 메소드들이 자동으로 제공된다.

}
