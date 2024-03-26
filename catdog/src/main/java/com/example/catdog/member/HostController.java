package com.example.catdog.member;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HostController {

    private final MemberService memberService;

    @GetMapping("/findId")
    public String findMemberIdByNameAndPhoneNum(@RequestParam String name, @RequestParam String phone_num) {
        return memberService.findMemberIdByNameAndPhoneNum(name, phone_num);
    }

    // 토큰 인증 확인하기
    @GetMapping("host")
    public String example(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        // Authorization 헤더 확인
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String accessToken = authorizationHeader.substring(7); // "Bearer " 이후의 토큰 부분만 추출
            // 여기서 accessToken을 확인하거나 다른 작업 수행
        }
        // 다른 로직 수행
        return "인증 확인";
    }

    @GetMapping("user")
    public String getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "로그인 한사람 없음";
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return "User: " + userDetails.getUsername() + "\n" + "authority: " + userDetails.getAuthorities();
    }
}
