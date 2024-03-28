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
            throw new CareGroupException(ErrorCode.EMPTY_VALUE);
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

    // NOTE 그룹 수정 - 인원 삭제 및 등록 ( eunae ) CHECK 03.27 수정완료
    @Transactional
    public int groupInMemberInAndOutUpdate(int groupNum, String loginId, String targetMember) {
        int result = 0;

        // NOTE 예외처리
        // CHECK 1. 빈 값일 경우
        if(groupNum == 0 || targetMember.equals("") || loginId.equals("")) {
            result = -1;
            throw new CareGroupException(ErrorCode.EMPTY_VALUE);
        }
        // CHECK 2. loginId가 admin이 아닐 경우
        CareGroupMember member = careGroupMemberRepository.findByGroupNumAndMemberId(groupNum, loginId);
        if(String.valueOf(member.getRole()).toLowerCase() != "admin") {
            result = -1;
            throw new CareGroupException(ErrorCode.PERMISSION_RESTRICTIONS);
        }
        // CHECK 3. 멤버는 0명이 아닌지?
        int memberCount = careGroupMemberRepository.countByGroupNumAndResignYn(groupNum);
        System.err.println("memberCount >>>>>>>>> " + memberCount);
        if(memberCount == 0) {
            throw new CareGroupException(ErrorCode.MEMBER_NOT_FOUND);
        }
        // CHECK 4. 멤버는 최대 4명까지만 등록이 가능하다.
        if(memberCount > 4) {
            throw new CareGroupException(ErrorCode.LIMITED_NUMBER_OF_MEMBER_REGISTERED);
        }

        // NOTE 멤버 삭제 및 수정
        CareGroupMember target = careGroupMemberRepository.findByGroupNumAndMemberId(groupNum, targetMember);
        if(String.valueOf(target.getResign_yn()).toUpperCase() == "N") {
            target.setResign_yn(Resign_yn.Y);
            careGroupMemberRepository.save(target);
        } else if(String.valueOf(target.getResign_yn()).toUpperCase() == "Y") {
            target.setResign_yn(Resign_yn.N);
            careGroupMemberRepository.save(target);
        }

        result = 1;
        return result;
    }

    // NOTE 그룹 수정 - 반려동물 삭제 ( eunae )
    public int groupInPetOutUpdate(int groupNum, String loginId, int deleteTarget) {
        int result = 0;

        // CHECK 1. 빈 값일 경우
        if(groupNum == 0 || deleteTarget == 0 || loginId.equals("")) {
            result = -1;
            throw new CareGroupException(ErrorCode.NOT_FOUND);
        }
        // CHECK 2. loginId가 admin이 아닐 경우
        CareGroupMember member = careGroupMemberRepository.findByGroupNumAndMemberId(groupNum, loginId);
        if(String.valueOf(member.getRole()).toLowerCase() != "admin") {
            result = -1;
            throw new CareGroupException(ErrorCode.PERMISSION_RESTRICTIONS);
        }

        // NOTE 반려동물 삭제
        CareTarget pet = careTargetRepository.findByGroupNumInPetInformation(groupNum, deleteTarget);
        careTargetRepository.delete(pet);

        return result;
    }

    // NOTE 그룹 수정 - 반려동물 등록 ( eunae )
    @Transactional
    public int groupInPetInUpdate(int groupNum, String loginId, int inTargetPet) {
        int result = 0;

        // NOTE 예외처리
        // CHECK 1. 빈 값일 경우
        if(groupNum == 0 || inTargetPet == 0 || loginId.equals("")) {
            result = -1;
            throw new CareGroupException(ErrorCode.EMPTY_VALUE);
        }
        // CHECK 2. loginId가 admin이 아닐 경우
        CareGroupMember member = careGroupMemberRepository.findByGroupNumAndMemberId(groupNum, loginId);
        if(String.valueOf(member.getRole()).toLowerCase() != "admin") {
            result = -1;
            throw new CareGroupException(ErrorCode.PERMISSION_RESTRICTIONS);
        }
        //  CHECK 3. 펫은 최대 5마리까지만 등록이 가능하다.
        int groupInPetCount = careTargetRepository.getGroupInPetCount(groupNum);
        if(groupInPetCount > 5) {
            throw new CareGroupException(ErrorCode.PET_REGISTRATION_RESTRICTIONS);
        }
        // CHECK 4. 중복 등록 방지
        List<CareTarget> ctList = careTargetRepository.findByGroupNumInPet(groupNum);
        List<Integer> petNumList = new ArrayList<>();
        for(CareTarget pet : ctList) {
            petNumList.add(pet.getPet_num());
        }
        // 현재 등록되어있는 펫 번호와 등록하려는 펫 번호가 동일하면 예외 던져주기
        for(int pet : petNumList) {
            if(inTargetPet == pet) {
                result = -1;
                throw new CareGroupException(ErrorCode.DUPLICATE);
            }
        }

        // NOTE 반려동물 등록처리
        CareTarget careTarget = CareTarget.builder()
                                            .group_num(groupNum)
                                            .pet_num(inTargetPet)
                                            .build();
        careTargetRepository.save(careTarget);
        result = 1;
        return result;
    }

    // NOTE 그룹 수정 ( eunae )
    public int groupUpdate(CareGroup careGroup, String loginId) {
        int result = 0;

        // NOTE 예외처리
        // CHECK 1. 빈값으로 넘어오는지?
        if(careGroup == null || loginId.equals("")) {
            result = -1;
            throw new CareGroupException(ErrorCode.EMPTY_VALUE);
        }
        // CHECK 2. 로그인한 아이디가 그룹을 수정할 수 있는 권한인지?
        CareGroupMember roleCheck = careGroupMemberRepository.findByGroupNumAndMemberId(careGroup.getGroup_num(), loginId);
        if(String.valueOf(roleCheck.getRole()).toLowerCase() != "admin") {
            result = -1;
            throw new CareGroupException(ErrorCode.PERMISSION_RESTRICTIONS);
        }
        // NOTE 그룹 수정 진행
        CareGroup cg = CareGroup.builder()
                                .group_num(careGroup.getGroup_num())
                                .group_name(careGroup.getGroup_name())
                                .resign_yn(Resign_yn.N)
                                .build();
        groupRepository.save(cg);
        result = 1;
        return result;
    }

}
