package com.example.catdog.main;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class MainController {

    private final MainService mainService;

    // 로그인 한 회원의 그룹별 반려동물 정보
    @GetMapping("/petInfo")
    public ResponseEntity<Map<String, List<Object>>> getGroupPet(@RequestParam String id){
        Map<String, List<Object>> pets = mainService.getGroupInfoPet(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pets);
    }

}
