package com.example.catdog.group;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("group")
public class GroupController {
    private final GroupService service;

    @GetMapping()
    public ResponseEntity<List<Group>> gorupList(@Valid String id) {
        List<Group> list = service.groupList(id);
        return null;
    }
}
