package com.example.catdog.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//@Service
//@RequiredArgsConstructor
//public class MemberService {
//
//    private final MemberRepository memberRepository;
//
//    private final PasswordEncoder passwordEncoder;
//
//    public void registerMember(String id, String password, String nickname, String name) {
//        Member member = new Member();
//        member.setId(id);
//        member.setPassword(passwordEncoder.encode(password));
//        member.setNickname(nickname);
//        member.setName(name);
//        member.setResginYn(resgin_yn.N); // 기본값으로 'N' 설정
//
//        memberRepository.save(member);
//    }
//
//
//    public void registerMember(Member member) {
//    }
//}
