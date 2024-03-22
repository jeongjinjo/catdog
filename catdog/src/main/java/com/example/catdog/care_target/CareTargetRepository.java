package com.example.catdog.care_target;

import com.example.catdog.care_group.Care_group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CareTargetRepository extends JpaRepository<Care_group, Integer> {

    @Query(value = "SELECT new com.example.catdog.care_target.Care_target(ct.group_num) " +
                    " FROM Care_target ct " +
                    " JOIN Pet p " +
                    "   ON ct.pet_num = p.pet_num  " +
                    "WHERE ct.pet_num = :pet_num")
    public Care_target getCareGroupNum(@Param("pet_num") int pet_num);
}
