package com.example.catdog.careGroup.target;

import com.example.catdog.careGroup.CareGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CareTargetRepository extends JpaRepository<CareTarget, Integer> {
    // NOTE 펫 번호를 기준으로 그룹 번호를 조회 ( eunae )
    @Query(value = "SELECT new com.example.catdog.careGroup.target.CareTarget(ct.group_num) " +
                    " FROM CareTarget ct " +
                    " JOIN Pet p " +
                    "   ON ct.pet_num = p.pet_num  " +
                    "WHERE ct.pet_num = :pet_num")
    CareTarget getCareGroupNum(@Param("pet_num") int petNum);

    // NOTE 특정 그룹에 속해있는 반려동물의 모든 행을 조회 ( eunae )
    @Query(value = "SELECT ct" +
                    " FROM CareTarget ct " +
                    "WHERE ct.group_num = :group_num")
    List<CareTarget> findByGroupNumInPet(@Param("group_num") int groupNum);

    // NOTE 특정 그룹의 특정 반려동물의 정보를 조회 ( eunae )
    @Query(value = "SELECT ct" +
                    " FROM CareTarget ct " +
                    "WHERE ct.group_num = :group_num " +
                    "  AND ct.pet_num = :pet_num")
    CareTarget findByGroupNumInPetInformation(@Param("group_num") int groupNum, @Param("pet_num") int petNum);

    // NOTE 특정 그룹의 반려동물이 몇 마리가 존재하는지 조회 ( eunae )
    @Query(value = "SELECT COUNT(*) FROM CareTarget ct WHERE ct.group_num = :group_num")
    int getGroupInPetCount(@Param("group_num") int groupNum);

}
