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

    // 내 정보 확인 ( eunae ) - 03.21 확인완료 / 03.22 확인완료
    @GetMapping("{id}")
    public ResponseEntity<Member> myInfo(@PathVariable String id) {
        Member member = service.getInfo(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(member);
    }

    // 내 정보 수정 ( eunae ) - 03.21 수정완료 / 03.22 확인완료
    @PutMapping()
    public ResponseEntity<Integer> myInfoUpdate(@Valid @RequestBody MemberDTO memberDTO) {
        ModelMapper mapper = new ModelMapper();
        Member member = mapper.map(memberDTO, Member.class);

        int result = service.myInfoUpdate(member, memberDTO.getPasswordUpdate());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    // 회원탈퇴 ( eunae ) - 03.21 확인완료 / 03.22 확인완료
    @PutMapping("signOut")
    public ResponseEntity<Member> SignOut(@Valid @RequestBody String id) {
        System.out.println(id);
        String idValue = id.split(":")[1]
                            .replace("{", "")
                            .replace("}", "")
                            .replace("\"", "")
                            .trim();
        Member db = service.signOut(idValue);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(db);
    }

    // 탈퇴 전 비밀번호 입력하기 ( eunae ) - 03.21 수정완료 / 03.22 확인완료
    @GetMapping("pwCheck")
    public ResponseEntity<Integer> pwCheck(@Valid @RequestBody String idAndPw) {
        int result = 0;
        String[] parts = idAndPw.split(":");
        String idValue = parts[1].split(",")[0]
                                    .replaceAll("[{}\"]", "")
                                    .trim();
        String pwValue = parts[2].replaceAll("[{}\"]", "")
                                    .trim();

        Member db = service.pwCheck(idValue, pwValue);

        if(db != null) {
            result = 1;
        }

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }
}
