package com.example.catdog.careGroup;

import com.example.catdog.careGroup.dto.RequestDTO;
import com.example.catdog.careGroup.member.CareGroupMember;
import com.example.catdog.careGroup.member.CareGroupMemberDTO;
import com.example.catdog.member.Member;
import com.example.catdog.member.MemberService;
import com.example.catdog.pet.Pet;
import com.example.catdog.pet.PetService;
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
public class GroupController {
    private final GroupService groupService;
    private final PetService petService;
    private final MemberService memberService;

    // NOTE 유저가 속해있는 그룹 정보 조회 ( eunae ) CHECK 03.21 확인완료 03.22 확인완료
    @GetMapping("{id}")
    public ResponseEntity<Map<Integer, List<CareGroupMember>>> gorupList(@PathVariable String id) {
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
    @PostMapping()
    public ResponseEntity<Integer> careGroupAndTargetInsert(@Valid @RequestBody RequestDTO requestDTO) {

        ModelMapper mapper = new ModelMapper();
        CareGroup careGroupDb = mapper.map(requestDTO.getGroupDTO(), CareGroup.class);
        int result = groupService.groupInsert(careGroupDb
                                                , requestDTO.getMember_id()
                                                , requestDTO.getPet_num()
                                                , requestDTO.getCurrent_member_id());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 그룹 수정
}
