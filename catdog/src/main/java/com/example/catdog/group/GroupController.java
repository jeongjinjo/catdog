package com.example.catdog.group;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class GroupController {
    private final GroupService groupService;
}
