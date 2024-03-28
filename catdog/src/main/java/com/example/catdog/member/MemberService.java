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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Transactional
    public Member signup(MemberDTO memberDTO) {

        Member member = new Member();
        member.setMember_id(memberDTO.getMember_id());
        member.setPassword(passwordEncoder.encode(memberDTO.getPassword()));
        member.setNickname(memberDTO.getNickname());
        member.setName(memberDTO.getName());
        member.setResign_yn(Resign_yn.N);
        member.setPhone_num(memberDTO.getPhone_num());

        Member savedMember = memberRepository.save(member);

        String groupName = memberDTO.getNickname();
        Role role = Role.admin;

        CareGroup careGroup = new CareGroup();
        careGroup.setGroup_name(groupName);
        careGroup.setResign_yn(Resign_yn.N);
        groupRepository.save(careGroup);

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
        // CHECK 0. 회원이 존재하지 않는 경우 예외 처리
        Optional<Member> dbInMember = memberRepository.findByMemberId(member.getMember_id());
        if(dbInMember.isEmpty()) {
            throw new MemberExcption(ErrorCode.NOT_FOUND);
        }

        // CHECK 1. 입력한 패스워드와 DB에 저장된 패스워드가 동일한지?
        boolean check = passwordEncoder.matches(member.getPassword(),dbInMember.get().getPassword());
        if(!check) {
            throw new MemberExcption(ErrorCode.PASSWORD_NOT_MATCH);
        }
        // CHECK 2. 동일하다면 패스워드를 바꿀건지?
        String password = "";
        // CHECK 2.1. 패스워드를 바꾸지 않고 다른 정보를 바꾸는거라면?
        if (passwordUpdate == null ||  passwordUpdate.isEmpty()) {
            // 패스워드 변경이 아닌 경우, 기존 패스워드를 사용
            password = dbInMember.get().getPassword();
        // CHECK 2.2. 패스워드를 바꿀 생각이라면?
        } else {
            // 패스워드 변경인 경우, 새로운 비밀번호를 암호화하여 사용
            password = passwordEncoder.encode(passwordUpdate);
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
    public int signOut(String id) {
        int result = 0;
        Optional<Member> member = memberRepository.findByMemberId(id);
        if (member.isEmpty()) {
            result = -1;
            throw new MemberExcption(ErrorCode.NOT_FOUND);
        }
        Member memberUpdate = member.get();
        memberUpdate.setName("탈퇴한 회원");
        memberUpdate.setResign_yn(Resign_yn.Y);
        Member db = memberRepository.save(memberUpdate);

        if(db != null) {
            result = 1;
        }
        return result;
    }

    // NOTE 비밀번호 확인 ( eunae ) CHECK 03.27 수정완료
    public int pwCheck(String id, String pw) {
        int result = 0;
        Optional<Member> memberId = memberRepository.findByMemberId(id);
        boolean check = passwordEncoder.matches(pw, memberId.get().getPassword());

        if(!check) {
            result = -1;
            throw new MemberExcption(ErrorCode.PASSWORD_NOT_MATCH);
        }
//        Optional<Member> member = memberRepository.findByMemberIdAndPassword(id, pw);
//        if (member.isEmpty()) {
//            throw new MemberExcption(ErrorCode.ID_OR_PASSWORD_FAILED);
//        }
        result = 1;
        return result;
    }

    // 멤버 그룹 초대 ( eunae )
    public List<Member> memberGroupInvite(String member_id, String search_id) {
        return memberRepository.memberGroupInvite(member_id, search_id);
    }


}
