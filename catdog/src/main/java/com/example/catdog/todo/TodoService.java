package com.example.catdog.todo;

import com.example.catdog.enum_column.Comp_yn;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.TodoException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    // 로그인 한 아이디와, 아이디의 그룹의 펫 번호, 날짜를 가지고 펫의 할일 불러오기
    public Map<Integer, List<Object>> getdatePetTodoList(String member_id, Integer pet_num, Date date){
        Optional<List<Object[]>> todoList = todoRepository.findByGroupPetTodo(member_id, pet_num, date);
        Map<Integer, List<Object>> groupTodos = new HashMap<>();

        for(Object[] info : todoList.get()){
            Integer groupNum = (Integer) info[0];

            Todo todos = new Todo();

            todos.setTodo_num((Integer) info[1]);
            todos.setPet_num((Integer) info[2]);
            todos.setTodo((String) info[3]);
            todos.setStart_date((LocalDateTime) info[4]);
            todos.setStart_id((String) info[5]);

            groupTodos.putIfAbsent(groupNum, new ArrayList<>());
            groupTodos.get(groupNum).add(todos);
        }
        if(groupTodos.isEmpty()) {
            throw new TodoException(ErrorCode.NOT_FOUND);
        }
        return groupTodos;
    }
    
    // 로그인 한 사람의 그룹의 반려동물 할일 수정하기
    public Todo updateTodo(Todo todo) {
        Optional<Todo> optionalTodo = todoRepository.findById(todo.getTodo_num());

        if (optionalTodo.isPresent()) {
            Todo todoEntity = optionalTodo.get();

            if (todoEntity.getStart_date().toLocalDate().isEqual(LocalDate.now())
                    && todoEntity.getResign_yn() == Resign_yn.N
            ) {
                todoEntity.setTodo(todo.getTodo());
                todoEntity.setStart_date(LocalDateTime.now());

                Todo updateTodo = todoRepository.save(todoEntity);
                return updateTodo;
            }
            else{
                throw new TodoException(ErrorCode.NOT_FOUND);
            }
        }
        return null;
    }

    // 할일 완료 안 된 것만 삭제 하기
    public Todo deleteTodo(Integer todo_num) {
        Optional<Todo> optionalTodo = todoRepository.findById(todo_num);

        if (optionalTodo.isPresent()) {
            Todo todoEntity = optionalTodo.get();
            if ( todoEntity.getStart_date().toLocalDate().isEqual(LocalDate.now())
                    && todoEntity.getComplete_yn() == Comp_yn.N
                    && todoEntity.getComplete_by() == null
                    && todoEntity.getComplete_at() == null
                    && todoEntity.getResign_yn() == Resign_yn.N
                    && !todoEntity.getTodo().isEmpty()
                )
            {
                todoEntity.setResign_yn(Resign_yn.Y);
                Todo updateTodo = todoRepository.save(todoEntity);

                return updateTodo;
            }
            else{
                throw new TodoException(ErrorCode.NOT_FOUND);
            }
        }
        return null;
    }
}
