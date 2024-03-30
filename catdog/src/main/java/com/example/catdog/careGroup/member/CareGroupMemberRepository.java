package com.example.catdog.careGroup.member;

import com.example.catdog.careGroup.CareGroup;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareGroupMemberRepository extends JpaRepository<CareGroupMember, Integer> {

    // JJanu
    @Query("SELECT cgm FROM CareGroupMember cgm WHERE cgm.member.member_id = :memberId")
    Optional<CareGroupMember> findByMemberId(@Param("memberId") String memberId);

    // NOTE 그룹번호를 기준으로 내가 속해있는 그룹 조회 ( eunae )
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

    // NOTE 로그인한 유저의 그룹 개수 확인 ( eunae )
    @Query("SELECT COUNT(*) " +
            "FROM CareGroupMember cgm " +
            "WHERE cgm.member.member_id = :member_id " +
            "AND cgm.resign_yn = 'N'")
    int countByMemberIdAndResignYn(@Param("member_id") String memberId);

    // NOTE 한 그룹 내에 몇명인지 확인 ( eunae )
    @Query(value = "SELECT COUNT(*) " +
                    " FROM CareGroupMember cgm " +
                    "WHERE cgm.careGroup.group_num = :group_num " +
                    "  AND cgm.member.resign_yn = 'N'")
    int countByGroupNumAndResignYn(@Param("group_num") int groupNum);

    // NOTE 그룹 내 멤버의 정보 확인하기 ( eunae )
    @Query(value = "SELECT cgm " +
                    " FROM CareGroupMember cgm " +
                    "WHERE cgm.careGroup.group_num = :group_num " +
                    "  AND cgm.member.member_id = :member_id")
    Optional<CareGroupMember> findByGroupNumAndMemberId(@Param("group_num") int group_num, @Param("member_id") String member_id);

    // NOTE 그룹 내 멤버 삭제 여부 수정하기 ( eunae )
    @Modifying
    @Transactional
    @Query(value = "UPDATE CareGroupMember cgm " +
                    "  SET cgm.resign_yn = 'Y' " +
                    "WHERE cgm.careGroup.group_num  = :group_num")
    void groupMemberResignYnUpdateAll(@Param("group_num") int groupNum);
}
