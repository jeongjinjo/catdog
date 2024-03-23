package com.example.catdog.pet;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
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
            " FROM Pet p " +
            "WHERE NOT EXISTS (" +
            "    SELECT 1 " +
            "      FROM CareTarget c " +
            "     WHERE c.pet_num = p.pet_num " +
            ") " +
            "  AND p.member_id = :id"
    )
    public List<Pet> findByGroupNotInPet(@Param("id") String id);

    // 로그인 한 사람의 그룹별 반려동물 정보 ( gayoung )
//    @Query(value = "SELECT DISTINCT c.group_num, p.pet_name, p.gender, p.age, p.disease " +
//            "FROM Pet p " +
//            "JOIN Member m ON m.member_id=p.member_id " +
//            "JOIN CareTarget c ON c.pet_num=p.pet_num " +
//            "JOIN CareGroup g ON g.group_key=c.group_num " +
//            "WHERE c.group_num IN ( " +
//            "   SELECT a.group_key " +
//            "   FROM CareGroup a " +
//            "   JOIN CareTarget b ON a.group_key=b.group_num " +
//            "   WHERE a.member.member_id = :id " +
//            "     AND p.resign_yn = 'N'" +
//            ")"
//    )
//    public Optional<List<Object[]>> findByGroupInfoPet(@Param("id") String id);


    // 로그인 한 사람의 그룹별 반려동물 정보 ( eunae )
    @Query(value = "SELECT new com.example.catdog.pet.Pet(p.pet_num, p.type, p.type_detail, p.pet_name, p.age, p.kg, p.disease, p.resign_yn, p.gender, p.member_id) " +
            " FROM Pet p " +
            "  JOIN CareTarget ct " +
            "    ON ct.pet_num = p.pet_num " +
            " WHERE ct.group_num IN (" +
            "   SELECT cg.group_num " +
            "     FROM CareGroup cg " +
            "     JOIN CareTarget ct2 " +
            "       ON cg.group_num = ct2.group_num " +
            "     JOIN CareGroupMember cgm " +
            "       ON cgm.careGroup.group_num = cg.group_num " +
            "    WHERE cgm.member.member_id = :member_id " +
            "      AND cgm.resign_yn = 'N' " +
            ")  " +
            "AND p.resign_yn = 'N'"
    )
    public List<Pet> getGroupInfoPet(@Param("member_id") String member_id);

}
