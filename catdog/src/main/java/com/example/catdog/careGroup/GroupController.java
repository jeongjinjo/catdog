package com.example.catdog.careGroup;

import com.example.catdog.careGroup.dto.RequestDTO;
import com.example.catdog.careGroup.member.CareGroupMember;
import com.example.catdog.careGroup.member.CareGroupMemberDTO;
import com.example.catdog.member.Member;
import com.example.catdog.member.MemberService;
import com.example.catdog.pet.Pet;
import com.example.catdog.pet.PetService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("group")
@Tag(name = "CareGroupController", description = "그룹관리 관련 컨트롤러")
public class GroupController {
    private final GroupService groupService;
    private final PetService petService;
    private final MemberService memberService;

    // NOTE 유저가 속해있는 그룹 정보 조회 ( eunae ) CHECK 03.21 확인완료 03.22 확인완료
    @Operation(summary = "멤버가 속해있는 그룹 정보 조회"
            , description = "그룹관리 페이지의 화면을 만들어주기 위한 SELECT 기능")
    @GetMapping("{id}")
    public ResponseEntity<Map<Integer, List<CareGroupMember>>> groupList(@PathVariable String id) {
        Map<Integer,List<CareGroupMember>> list = groupService.groupList(id);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // NOTE 그룹에 속해있지 않은 내 반려동물 조회 ( eunae ) CHECK 03.21 확인완료 03.22 확인완료
    @GetMapping("pet/{id}")
    public ResponseEntity<List<Pet>> getGroupNotInPet(@PathVariable String id) {
        List<Pet> pet = petService.getGroupNotInPet(id);
        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

    // NOTE 그룹 내에 등록할 유저 검색 ( eunae ) CHECK 03.22 확인완료
    @GetMapping("search")
    public ResponseEntity<List<Member>> memberGroupInvite(@RequestBody String myAndSearchMember) {
        String[] parts = myAndSearchMember.split(":");
        String member_id = parts[1].split(",")[0]
                .replaceAll("[{}\"]", "")
                .trim();
        String search_id = parts[2].replaceAll("[{}\"]", "")
                .trim();

        List<Member> list = memberService.memberGroupInvite(member_id, search_id);

        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // NOTE 그룹에 속해있는 반려동물 확인하기 ( eunae ) CHECK 03.22 확인완료
    @GetMapping("groupInPet/{id}")
    public ResponseEntity<Map<Integer, List<Pet>>> getGroupInfoPet(@PathVariable String id) {
        Map<Integer,List<Pet>> list = groupService.getGroupInfoPet(id);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // NOTE 그룹 등록 ( eunae ) CHECK 03.23 생성
    @PostMapping
    public ResponseEntity<Integer> careGroupAndMemberAndTargetInsert(@Valid @RequestBody RequestDTO requestDTO) {

        ModelMapper mapper = new ModelMapper();
        CareGroup careGroupDb = mapper.map(requestDTO.getGroupDTO(), CareGroup.class);
        int result = groupService.groupInsert(careGroupDb
                , requestDTO.getMember_id()
                , requestDTO.getPet_num()
                , requestDTO.getCurrent_member_id());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 그룹 삭제 ( eunae ) CHECK 03.24 생성 --> @PathVariable로 바꿔보기
    @PutMapping("del")
    public ResponseEntity<Integer> careGroupAllDelete(@Valid @RequestBody RequestDTO requestDTO) {
        int result = groupService.groupDelete( requestDTO.getGroupDTO().getGroup_num(),
                requestDTO.getCurrent_member_id()
        );
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 그룹 수정 - 인원 삭제 및 등록 ( eunae ) CHECK 03.25 생성 03.27 수정완료
    @PutMapping("{groupNum}/{loginId}/{targetMember}")
    public ResponseEntity<Integer> groupInMemberOutUpdate(@PathVariable int groupNum
            , @PathVariable String loginId
            , @PathVariable String targetMember) {
        int result = groupService.groupInMemberInAndOutUpdate(groupNum, loginId, targetMember);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @DeleteMapping("{groupNum}/{loginId}/{delTargetPet}")
    // NOTE 그룹 수정 - 반려동물 삭제 ( eunae ) CHECK 03.25 생성
    public ResponseEntity<Integer> groupInPetOutUpdate(@PathVariable int groupNum
            , @PathVariable String loginId
            , @PathVariable int delTargetPet) {
        int result = groupService.groupInPetOutUpdate(groupNum, loginId, delTargetPet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @PostMapping("{groupNum}/{loginId}/{inTargetPet}")
    // NOTE 그룹 수정 - 반려동물 등록 ( eunae ) CHECK 03.27 생성
    public ResponseEntity<Integer> groupInPetInUpdate(@PathVariable int groupNum
            , @PathVariable String loginId
            , @PathVariable int inTargetPet) {
        int result = groupService.groupInPetInUpdate(groupNum, loginId, inTargetPet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 그룹 수정 ( eunae ) CHECK 03.27 생성
    @PutMapping("{id}")
    public ResponseEntity<Integer> careGroupUpdate(@RequestBody GroupDTO groupDTO, @PathVariable String loginId) {
        ModelMapper mapper = new ModelMapper();
        CareGroup careGroup = mapper.map(groupDTO, CareGroup.class);
        int result = groupService.groupUpdate(careGroup, loginId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
