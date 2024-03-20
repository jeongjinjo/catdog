package com.example.catdog.group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<CareGroup, Integer> {
    // 클래스 번호로 내가 속해있는 그룹 조회
    @Query(value = "SELECT new com.example.catdog.group.CareGroup(g.group_num, g.role, g.member_id, g.group_class, m.name, m.nickname)  " +
            "FROM CareGroup g " +
            "JOIN Member m " +
            "ON g.member_id = m.id " +
            "WHERE g.group_class IN ( " +
            "      SELECT gr.group_class " +
            "      FROM CareGroup gr " +
            "      WHERE gr.member_id = :member_id" +
            "   )")
    Optional<List<CareGroup>> findByClassNumGrouped(@Param("member_id") String memberId);
}
