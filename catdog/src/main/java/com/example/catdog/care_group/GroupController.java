package com.example.catdog.care_group;

import com.example.catdog.member.Member;
import com.example.catdog.member.MemberService;
import com.example.catdog.pet.Pet;
import com.example.catdog.pet.PetDto;
import com.example.catdog.pet.PetService;
import lombok.RequiredArgsConstructor;
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

    // 유저가 속해있는 그룹 정보 조회 ( eunae ) - 03.21 확인완료
    @GetMapping()
    public ResponseEntity<Map<Integer, List<Care_group>>> gorupList(@RequestBody String id) {
        String idValue = id.split(":")[1]
                            .replace("{", "")
                            .replace("}", "")
                            .replace("\"", "")
                            .trim();
        Map<Integer,List<Care_group>> list = groupService.groupList(idValue);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
    }

    // 그룹에 속해있지 않은 내 반려동물 조회 ( eunae ) - 03.21 확인완료
    @GetMapping("pet")
    public ResponseEntity<List<Pet>> getGroupNotInPet(@RequestBody String id) {
        String idValue = id.split(":")[1]
                            .replace("{", "")
                            .replace("}", "")
                            .replace("\"", "")
                            .trim();
        List<Pet> pet = petService.getGroupNotInPet(idValue);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pet);
    }

    // 멤버 그룹 초대 ( eunae ) -> 기능보유객체: Member
    @GetMapping("search")
    public ResponseEntity<List<Member>> memberGroupInvite(@RequestBody String myAndSearchMember) {
        String[] parts = myAndSearchMember.split(":");
        String member_id = parts[1].split(",")[0]
                .replaceAll("[{}\"]", "")
                .trim();
        String search_id = parts[2].replaceAll("[{}\"]", "")
                .trim();

        List<Member> list = memberService.memberGroupInvite(member_id, search_id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
    }

    @GetMapping("{id}")
    public ResponseEntity<Map<Integer, List<Pet>>> getGroupInfoPet(@PathVariable String id) {
//        String idValue = id.split(":")[1]
//                .replace("{", "")
//                .replace("}", "")
//                .replace("\"", "")
//                .trim();
        Map<Integer,List<Pet>> list = groupService.getGroupInfoPet(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
    }
}
