package com.example.catdog.careGroup.member;

import com.example.catdog.careGroup.CareGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareGroupMemberRepository extends JpaRepository<CareGroupMember, Integer> {
    // 그룹번호를 기준으로 내가 속해있는 그룹 조회 ( eunae )
    @Query(value = "SELECT cgm " +
                    " FROM CareGroupMember cgm " +
                    " JOIN CareGroup cg " +
                    "   ON cgm.careGroup.group_num = cg.group_num " +
                    " JOIN Member m " +
                    "   ON cgm.member.member_id = m.member_id " +
                    "WHERE cgm.careGroup.group_num IN (" +
                        " SELECT cgmm.careGroup.group_num " +
                        "   FROM CareGroupMember cgmm " +
                        "  WHERE cgmm.resign_yn = 'N' " +
                        "    AND cgmm.member.member_id = :member_id" +
                    ")"
            )
    Optional<List<CareGroupMember>> findByGroup(@Param("member_id") String memberId);

    // 로그인한 유저의 그룹 개수 확인 ( eunae )
    @Query("SELECT COUNT(*) " +
            "FROM CareGroupMember cgm " +
            "WHERE cgm.member.member_id = :member_id " +
            "AND cgm.resign_yn = 'N'")
    int countByMemberIdAndResignYn(@Param("member_id") String memberId);

    // 한 그룹 내에 몇명인지 확인 ( eunae )
    @Query(value = "SELECT COUNT(*) " +
                    " FROM CareGroupMember cgm " +
                    "WHERE cgm.careGroup.group_num = :group_num " +
                    "  AND cgm.member.resign_yn = 'N'")
    int countByGroupNumAndResignYn(@Param("group_num") int groupNum);

}
