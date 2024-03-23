package com.example.catdog.careGroup;

import com.example.catdog.careGroup.member.CareGroupMember;
import com.example.catdog.careGroup.member.CareGroupMemberRepository;
import com.example.catdog.careGroup.target.CareTargetRepository;
import com.example.catdog.careGroup.target.CareTarget;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.MemberExcption;
import com.example.catdog.pet.Pet;
import com.example.catdog.pet.PetRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
            throw new MemberExcption(ErrorCode.NOT_FOUND);
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

    // 그룹 등록 ( eunae )
    @Transactional
    public int groupInsert(CareGroupMember careGroupMember) {

        int result = 0;
        // 1. 주인장이 그룹을 3개 가지고 있으면 등록을 못하게 해야해
        int gorupCount = careGroupMemberRepository.countByMemberIdAndResignYn(careGroupMember.getMember().getMember_id());
        // 2. admin인 주인장이 그룹을 만들어



        // 2. HOST인 주인장의 정보와 그룹을 만든다
        //   1.1. 근데 그룹이 3개 이상이면 INSERT 못하게 해야하잖아?
//        Long groupCount = groupRepository.countByMemberIdAndResignYn(careGroup.getMember().getMember_id());
//        if(groupCount > 2L) {
//            throw new MemberExcption(ErrorCode.GROUP_REGISTRATION_RESTRICTIONS);
//        }
//        //   1.2. 그룹 정상 등록
//        careGroup.setResign_yn(Resign_yn.N);
//        CareGroup cg = groupRepository.save(careGroup);
//
//        // 2. 1번이 정상적으로 진행되면 그룹에 GUEST를 넣어준다.
//        Optional<CareGroup> findCgInfo
//                = groupRepository.findGoupHostInfo(careGroup.getMember().getMember_id(), groupRepository.findNextGroupNum());
//
//
//
//
//        // 3. 2번이 정상적으로 진행되면 그룹에 등록할 반려동물을 등록해준다.
//
//        if(cg != null) {
//            result = 1;
//        }



        return result;
    }
}
