package com.example.catdog.member;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.MemberExcption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    // 내 정보 확인 ( eunae )
    public Member getInfo(String id) {
        Optional<Member> member = memberRepository.findByMemberId(id);

        if(member.isEmpty()) {
            throw new MemberExcption(ErrorCode.NOT_FOUND);
        }

        return member.get();
    }

    @Transactional
    // 내 정보 수정 ( eunae )
    public int myInfoUpdate(Member member, String passwordUpdate) {
        String password = passwordUpdate;
        Optional<Member> mem = memberRepository.findByMemberIdAndPassword(member.getMember_id(), member.getPassword());

        if(mem.isEmpty()) {
            throw new MemberExcption(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // 패스워드를 바꾸지 않고 다른 정보를 바꾸는거라면?
        if(passwordUpdate == null || passwordUpdate.equals("") || passwordUpdate == "") {
            password = member.getPassword();
        }

        int result = memberRepository.myInfoUpdate(
                                        member.getName(),
                                        member.getNickname(),
                                        password,
                                        member.getPhone_num(),
                                        member.getMember_id()
                        );
        return result;
    }

    // 회원탈퇴 ( eunae )
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

    // 비밀번호 확인 ( eunae )
    public Member pwCheck(String id, String pw) {
        Optional<Member> member = memberRepository.findByMemberIdAndPassword(id, pw);

        if(member.isEmpty()) {
            throw new MemberExcption(ErrorCode.ID_OR_PASSWORD_FAILED);
        }

        return member.get();
    }

    // 멤버 그룹 초대 ( eunae )
    public List<Member> memberGroupInvite(String member_id, String search_id) {
        return memberRepository.memberGroupInvite(member_id, search_id);
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
