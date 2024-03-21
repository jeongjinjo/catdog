package com.example.catdog.care_group;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Care_group, Integer> {
    // 그룹 계층 번호를 기준으로 내가 속해있는 그룹 조회
    @Query(value = "SELECT new com.example.catdog.care_group.Care_group(g.group_num, g.role, g.member_id, g.group_key, m.name, m.nickname, g.group_name, m.resign_yn)  " +
                    " FROM Care_group g " +
                    " JOIN Member m " +
                    "   ON g.member_id = m.member_id " +
                    " WHERE g.group_key IN ( " +
                        "      SELECT gr.group_key " +
                        "        FROM Care_group gr " +
                        "       WHERE gr.member_id = :member_id " +
                        "         AND gr.resign_yn = 'N'" +
                    ")")
    Optional<List<Care_group>> findByClassNumGrouped(@Param("member_id") String memberId);
}
