package com.example.catdog.todo;

import com.example.catdog.enum_column.Comp_yn;
import com.example.catdog.enum_column.Resign_yn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    // 로그인 한 아이디와, 아이디의 그룹의 펫 번호, 날짜를 가지고 펫의 할일 불러오기
    public Map<Integer, List<Object>> getdatePetTodoList(String id, Integer petNum, Date date){
        Optional<List<Object[]>> todoList = todoRepository.findByGroupPetTodo(id, petNum, date);
        Map<Integer, List<Object>> groupTodos = new HashMap<>();

        for(Object[] info : todoList.get()){
            Integer groupNum = (Integer) info[0];

            Todo todo = new Todo();

            todo.setTodo_num((Integer) info[1]);
            todo.setPet_num((Integer) info[2]);
            todo.setTodo((String) info[3]);
            todo.setStart_date((LocalDateTime) info[4]);
            todo.setStart_id((String) info[5]);

            groupTodos.putIfAbsent(groupNum, new ArrayList<>());
            groupTodos.get(groupNum).add(todo);
        }
        return groupTodos;
    }
    
    // 로그인 한 사람의 그룹의 반려동물 할일 수정하기
    public Optional<Todo> updateTodo(int todo_num, String todo, String member_id) {
        Optional<Todo> optionalTodo = todoRepository.findById(todo_num);

        if (optionalTodo.isPresent()) {

            Todo todoEntity = optionalTodo.get();

            if ( todoEntity.getStart_date().toLocalDate().isEqual(LocalDate.now())
                    && todoEntity.getComplete_yn() == Comp_yn.N
                    && todoEntity.getComplete_by() == null
                    && todoEntity.getComplete_at() == null
                    && todoEntity.getResign_yn() == Resign_yn.N
//                    && todoEntity.getStart_id().equals(member_id)// 이 부분 없애고 set으로 던지면 로그인 한 아이디로 수정변경 됨.
                )
            {
                todoEntity.setTodo(todo);
                todoEntity.setStart_date(LocalDateTime.now());
                todoEntity.setStart_id(member_id);

                Todo updatedTodo = todoRepository.save(todoEntity);

                return Optional.of(updatedTodo);
            }
        }
        return Optional.empty();
    }
    //@RequestBody 로 수정해보기 도전 중--
//    public Optional<Todo> updateTodo(TodoDTO todoDTO) {
//        Optional<Todo> optionalTodo = todoRepository.findById(todoDTO.getTodo_num());
//
//        if (optionalTodo.isPresent()) {
//            Todo todoEntity = optionalTodo.get();
//
//            // Check if the conditions allow for updating the todo
//            if (isUpdateAllowed(todoEntity, todoDTO)) {
//                todoEntity.setTodo(todoDTO.getTodo());
//                todoEntity.setStart_date(LocalDateTime.now());
//
//                Todo updatedTodo = todoRepository.save(todoEntity);
//                return Optional.of(updatedTodo);
//            }
//        }
//        return Optional.empty();
//    }
//    private boolean isUpdateAllowed(Todo todoEntity, TodoDTO todoDTO) {
//        return todoEntity.getStart_date().toLocalDate().isEqual(LocalDate.now()) &&
//                todoEntity.getComplete_yn() == Comp_yn.N &&
//                todoEntity.getComplete_by() == null &&
//                todoEntity.getComplete_at() == null &&
//                todoEntity.getResign_yn() == Resign_yn.N &&
//                todoEntity.getStart_id().equals(todoDTO.getStart_id());
//    }

    // 할일 완료 안 된 것만 삭제 하기
    public Optional<Todo> deleteTodo(int todo_num, String member_id) {
        Optional<Todo> optionalTodo = todoRepository.findById(todo_num);

        if (optionalTodo.isPresent()) {

            Todo todoEntity = optionalTodo.get();

            if ( todoEntity.getStart_date().toLocalDate().isEqual(LocalDate.now())
                    && todoEntity.getComplete_yn() == Comp_yn.N
                    && todoEntity.getComplete_by() == null
                    && todoEntity.getComplete_at() == null
                    && todoEntity.getResign_yn() == Resign_yn.N
                    && todoEntity.getTodo()!=null
                )
            {
                todoEntity.setResign_yn(Resign_yn.Y);

                Todo updatedTodo = todoRepository.save(todoEntity);
                return Optional.of(updatedTodo);
            }
        }
        return Optional.empty();
    }
}
