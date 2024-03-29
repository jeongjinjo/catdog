package com.example.catdog.member;

import com.example.catdog.jwt.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "MemberController", description = "Member 회원가입, 로그인 관련 controller 입니다")
public class MemberController {
    private final JwtUtil jwtUtil;
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final MemberDetailsService memberDetailsService;

    @Operation(summary = "비밀번호 찾기전 인증과정",
            description = "member_id, name, phone_num 정보를 받아서 db와 대조한뒤에 인증성공 여부를 확인")

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

    @Operation(summary = "비밀번호 찾기 기능",
            description = "새비밀번호와 비밀번호확인을 통해서 일치하면 비밀번호 변경을 해서 암호화한뒤 db에 저장")
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

    @Operation(summary = "아이디 찾기 기능",
            description = "name과 phone_num을 인증받아서 해당정보의 ID를 출력")
    @GetMapping("/findId")
    public String findMemberIdByNameAndPhoneNum(@RequestBody Member member) {
        return memberService.findMemberIdByNameAndPhoneNum(member.getName(), member.getPhone_num());
    }

    @Operation(summary = "로그인 기능",
            description = "Member_id와 password의 정보를 받아서 로그인한뒤 jwt토큰을 발행")
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberDTO memberDTO) {
        String memberId = memberDTO.getMember_id();
        String password = memberDTO.getPassword();

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

    @Operation(summary = "회원가입 기능",
            description = "회원가입시 member, careGroup, careGroupMember 테이블에 데이터를 저장")
    @PostMapping("signUp")
    public ResponseEntity<String> signup(@RequestBody @Valid MemberDTO memberDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시 처리
            StringBuilder errorMessage = new StringBuilder();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMessage.append(error.getDefaultMessage());
            }
            return ResponseEntity.badRequest().body(errorMessage.toString());
        }

        memberService.signup(memberDTO);
        return ResponseEntity.ok("회원가입이 완료되었습니다.");
    }

    @Operation(summary = "아이디 중복체크",
            description = "입력한 아이디를 db와 대조한뒤 중복 체크")
    @PostMapping("checkId")
    public ResponseEntity<String> checkId(@RequestBody Member member) {
        boolean checkIDDuplicate = memberRepository.existsById(member.getMember_id());

        if (checkIDDuplicate) {
            return ResponseEntity.ok("중복된 아이디입니다");
        } else {
            return ResponseEntity.ok("사용가능한 아이디 입니다");
        }
    }

    @Operation(summary = "닉네임 중복체크",
            description = "입력한 닉네임을 db와 대조한뒤 중복 체크")
    @PostMapping("checkNick")
    public ResponseEntity<String> checkNick(@RequestBody Member member) {
        boolean checkNickDuplicate = memberRepository.existsByNickname(member.getNickname());

        if (checkNickDuplicate) {
            return ResponseEntity.ok("중복된 닉네임 입니다");
        } else {
            return ResponseEntity.ok("사용가능한 닉네임 입니다");
        }
    }

    @Operation(summary = "폰번호 중복체크",
            description = "입력한 폰번호를 db와 대조한뒤 중복 체크")
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
