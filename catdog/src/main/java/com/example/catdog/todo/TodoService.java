package com.example.catdog.todo;

import com.example.catdog.todo.Todo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

//    public Map<Integer, List<Object>> get(String id){
//        Optional<List<Object[]>> todolists = todoRepository.findByGroupPetTodo(id);
//        Map<Integer, List<Object>> groupTodos = new HashMap<>();
//
//        for(Object[] info : todolists.get()){
//            Integer groupNum = (Integer) info[0];
//
//            Todo todo = new Todo();
//
//            todo.setTodo_num((Integer) info[1]);
//            todo.setTodo((String) info[2]);
//            //todo.setStart_date();
//            todo.setStart_id((String) info[4]);
//
//            groupTodos.putIfAbsent(groupNum, new ArrayList<>());
//            groupTodos.get(groupNum).add(todolists);
//        }
//        return groupTodos;
//    }
}
