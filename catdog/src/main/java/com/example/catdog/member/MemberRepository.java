package com.example.catdog.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, String> {

//    Optional<Member> findById(String id);

    // SELECT ... FROM member WHERE id = ? AND password = ?
    public Optional<Member> findByIdAndPassword(String id, String pw);

}
