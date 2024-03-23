package com.example.catdog.careGroup.target;

import com.example.catdog.careGroup.CareGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CareTargetRepository extends JpaRepository<CareGroup, Integer> {

    @Query(value = "SELECT new com.example.catdog.careGroup.target.CareTarget(ct.group_num) " +
                    " FROM CareTarget ct " +
                    " JOIN Pet p " +
                    "   ON ct.pet_num = p.pet_num  " +
                    "WHERE ct.pet_num = :pet_num")
    public CareTarget getCareGroupNum(@Param("pet_num") int pet_num);
}
