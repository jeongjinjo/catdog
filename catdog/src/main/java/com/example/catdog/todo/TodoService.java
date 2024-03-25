package com.example.catdog.todo;

import com.example.catdog.todo.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
}
