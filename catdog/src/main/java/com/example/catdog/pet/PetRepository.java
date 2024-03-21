package com.example.catdog.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    //save,findById,findAll,deletedById 등의 메소드들이 자동으로 제공된다.

    // 그룹에 등록되지 않은 펫 확인하기 ( eunae )
    @Query(value = "SELECT p " +
            "FROM Pet p " +
            "WHERE NOT EXISTS (" +
            "    SELECT 1 " +
            "    FROM Care_target c " +
            "    WHERE c.pet_num = p.pet_num " +
            ") " +
            "AND p.member_id = :id"
    )
    public Optional<List<Pet>> findByGroupNotInPet(@Param("id") String id);

    // 로그인 한 사람의 그룹별 반려동물 정보 ( gayoung )
    @Query(value = "SELECT DISTINCT g.group_name, p.pet_name, p.gender, p.age, p.disease " +
            "FROM Pet p " +
            "JOIN Member m ON m.member_id=p.member_id " +
            "JOIN Care_target c ON c.pet_num=p.pet_num " +
            "JOIN Care_group g ON g.group_key=c.group_num " +
            "WHERE c.group_num IN ( " +
            "   SELECT a.group_key " +
            "   FROM Care_group a " +
            "   JOIN Care_target b ON a.group_key=b.group_num " +
            "   WHERE a.member_id = :id " +
            ")"
    )
    public Optional<List<Object[]>> findByGroupInfoPet(@Param("id") String id);
}
