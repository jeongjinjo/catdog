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

//    Optional<Member> findById(String id);

    // SELECT ... FROM member WHERE member_id = ? AND password = ?
    @Query(value = "SELECT new com.example.catdog.member.Member(m.member_id, m.password, m.name, m.nickname, m.phone_num, m.resign_yn) " +
            " FROM Member m " +
            "WHERE m.member_id = :member_id " +
            "  AND m.password = :password")
    public Optional<Member> findByMemberIdAndPassword(@Param("member_id") String member_id, @Param("password") String password);

    @Query(value = "SELECT new com.example.catdog.member.Member(m.member_id, m.password, m.name, m.nickname, m.phone_num, m.resign_yn) " +
            " FROM Member m " +
            "WHERE m.member_id = :member_id")
    public Optional<Member> findByMemberId(@Param("member_id") String member_id);

    //(clearAutomatically = true)
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
