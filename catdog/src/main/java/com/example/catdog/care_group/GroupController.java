package com.example.catdog.care_group;

import com.example.catdog.pet.Pet;
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

    // 유저가 속해있는 그룹 정보 조회 ( eunae ) - 03.21 확인
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
    @GetMapping("/pet")
    public ResponseEntity<List<Pet>> getGroupNotInPet(@RequestBody String id) {
        String idValue = id.split(":")[1]
                            .replace("{", "")
                            .replace("}", "")
                            .replace("\"", "")
                            .trim();
        List<Pet> pet = petService.getGroupNotInPet(idValue);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pet);
    }
}
