package com.example.catdog.todo;

import com.example.catdog.enum_column.Comp_yn;
import com.example.catdog.enum_column.Resign_yn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("checklist")
public class TodoController {

    private final TodoService todoService;
    private final TodoRepository todoRepository;

    /**
     * @param todo : 할일
     * @param member_id : 로그인한 사람의 아이디
     * @param pet_num : 선택한 반려동물
     * @return : 반려동물의 todo(할 일)을 등록한다.
     */
    @PostMapping()
    public Todo getPetTodo(String todo, String member_id, Integer pet_num){
        Todo todos= Todo.builder()
                .todo(todo)
                .complete_yn(Comp_yn.N)
                .start_id(member_id)
                .start_date(LocalDateTime.now())
                .resign_yn(Resign_yn.N)
                .pet_num(pet_num)
                .build();
        Todo dbtodo = todoRepository.save(todos);
        return dbtodo;
    }

    /**
     * @param id : 로그인 한 아이디
     * @param petNum : 선택한 반려동물
     * @param date : 날짜
     * @return : 선택한 동물과 날짜로 할 일 조회한다. 디폴트는 오늘날짜, 첫번째 반려동물
     */
    @GetMapping()
    public ResponseEntity<Map<Integer, List<Object>>> todolist(@RequestParam String id, Integer petNum, Date date){
        Map<Integer, List<Object>> todolists = todoService.getdatePetTodoList(id, petNum, date);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(todolists);
    }
}
