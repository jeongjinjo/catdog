package com.example.catdog.member;

import com.example.catdog.jwt.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MemberController {
    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberDetailsService memberDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(@RequestBody Member member) {
        boolean authenticated = memberService.passwordAuthenticate(
                member.getMember_id(), member.getName(), member.getPhone_num());
        if (authenticated) {
            return ResponseEntity.ok("인증성공, 비밀번호 변경 가능");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("인증 실패, 인증 정보 재확인 요청");
        }
    }

    @PutMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        String member_id = changePasswordRequest.getMember_id();
        String newPassword = changePasswordRequest.getNewPassword();
        String confirmPassword = changePasswordRequest.getConfirmPassword();
        if (newPassword.equals(confirmPassword)) {
            boolean success = memberService.changePassword(member_id, newPassword);
            if (success) {
                return ResponseEntity.ok("패스워드 변경 성공");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("패스워드 변경 실패");
            }
        } else {
            return ResponseEntity.badRequest().body("새비밀번호와 비밀번호확인이 일치하지 않음");
        }
    }

    @GetMapping("/findId")
    public String findMemberIdByNameAndPhoneNum(@RequestBody Member member) {
        return memberService.findMemberIdByNameAndPhoneNum(member.getName(), member.getPhone_num());
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Member member) {
        String memberId = member.getMember_id();
        String password = member.getPassword();

        // 회원 인증
        boolean isAuthenticated = memberService.authenticate(memberId, password);
        if (!isAuthenticated) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 실패");
        }

        // JWT 토큰 생성
        UserDetails userDetails = memberDetailsService.loadUserByUsername(memberId);
        String token = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }

    @PostMapping("signUp")
    public ResponseEntity<String> signup(@RequestBody Member member) {
        memberService.signup(member);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @PostMapping("checkId")
    public ResponseEntity<String> checkId(@RequestBody Member member) {
        boolean checkIDDuplicate = memberRepository.existsById(member.getMember_id());

        if (checkIDDuplicate) {
            return ResponseEntity.ok("중복된 아이디입니다");
        } else {
            return ResponseEntity.ok("사용가능한 아이디 입니다");
        }
    }

    @PostMapping("checkNick")
    public ResponseEntity<String> checkNick(@RequestBody Member member) {
        boolean checkNickDuplicate = memberRepository.existsByNickname(member.getNickname());

        if (checkNickDuplicate) {
            return ResponseEntity.ok("중복된 닉네임 입니다");
        } else {
            return ResponseEntity.ok("사용가능한 닉네임 입니다");
        }
    }

    @PostMapping("checkPhone")
    public ResponseEntity<String> checkPhone(@RequestBody Member member) {
        int checkPhoneDuplicate = memberRepository.countByPhoneNum(member.getPhone_num());

        if (checkPhoneDuplicate > 0) {
            return ResponseEntity.ok("중복된 전화번호 입니다");
        } else {
            return ResponseEntity.ok("사용가능한 전화번호 입니다");
        }
    }


}
