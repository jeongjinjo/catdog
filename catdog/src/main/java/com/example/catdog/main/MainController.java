package com.example.catdog.main;

import lombok.RequiredArgsConstructor;
import org.springframework.data.relational.core.sql.In;
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
    public ResponseEntity<Map<Integer, List<Object>>> getGroupPet(@RequestParam String id){
        Map<Integer, List<Object>> pets = mainService.getGroupInfoPet(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pets);
    }

    // 로그인 한 회원이 속한 그룹의 그룹별 회원닉네임
    @GetMapping("/memberInfo")
    public ResponseEntity<Map<Integer, List<Object>>> getGroupInfoMember(@RequestParam String id){
        Map<Integer, List<Object>> members = mainService.getGroupInfoMember(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(members);
    }

}
