package com.example.catdog.todo;

import com.example.catdog.enum_column.Comp_yn;
import com.example.catdog.enum_column.Resign_yn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
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
@Tag(name = "TodoController", description = "반려동물 별 할일 컨트롤러")
public class TodoController {

    private final TodoService todoService;
    private final TodoRepository todoRepository;

    /**
     * @param todo : 할일
     * @return : 반려동물의 todo(할 일)을 등록한다.
     */
    @Operation(summary = "반려동물의 할일 등록"
            , description = "사용 키: todo, start_id, pet_num /할일관리를 만들어주기 위한 INSERT 기능/")
    @PostMapping()
    public Todo insertTodo(@RequestBody Todo todo){
        Todo todos= Todo.builder()
                .todo(todo.getTodo())
                .complete_yn(Comp_yn.N)
                .start_id(todo.getStart_id())
                .start_date(LocalDateTime.now())
                .resign_yn(Resign_yn.N)
                .pet_num(todo.getPet_num())
                .build();
        Todo dbtodo = todoRepository.save(todos);
        return dbtodo;
    }

    /**
     * @param member_id : 로그인 한 아이디
     * @param pet_num : 선택한 반려동물
     * @param date : 날짜
     * @return : 선택한 동물과 날짜로 할 일 조회한다. 디폴트는 오늘날짜, 첫번째 반려동물
     */
    @Operation(summary = "반려동물의 할일 조회"
            , description = "반려동물의 고유번호로 할일 조회. SELECT 기능 / date 예시 : 2024/03/28")
    @GetMapping()
    public ResponseEntity<Map<Integer, List<Object>>> selectTodoList(@RequestParam String member_id, Integer pet_num, Date date){
        Map<Integer, List<Object>> todolist = todoService.getdatePetTodoList(member_id, pet_num, date);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(todolist);
    }

    /**
     * @return : 오늘날짜의 할일 수정하기
     */
    @Operation(summary = "반려동물의 할일 수정"
            , description = "사용 키: todo_num , todo /반려동물 고유번호를 이용해 오늘 날짜일 경우 수정가능. UPDATE 기능/ ")
    @PutMapping()
    public ResponseEntity<Todo> updateTodo(@RequestBody TodoDTO todoDTO) {
        ModelMapper mapper = new ModelMapper();
        Todo todo = mapper.map(todoDTO, Todo.class);
        Todo updatedTodo = todoService.updateTodo(todo);
        return ResponseEntity.status(HttpStatus.OK).body(updatedTodo);
    }

    /**
     * @param todo_num : 삭제할 반려동물 고유번호
     * @return : 반려동물의 오늘날짜의 할일 삭제하기
     */
    @Operation(summary = "반려동물의 할일 삭제"
            , description = "반려동물 고유번호 받아서 삭제 처리. 할일 완료 안 되 것만 삭제 가능. UPDATE 기능.")
    @PutMapping("{todo_num}")
    public ResponseEntity<Todo> deleteTodo(@PathVariable Integer todo_num) {
        Todo deleteTodo = todoService.deleteTodo(todo_num);
        return ResponseEntity.status(HttpStatus.OK).body(deleteTodo);
    }
}
