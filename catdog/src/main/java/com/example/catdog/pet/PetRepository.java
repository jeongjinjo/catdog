package com.example.catdog.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    //save,findById,findAll,deletedById 등의 메소드들이 자동으로 제공된다.

    // 그룹에 등록되지 않은 펫 확인하기 ( eunae )
    @Query(value = "SELECT p " +
            "FROM Pet p " +
            "WHERE NOT EXISTS (" +
            "    SELECT 1 " +
            "    FROM CareTarget c " +
            "    WHERE c.pet_num = p.pet_num " +
            ") " +
            "AND p.id = :id"
    )
    public Optional<List<Pet>> findByGroupNotInPet(@Param("id") String id);

}
