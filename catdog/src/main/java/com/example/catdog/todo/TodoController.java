package com.example.catdog.todo;

import com.example.catdog.enum_column.Comp_yn;
import com.example.catdog.enum_column.Resign_yn;
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
public class TodoController {

    private final TodoService todoService;
    private final TodoRepository todoRepository;

    /**
     * @param todo : 할일
     * @return : 반려동물의 todo(할 일)을 등록한다.
     */
    @PostMapping()
    public Todo getPetTodo(@RequestBody Todo todo){
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

    /**
     * @return : 로그인 한 사람이름이 본인이 작석한 오늘날짜의 할일 수정하기
     */
    @PutMapping()
    public ResponseEntity<Todo> update(@RequestBody TodoDTO todoDTO) {
        ModelMapper mapper = new ModelMapper();
        Todo todo = mapper.map(todoDTO, Todo.class);

        Todo updatedTodo = todoService.updateTodo(todo);

        return ResponseEntity.status(HttpStatus.OK).body(updatedTodo);
    }

    /**
     * @param todo_num : 삭제할 반려동물 고유번호
     * @return : 반려동물의 오늘날짜의 할일 삭제하기
     */
    @PutMapping("{todo_num}")
    public ResponseEntity<Todo> delete(@PathVariable Integer todo_num) {
        Todo deleteTodo = todoService.deleteTodo(todo_num);

        return ResponseEntity.status(HttpStatus.OK).body(deleteTodo);
    }
}
