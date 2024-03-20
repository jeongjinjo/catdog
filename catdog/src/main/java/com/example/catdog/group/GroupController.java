package com.example.catdog.group;

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

    @GetMapping()
    public ResponseEntity<Map<Integer, List<CareGroup>>> gorupList(@RequestBody String id) {
        String idValue = id.split(":")[1]
                            .replace("{", "")
                            .replace("}", "")
                            .replace("\"", "")
                            .trim();
        Map<Integer,List<CareGroup>> list = groupService.groupList(idValue);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(list);
    }

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
