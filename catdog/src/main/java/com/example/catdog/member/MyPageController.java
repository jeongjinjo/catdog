package com.example.catdog.member;

import com.example.catdog.careGroup.GroupService;
import com.example.catdog.careGroup.member.CareGroupMember;
import com.example.catdog.pet.Pet;
import com.example.catdog.pet.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("my")
@Tag(name = "MyPageController", description = "마이페이지 관련 컨트롤러")
public class MyPageController {
    private final MemberService service;
    private final GroupService groupService;
    private final PetService petService;

    // NOTE 내 정보 확인 ( eunae ) CHECK 03.21 확인완료 / 03.22 확인완료
    @Operation(summary = "내 정보 조회"
            , description = "아이디를 기준으로 내 정보를 확인할 수 있는 SELECT 기능")
    @Parameters(
            @Parameter(
                    description = "수정하고자하는 멤버의 아이디를 입력해주세요.",
                    name = "id",
                    required = true
            )
    )
    @GetMapping("{loginId}")
    public ResponseEntity<Member> myInfo(@PathVariable String loginId) {
        Member member = service.getInfo(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(member);
    }

    // NOTE 내 정보 수정 ( eunae ) CHECK 03.21 수정완료 / 03.22 확인완료 / 03.27 수정완료
    @Operation(summary = "내 정보 수정"
            , description = "내 정보 외에도 수정할 비밀번호도 같이 입력할 수 있도록 만든 INSERT 기능")
    @PutMapping()
    public ResponseEntity<Integer> myInfoUpdate(@Valid @RequestBody MemberDTO memberDTO) {
        ModelMapper mapper = new ModelMapper();
        Member member = mapper.map(memberDTO, Member.class);
        int result = service.myInfoUpdate(member, memberDTO.getPasswordUpdate());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 회원탈퇴 ( eunae ) CHECK 03.21 확인완료 / 03.22 확인완료 / 03.27 수정완료
    @Operation(summary = "회원탈퇴"
            , description = "내 정보 외에도 수정할 비밀번호도 같이 입력할 수 있도록 만든 INSERT 기능")
    @Parameters(
            @Parameter(
                    description = "탈퇴시킬 아이디를 입력해주세요.",
                    name = "id",
                    required = true
            )
    )
    @PutMapping("{loginId}")
    public ResponseEntity<Integer> SignOut(@PathVariable String loginId) {
        int result = service.signOut(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 탈퇴 전 비밀번호 입력하기 ( eunae ) CHECK 03.21 수정완료 / 03.22 확인완료 / 03.27 수정완료 / 03.28 수정완료
    @Operation(summary = "탈퇴 전 본인인증하기"
            , description = "아이디와 패스워드만 입력해주면 탈퇴 전 본인확인을 진행하는 SELECT 기능")
    @Parameters(
            @Parameter(
                    description = "본인확인을 위해 아이디를 입력해주세요.",
                    name = "id",
                    required = true
            )
    )
    @PostMapping("{loginId}")
    public ResponseEntity<Integer> pwCheck(@PathVariable String loginId, @RequestBody String password) {
        String pw = password.replace("\"", "");
        int result = service.pwCheck(loginId, pw);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    //  NOTE 그룹관리 조회 ( eunae )
    @Operation(summary = "본인이 속해있는 그룹과 그룹에 속해있는 멤버 조회"
            , description = "id만 입력하시면 본인이 속해있는 그룹과 그룹에 속해있는 멤버를 조회할 수 있습니다.")
    @PostMapping("group/{loginId}")
    public ResponseEntity<Map<Integer, List<CareGroupMember>>> groupList(@PathVariable String loginId) {
        Map<Integer,List<CareGroupMember>> list = groupService.groupList(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // NOTE 내 반려동물 조회 ( eunae )
    @Operation(summary = "본인이 등록한 반려동물 조회"
    ,description = "id만 입력하시면 본인이 등록한 반려동물을 확인할 수 있습니다.")
    @GetMapping("pet/{loginId}")
    public ResponseEntity<List<Pet>> myPetInfo(@PathVariable String loginId) {
        List<Pet> pets =petService.getPetsByMemberId(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(pets);
    }
}
