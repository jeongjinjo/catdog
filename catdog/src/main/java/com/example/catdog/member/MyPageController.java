package com.example.catdog.member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("my")
@RequiredArgsConstructor
public class MyPageController {
    private final MemberService service;

    // 내 정보 확인 ( eunae )
    @GetMapping("{id}")
    public ResponseEntity<Member> myInfo(@PathVariable String id) {
        Member member = service.getInfo(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(member);
    }

    @PutMapping()
    public ResponseEntity<Member> myInfoUpdate(@Valid @RequestBody MemberDTO memberDTO) {
        ModelMapper mapper = new ModelMapper();
        Member member = mapper.map(memberDTO, Member.class);

        Member db = service.myInfoUpdate(member);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(db);
    }

    // 회원탈퇴 ( eunae )
    @PutMapping("signOut")
    public ResponseEntity<Member> SignOut(@Valid @RequestBody String id) {
        String idValue = id.split(":")[1]
                            .replace("{", "")
                            .replace("}", "")
                            .replace("\"", "")
                            .trim();
        Member db = service.signOut(idValue);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(db);
    }

    // 탈퇴 전 비밀번호 입력하기 ( eunae )
    @GetMapping("pwCheck")
    public ResponseEntity<Member> pwCheck(@Valid @RequestBody String idAndPw) {
        String[] parts = idAndPw.split(":");
        String idValue = parts[1].split(",")[0]
                                    .replaceAll("[{}\"]", "")
                                    .trim();
        String pwValue = parts[2].replaceAll("[{}\"]", "")
                                    .trim();

        Member db = service.pwCheck(idValue, pwValue);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(db);
    }
}
