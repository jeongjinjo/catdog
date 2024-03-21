package com.example.catdog.member;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.MemberExcption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 내 정보 확인
    public Member getInfo(String id) {
        Optional<Member> member = memberRepository.findByMemberId(id);

        if(member.isEmpty()) {
            throw new MemberExcption(ErrorCode.NOT_FOUND);
        }

        return member.get();
    }

    // 내 정보 수정
    public Member myInfoUpdate(Member member, String passwordUpdate) {
        Optional<Member> mem = memberRepository.findBymemberIdAndPassword(member.getMember_id(), member.getPassword());

        if(mem.isEmpty()) {
            throw new MemberExcption(ErrorCode.PASSWORD_NOT_MATCH);
        }

        Member db = memberRepository.save(mem.get());

        db.setName(member.getName());
        db.setPassword(passwordUpdate);
        db.setNickname(member.getNickname());
        return db;
    }


    // 회원탈퇴
    public Member signOut(String id) {
        Optional<Member> member = memberRepository.findByMemberId(id);

        if(member.isEmpty()) {
            throw new MemberExcption(ErrorCode.NOT_FOUND);
        }

        System.out.println(member.get());

        Member memberUpdate = member.get();
        memberUpdate.setName("탈퇴한 회원");
        memberUpdate.setResign_yn(Resign_yn.Y);

        Member db = memberRepository.save(memberUpdate);

        return db;
    }

    // 비밀번호 확인
    public Member pwCheck(String id, String pw) {
        Optional<Member> member = memberRepository.findBymemberIdAndPassword(id, pw);

        if(member.isEmpty()) {
            throw new MemberExcption(ErrorCode.PASSWORD_NOT_MATCH);
        }

        return member.get();
    }

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


}
