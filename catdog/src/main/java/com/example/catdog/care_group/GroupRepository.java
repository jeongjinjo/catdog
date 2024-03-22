package com.example.catdog.care_group;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.MemberExcption;
import com.example.catdog.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Care_group, Integer> {
    // 그룹 계층 번호를 기준으로 내가 속해있는 그룹 조회
    @Query(value = "SELECT g  " +
                    " FROM Care_group g " +
                    " JOIN Member m" +
                    "   ON g.member.member_id = m.member_id  " +
                    " WHERE g.group_key IN ( " +
                        "      SELECT gr.group_key " +
                        "        FROM Care_group gr " +
                        "       WHERE gr.member.member_id = :member_id " +
                        "         AND gr.resign_yn = 'N'" +
                    ")")
    Optional<List<Care_group>> findByClassNumGrouped(@Param("member_id") String memberId);

    @Query(value = "SELECT MAX(cg.group_num) + 1 FROM Care_group cg")
    Integer findNextGroupNum();

    // 로그인한 유저의 그룹 개수 확인 ( eunae )
    @Query("SELECT COUNT(cg) FROM Care_group cg " +
            "WHERE cg.member.member_id = :member_id " +
            "  AND cg.resign_yn = 'N'")
    Long countByMemberIdAndResignYn(@Param("member_id") String memberId);

    /** 등록한 그룹 찾기 ( eunae )
     *
     * @param memberId 권한이 HOST인 member_id(로그인 한 유저)
     * @param groupNum 등록된 그룹의 그룹 번호
     * @return
     */
    @Query(value = "SELECT cg" +
                    " FROM Care_group cg " +
                    "WHERE cg.member.member_id = :memberId" +
                    "  AND cg.group_num = :groupNum " +
                    "  AND cg.role = 'HOST'")
    public Optional<Care_group> findGoupHostInfo(@Param("memberId") String memberId, @Param("groupNum") int groupNum);

}
