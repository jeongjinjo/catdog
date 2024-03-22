package com.example.catdog.member;

import com.example.catdog.care_group.Care_group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {
    @Query(value = "SELECT new com.example.catdog.member.Member(m.member_id, m.password, m.name, m.nickname, m.phone_num, m.resign_yn) " +
            " FROM Member m " +
            "WHERE m.member_id = :member_id " +
            "  AND m.password = :password")
    public Optional<Member> findByMemberIdAndPassword(@Param("member_id") String member_id, @Param("password") String password);

    @Query(value = "SELECT new com.example.catdog.member.Member(" +
                                  "m.member_id" +
                                ", m.password" +
                                ", m.name" +
                                ", m.nickname" +
                                ", m.phone_num" +
                                ", m.resign_yn" +
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
    @Query(value = "SELECT c.group_key, m.nickname " +
            "FROM Member m " +
            "JOIN Care_group c ON m.member_id=c.member_id " +
            "WHERE c.group_key IN ( " +
            "   SELECT group_key " +
            "   FROM Care_group " +
            "   WHERE member_id=:id " +
            ") " +
            "AND m.resign_yn='N'"
    )
    public Optional<List<Object[]>> findByGroupMember(@Param("id") String id);

}
