package com.example.catdog.member;

import com.example.catdog.careGroup.member.CareGroupMember;
import com.example.catdog.careGroup.member.CareGroupMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final CareGroupMemberRepository careGroupMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String member_id) throws UsernameNotFoundException {
        Optional<Member> optionalMember = memberRepository.findById(member_id);
        Member member = optionalMember.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        Optional<CareGroupMember> optionalCareGroupMember = careGroupMemberRepository.findByMemberId(member_id);
        CareGroupMember careGroupMember = optionalCareGroupMember.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        GrantedAuthority authority = new SimpleGrantedAuthority(careGroupMember.getRole().toString());

        return new User(
                member.getMember_id(),
                member.getPassword(),
                Collections.singleton(authority)
        );
    }
}

