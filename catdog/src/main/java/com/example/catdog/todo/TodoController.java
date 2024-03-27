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
import java.util.Optional;

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

    /**
     * @param todo_num : 펫 번호
     * @param todo : 할일
     * @param member_id : 로그인 한 아이디
     * @return : 로그인 한 사람이름이 본인이 작석한 오늘날짜의 할일 수정하기
     */
    @PutMapping()
    public ResponseEntity<Todo> update(@RequestParam Integer todo_num, String todo, String member_id) {
        Optional<Todo> updatedTodo = todoService.updateTodo(todo_num, todo, member_id);
        return updatedTodo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //@RequestBody 로 수정해보기 도전 중--
//    @PutMapping()
//    public ResponseEntity<Todo> update(@RequestBody TodoDTO todoDTO) {
//        ModelMapper mapper = new ModelMapper();
//        Todo todo = mapper.map(todoDTO, Todo.class);
//        Optional<Todo> updatedTodo = todoService.updateTodo(todoDTO);
//                todoDTO.getTodo(),
//                todoDTO.getStart_id());
//
//        if (updatedTodo.isPresent()) {
//            return ResponseEntity.status(HttpStatus.OK).body(updatedTodo.get());
//        } else {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//        }
//        return updatedTodo.map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }

    /**
     * @param todo_num : 삭제할 반려동물 고유번호
     * @param member_id : 로그인 한 사람 아이디
     * @return : 로그인 한 사람이름이 본인이 작석한 오늘날짜의 할일 삭제하기
     */
    @PutMapping("/deletelist")
    public ResponseEntity<Todo> delete(@RequestParam Integer todo_num, String member_id) {
        Optional<Todo> deleteTodo = todoService.deleteTodo(todo_num, member_id);
        return deleteTodo.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
