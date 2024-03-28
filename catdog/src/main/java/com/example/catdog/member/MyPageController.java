package com.example.catdog.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("my")
@Tag(name = "MyPageController", description = "마이페이지 관련 컨트롤러")
public class MyPageController {
    private final MemberService service;

    // NOTE 내 정보 확인 ( eunae ) CHECK 03.21 확인완료 / 03.22 확인완료
    @Operation(summary = "내 정보 조회"
            , description = "아이디를 기준으로 내 정보를 확인할 수 있는 SELECT 기능")
    @GetMapping("{id}")
    public ResponseEntity<Member> myInfo(@PathVariable String id) {
        Member member = service.getInfo(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(member);
    }

    // NOTE 내 정보 수정 ( eunae ) CHECK 03.21 수정완료 / 03.22 확인완료 / 03.27 수정완료
    @Operation(summary = "내 정보 수정"
            , description = "내 정보 외에도 수정할 비밀번호도 같이 입력할 수 있도록 만든 INSERT 기능")
    @PutMapping()
    public ResponseEntity<Integer> myInfoUpdate(@Valid @RequestBody MemberDTO memberDTO) {
        ModelMapper mapper = new ModelMapper();
        Member member = mapper.map(memberDTO, Member.class);
        int result = service.myInfoUpdate(member, memberDTO.getPasswordUpdate());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    // NOTE 회원탈퇴 ( eunae ) CHECK 03.21 확인완료 / 03.22 확인완료 / 03.27 수정완료
    @Operation(summary = "회원탈퇴"
            , description = "내 정보 외에도 수정할 비밀번호도 같이 입력할 수 있도록 만든 INSERT 기능")
    @PutMapping("{id}")
    public ResponseEntity<Integer> SignOut(@PathVariable String id) {
        int result = service.signOut(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }

    // NOTE 탈퇴 전 비밀번호 입력하기 ( eunae ) CHECK 03.21 수정완료 / 03.22 확인완료 / 03.27 수정완료
    @Operation(summary = "회원탈퇴"
            , description = "내 정보 외에도 수정할 비밀번호도 같이 입력할 수 있도록 만든 INSERT 기능")
    @GetMapping("pwCheck")
    public ResponseEntity<Integer> pwCheck(@Valid @RequestBody MemberDTO memberDTO) {
        int result = service.pwCheck(memberDTO.getMember_id(), memberDTO.getPassword());
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);
    }
}
