package com.example.catdog.todo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("checklist")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("{id}")
    public ResponseEntity<Map<Integer, List<Object>>> todolist(@PathVariable String id){
        Map<Integer, List<Object>> todolists = todoService.get(id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(todolists);
    }
}
