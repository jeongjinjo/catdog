package com.example.catdog.careGroup;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class GroupServiceTest {
    @Autowired private GroupService service;

    @Test
    void groupInser() {
        // 현재 아이디
        String currentMemberId = "tt";
        // 그룹등록
        CareGroup careGroup = new CareGroup();
        careGroup.setGroup_name("TDD");
        // 인원
        List<String> memberId = Arrays.asList("tt", "yyy", "hoho", "ninano");
        // 펫등록
        List<Integer> petNum = Arrays.asList(8, 9, 10);
        // 실행
        int result = service.groupInsert(careGroup, memberId, petNum, currentMemberId);

        System.out.println(result);
    }
}