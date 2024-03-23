package com.example.catdog.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    @Query(value = "SELECT new com.example.catdog.member.Member(" +
                        "m.member_id" +
                        ", m.password" +
                        ", m.nickname" +
                        ", m.name" +
                        ", m.resign_yn" +
                        ", m.phone_num" +
                    ") " +
            " FROM Member m " +
            "WHERE m.member_id = :member_id " +
            "  AND m.password = :password")
    public Optional<Member> findByMemberIdAndPassword(@Param("member_id") String member_id, @Param("password") String password);

    @Query(value = "SELECT new com.example.catdog.member.Member(" +
                                  "m.member_id" +
                                ", m.password" +
                                ", m.nickname" +
                                ", m.name" +
                                ", m.resign_yn" +
                                ", m.phone_num" +
                            ") " +
                    " FROM Member m " +
                    "WHERE m.member_id = :member_id")
    public Optional<Member> findByMemberId(@Param("member_id") String member_id);

    @Modifying
    @Query(value = "UPDATE Member m " +
            "SET " +
                "m.name = :name, " +
                "m.nickname = :nickname, " +
                "m.password = :password, " +
                "m.phone_num = :phone_num " +
            "WHERE m.member_id = :member_id")
    public int myInfoUpdate(
                            @Param("name") String name,
                            @Param("nickname") String nickname,
                            @Param("password") String password,
                            @Param("phone_num") String phone_num,
                            @Param("member_id") String member_id
                            );
    /**
     * 그룹 초대 ( 나 제외 )
     * @param member_id 로그인한 아이디, 검색에서 제외시킴
     * @param search_id 검색할 멤버 아이디
     */
    @Query(value = "SELECT m " +
                    " FROM Member m " +
                    "WHERE m.member_id NOT IN (:member_id) " +
                    "AND m.member_id LIKE CONCAT('%', :search_id, '%') " +
                    " OR m.nickname LIKE CONCAT('%', '치', '%') " +
                    " AND m.resign_yn = 'N'")
    public List<Member> memberGroupInvite(@Param("member_id") String member_id, @Param("search_id") String search_id);

    // 로그인 한 사람의 그룹별 회원닉네임 정보 ( gayoung )
    @Query(value = "SELECT m.nickname, cgm.careGroup.group_num " +
            "  FROM Member m " +
            "  JOIN CareGroupMember cgm " +
            "    ON m.member_id = cgm.member.member_id " +
            " WHERE cgm.careGroup.group_num IN ( " +
            " SELECT cgm2.careGroup.group_num " +
            "   FROM CareGroupMember cgm2 " +
            " WHERE cgm2.member.member_id = :id" +
            ")" +
            "   AND m.resign_yn = 'N'"
    )
    public Optional<List<Object[]>> findByGroupMember(@Param("id") String id);

}
