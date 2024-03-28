package com.example.catdog.careGroup;

import com.example.catdog.careGroup.target.CareTarget;
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

    @Test
    void groupDelete() {
        // 현재 아이디
        String currentMemberId = "tt";
        // 그룹번호
        int groupNum = 21;
        // 실행
        int result = service.groupDelete(groupNum, currentMemberId);

        System.out.println(result);
    }

    @Test
    void groupInMemberInAndOutUpdate() {
        int groupNum = 8;
        String loginId = "ninano";
        String deleteTargetMember = "tt";

        int result = service.groupInMemberInAndOutUpdate(groupNum, loginId, deleteTargetMember);
    }

    @Test
    void groupInPetOutUpdate() {
        int groupNum = 1;
        String loginId = "hoho";
        int petNum = 1;

        int ct = service.groupInPetOutUpdate(groupNum, loginId ,petNum);
        System.out.println("결과 >>>>>>>>>>>>>>>>>>>>>>>>>>>" + ct);
    }

    @Test
    void groupInPetInUpdate() {
        int groupNum = 1;
        String loginId = "hoho";
        int petNum = 5;

        int ct = service.groupInPetInUpdate(groupNum, loginId ,petNum);
        System.out.println("결과 >>>>>>>>>>>>>>>>>>>>>>>>>>>" + ct);
    }

    @Test
    void groupUpdate() {
//        int groupNum = 1;
//        String loginId = "hoho";
//        service.groupUpdate(groupNum, loginId);
    }
}