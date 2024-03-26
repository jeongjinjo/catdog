package com.example.catdog.member;

import com.example.catdog.careGroup.CareGroup;
import com.example.catdog.careGroup.GroupRepository;
import com.example.catdog.careGroup.member.CareGroupMember;
import com.example.catdog.careGroup.member.CareGroupMemberRepository;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.MemberExcption;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final CareGroupMemberRepository careGroupMemberRepository;
    private final GroupRepository groupRepository;

    public boolean changePassword(String member_id, String newPassword) {
        Optional<Member> optionalMember = memberRepository.findById(member_id);
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            member.setPassword(passwordEncoder.encode(newPassword));
            memberRepository.save(member);
            return true;
        }
        return false;
    }

    public boolean passwordAuthenticate(String member_id, String name, String phone_num) {
        // 회원 아이디로 회원 정보 조회
        Optional<Member> optionalMember = memberRepository.findById(member_id);

        // 회원이 존재하고 이름과 전화번호가 일치하는지 확인
        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();
            if (member.getName().equals(name) && member.getPhone_num().equals(phone_num)) {
                return true; // 인증 성공
            }
        }
        return false; // 인증 실패
    }

    public String findMemberIdByNameAndPhoneNum(String name, String phone_num) {
        Optional<Member> optionalMember = memberRepository.findByNameAndPhoneNum(name, phone_num);
        return optionalMember.map(Member::getMember_id).orElse(null);
    }

    public boolean authenticate(String member_id, String password) {
        // 회원 아이디로 회원 정보 조회
        Member member = memberRepository.findById(member_id).orElse(null);

        // 회원이 존재하고, 입력한 비밀번호가 일치하는지 확인
        return member != null && passwordEncoder.matches(password, member.getPassword());
    }


    // 회원가입 ( jjanu )
    @Transactional
    public Member signup(Member member) {
        // 암호화된 비밀번호 설정

        member.setPassword(passwordEncoder.encode(member.getPassword()));
        member.setResign_yn(Resign_yn.N);

        // 회원 정보 저장
        Member savedMember = memberRepository.save(member);

        // 회원이 속한 그룹 정보 저장
        String groupName = savedMember.getNickname();// group_name에 nickname 값 설정
        Role role = Role.ADMIN; // role은 ADMIN으로 설정

        CareGroup careGroup = new CareGroup();
        careGroup.setGroup_name(groupName);
        careGroup.setResign_yn(Resign_yn.N);
        groupRepository.save(careGroup);

        // CareGroupMember 엔터티 생성 및 저장
        CareGroupMember careGroupMember = new CareGroupMember();
        careGroupMember.setCareGroup(careGroup);
        careGroupMember.setMember(savedMember);
        careGroupMember.setRole(role);
        careGroupMember.setResign_yn(Resign_yn.N);

        careGroupMemberRepository.save(careGroupMember);

        return savedMember;
    }


    // 내 정보 확인 ( eunae )
    public Member getInfo(String id) {
        Optional<Member> member = memberRepository.findByMemberId(id);

        if (member.isEmpty()) {
            throw new MemberExcption(ErrorCode.NOT_FOUND);
        }

        return member.get();
    }

    @Transactional
    // 내 정보 수정 ( eunae )
    public int myInfoUpdate(Member member, String passwordUpdate) {
        String password = passwordUpdate;
        Optional<Member> mem = memberRepository.findByMemberIdAndPassword(member.getMember_id(), member.getPassword());

        if (mem.isEmpty()) {
            throw new MemberExcption(ErrorCode.PASSWORD_NOT_MATCH);
        }

        // 패스워드를 바꾸지 않고 다른 정보를 바꾸는거라면?
        if (passwordUpdate == null || passwordUpdate.equals("") || passwordUpdate == "") {
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

        if (member.isEmpty()) {
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

        if (member.isEmpty()) {
            throw new MemberExcption(ErrorCode.ID_OR_PASSWORD_FAILED);
        }

        return member.get();
    }

    // 멤버 그룹 초대 ( eunae )
    public List<Member> memberGroupInvite(String member_id, String search_id) {
        return memberRepository.memberGroupInvite(member_id, search_id);
    }


}
