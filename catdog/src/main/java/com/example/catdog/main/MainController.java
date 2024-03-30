package com.example.catdog.main;

import com.example.catdog.todo.TodoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Tag(name = "MainController", description = "로그인 한 사람이 속한 그룹별 반려동물의 정보 조회")
public class MainController {

    private final MainService mainService;
    private final TodoRepository todoRepository;

    /**
     * @param loginId : 로그인 한 아아디
     * @return : 로그인 한 사람이 속한 그룹별 반려동물의 정보 조회하기
     */
    @Operation(summary = "그룹별 반려동물"
            , description = "로그인 한 사람이 속한 그룹별 반려동물의 정보 확인을 위한 SELECT 기능")
    @GetMapping("{loginId}")
    public ResponseEntity<Map<Integer, List<Object>>> selectGroupPet(@PathVariable String loginId){
        Map<Integer, List<Object>> pets = mainService.getGroupInfoPet(loginId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pets);
    }

    /**
     * @param date : 할일이 있는지 확인하려는 날짜 입력 (예시 : 2024/03/28)
     * @return : 달력을 선택하면 달력의 날짜를 이용해서 할일이 있는지 없는지를 확인한다.
     */
    @Operation(summary = "날짜별 할일의 유무"
            , description = "(예시 : 2024/03/28) 날짜별 할일이 있다면 true 로, 없다면 false 로 돌려주는 SELECT 기능")
    @GetMapping("calendar")
    public boolean checkCalendar(@RequestParam Date date){
        if(todoRepository.check(date) == 0){
            return false;
        }
        return true;
    }
}