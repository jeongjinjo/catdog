package com.example.catdog.main;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "MainController", description = "로그인 한 사람이 속한 그룹별 반려동물의 정보 조회")
public class MainController {

    private final MainService mainService;
    
    /**
     * @param member_id : 로그인 한 아아디
     * @return : 로그인 한 사람이 속한 그룹별 반려동물의 정보 조회하기
     */
    @Operation(summary = "그룹별 반려동물"
            , description = "로그인 한 사람이 속한 그룹별 반려동물의 정보 확인을 위한 SELECT 기능")
    @GetMapping("{member_id}")
    public ResponseEntity<Map<Integer, List<Object>>> selectGroupPet(@PathVariable String member_id){
        Map<Integer, List<Object>> pets = mainService.getGroupInfoPet(member_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pets);
    }
}
