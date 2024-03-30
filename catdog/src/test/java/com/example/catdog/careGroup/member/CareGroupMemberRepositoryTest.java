package com.example.catdog.careGroup.member;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CareGroupMemberRepositoryTest {
    @Autowired private CareGroupMemberRepository careGroupMemberRepository;

    // 멤버 권한 확인
    @Test
    void checkMemberPermissions() {
        Optional<CareGroupMember> careGroupMember = careGroupMemberRepository.findByGroupNumAndMemberId(1, "hoho");
        System.out.println(careGroupMember);
    }

    // 그룹에 속한 반려동물 목록 조회
//    @Test
//    void checkGroupMembership() {
//        int groupNum = 21;
//        List<CareGroupMember> careGroupMember = careGroupMemberRepository.findByGroupInMember(groupNum);
//        for(CareGroupMember m : careGroupMember) {
//            System.out.println(m);
//        }
//    }

    // 그룹에 속해있는 멤버 목록 조회
    @Test
    void editMemberInformation() {
        int groupNum = 21;
        careGroupMemberRepository.groupMemberResignYnUpdateAll(groupNum);
    }
}