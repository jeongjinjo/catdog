package com.example.catdog.member;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberRepository memberRepository;

    @PostMapping("/signUp")
    public String signUpMember (@RequestBody Member member) {
        memberRepository.save(member);
        return "회원가입 성공";
    }


}
