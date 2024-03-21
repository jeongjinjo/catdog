package com.example.catdog.main;

import com.example.catdog.pet.Pet;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    // 로그인 한 회원의 그룹별 반려동물 정보
    @GetMapping("/petInfo/{id}")
    public List<Pet> getGroupPet(@PathVariable String id){
        List<Pet> pets = mainService.getGroupInfoPet(id);
        return pets;
    }

}
