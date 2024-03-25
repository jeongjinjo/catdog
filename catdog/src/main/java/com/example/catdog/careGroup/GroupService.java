package com.example.catdog.careGroup;

import com.example.catdog.careGroup.member.CareGroupMember;
import com.example.catdog.careGroup.member.CareGroupMemberDTO;
import com.example.catdog.careGroup.member.CareGroupMemberRepository;
import com.example.catdog.careGroup.target.CareTargetDTO;
import com.example.catdog.careGroup.target.CareTargetRepository;
import com.example.catdog.careGroup.target.CareTarget;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.exception.CareGroupException;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.pet.Pet;
import com.example.catdog.pet.PetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;
    private final CareGroupMemberRepository careGroupMemberRepository;
    private final PetRepository petRepository;
    private final CareTargetRepository careTargetRepository;

    // 유저가 속해있는 그룹과 그룹에 속해있는 유저 조회 ( eunae )
    public Map<Integer, List<CareGroupMember>> groupList(String memberId) {
        // 특정 memberId가 멤버가 속한 그룹을 group_class를 기준으로 그룹화하는 Map
        Map<Integer, List<CareGroupMember>> groupedByClass = new HashMap<>();

        // 특정 memberId가 멤버가 속한 그룹 리스트를 가져옴.
        Optional<List<CareGroupMember>> careGroups = careGroupMemberRepository.findByGroup(memberId);
        if(careGroups.isEmpty()) {
            throw new CareGroupException(ErrorCode.NOT_FOUND);
        }
        // 가져온 그룹 리스트를 group_class를 기준으로 그룹화.
        for (CareGroupMember careGroup : careGroups.get()) {
            int groupClass = careGroup.getCareGroup().getGroup_num();

            if (!groupedByClass.containsKey(groupClass)) {
                groupedByClass.put(groupClass, new ArrayList<>());
            }

            groupedByClass.get(groupClass).add(careGroup);
        }

        // 그룹화된 결과를 반환
        return groupedByClass;
    }

    @Transactional
    // 유저(HOST 기준)의 그룹별 반려동물 정보 조회 ( eunae )
    public Map<Integer, List<Pet>> getGroupInfoPet(String member_id) {
        Map<Integer, List<Pet>> petInformationByGroup = new HashMap<>();
        List<Pet> petList = petRepository.getGroupInfoPet(member_id);

        for (Pet p : petList) {
            // 그룹번호 가져오는 쿼리문 따로 생성
            CareTarget careTarget = careTargetRepository.getCareGroupNum(p.getPet_num());

            int groupNum = careTarget.getGroup_num();
            if (!petInformationByGroup.containsKey(groupNum)) {
                petInformationByGroup.put(groupNum, new ArrayList<>());
            }
            petInformationByGroup.get(groupNum).add(p);
        }

        return petInformationByGroup;
    }

    // NOTE 그룹 등록 ( eunae )
    @Transactional
    public int groupInsert(CareGroup careGroup, List<String> memberId, List<Integer> petNum, String currentMemberId) {
        int result = 0;

        // NOTE 예외처리
        // CHECK 1. 로그인한 사람이 그룹을 3개 가지고 있으면 등록을 못하게 하기.
        int groupCount = careGroupMemberRepository.countByMemberIdAndResignYn(currentMemberId);
        if(groupCount >= 3) {
            result = -1;
            throw new CareGroupException(ErrorCode.GROUP_REGISTRATION_RESTRICTIONS);
        }

        // CHECK 2. 등록할 사람이 하나도 없다면?
        if(memberId.size() == 0) {
            result = -1;
            throw new CareGroupException(ErrorCode.MEMBER_NOT_FOUND);
        }

        // CHECK 3.그룹내에 몇 명을 집어넣을건지 확인한다.
        if(memberId.size() > 4) {
            result = -1;
            throw new CareGroupException(ErrorCode.LIMITED_NUMBER_OF_MEMBER_REGISTERED);
        }
        // CHECK 4. 그룹내에 몇 마리의 반려동물을 집어넣을건지 확인한다.
        if(petNum.size() > 5) {
            result = -1;
            throw new CareGroupException(ErrorCode.PET_REGISTRATION_RESTRICTIONS);
        }

        // NOTE * INSERT START *
        // CHECK 1. 그룹 등록
        careGroup.setResign_yn(Resign_yn.N);
        groupRepository.save(careGroup);
        CareGroup groupNum = groupRepository.findByLastGroupNumIsCareGroupType();

        //  CHECK 2. 그룹에 사람 집어넣기
        List<CareGroupMember> careGroupMemberInsert = new ArrayList<>();
        for(String member : memberId) {
            String role = String.valueOf(Role.guest);
            if(member.equals(currentMemberId)) {
                role = String.valueOf(Role.admin);
            }

            CareGroupMemberDTO cgmDTO = CareGroupMemberDTO
                                                    .builder()
                                                        .groupNum(groupNum)
                                                        .member_id(member)
                                                        .role(Role.valueOf(role))
                                                        .resign_yn(Resign_yn.N)
                                                    .build();
            ModelMapper mapper = new ModelMapper();
            CareGroupMember careGroupMember = mapper.map(cgmDTO, CareGroupMember.class);
            careGroupMemberInsert.add(careGroupMember);
        }
        careGroupMemberRepository.saveAll(careGroupMemberInsert);

        // CHECK 3. 반려동물 등록
        if(petNum.size() != 0) {
            List<CareTarget> careTargetInsert = new ArrayList<>();
            int CareTargetInsertGroupNum = groupNum.getGroup_num();
            for(int pet : petNum) {
                CareTargetDTO ctDTO = CareTargetDTO
                        .builder()
                        .group_num(CareTargetInsertGroupNum)
                        .pet_num(pet)
                        .build();
                ModelMapper mapper = new ModelMapper();
                CareTarget careTarget =  mapper.map(ctDTO, CareTarget.class);
                careTargetInsert.add(careTarget);
            }
            careTargetRepository.saveAll(careTargetInsert);
        }

        result = 1;
        return result;
    }

    // NOTE 그룹 삭제 ( eunae )
    @Transactional
    public int groupDelete(int groupNum, String currentMemberId) {
        int result = 0;
        // NOTE 예외처리 :
        // CHECK 1. groupNum과 currentMemberId가 없을 때
        if(groupNum == 0 || currentMemberId.equals("")) {
            throw new CareGroupException(ErrorCode.NOT_FOUND);
        }
        // CHECK 2. 로그인한 사람이 admin이 아닐 경우
        CareGroupMember careGroupMember = careGroupMemberRepository.findByGroupNumAndMemberId(groupNum, currentMemberId);
        if(String.valueOf(careGroupMember.getRole()).toLowerCase() != "admin") {
            result = -1;
            throw new CareGroupException(ErrorCode.PERMISSION_RESTRICTIONS);
        }

        // NOTE * GROUP DELETE *
        // CHECK 1. 펫 삭제 ( DELETE )
        List<CareTarget> careTargetList = careTargetRepository.findByGroupNumInPet(groupNum);
        for(CareTarget ct : careTargetList) {
            careTargetRepository.delete(ct);
        }
        // CHECK 2. 멤버 삭제 여부 변경 ( UPDATE )
        careGroupMemberRepository.groupMemberResignYnUpdateAll(groupNum);
        // CHECK 3. 그룹 삭제 여부 변경 ( UPDATE )
        groupRepository.groupResignYnUpdate(groupNum);

        result = 1;
        return result;
    }
}
