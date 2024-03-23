package com.example.catdog.careGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<CareGroup, Integer> {
    // NOTE 그룹의 마지막 그룹 번호 가져오기
    @Query(value = "SELECT new com.example.catdog.careGroup.CareGroup(cg.group_num) " +
                    "  FROM CareGroup cg " +
                    " ORDER BY cg.group_num DESC " +
                    " LIMIT 1")
    CareGroup findByLastGroupNumIsCareGroupType();
}
