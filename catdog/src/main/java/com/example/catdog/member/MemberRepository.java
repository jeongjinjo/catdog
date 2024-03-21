package com.example.catdog.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

//    Optional<Member> findById(String id);

    // SELECT ... FROM member WHERE member_id = ? AND password = ?
    @Query(value = "SELECT new com.example.catdog.member.Member(m.member_id, m.password, m.name, m.nickname, m.phone_num, m.resign_yn) " +
                    " FROM Member m " +
                    "WHERE m.member_id = :member_id " +
                    "  AND m.password = :password")
    public Optional<Member> findBymemberIdAndPassword(@Param("member_id") String member_id, @Param("password") String password);

    @Query(value = "SELECT new com.example.catdog.member.Member(m.member_id, m.password, m.name, m.nickname, m.phone_num, m.resign_yn) " +
                    " FROM Member m " +
                    "WHERE m.member_id = :member_id")
    public Optional<Member> findByMemberId(@Param("member_id") String member_id);



}
