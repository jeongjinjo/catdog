package com.example.catdog.care_group;

import com.example.catdog.care_target.CareTargetRepository;
import com.example.catdog.care_target.Care_target;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.MemberExcption;
import com.example.catdog.member.Member;
import com.example.catdog.member.MemberRepository;
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
    private final PetRepository petRepository;
    private final CareTargetRepository careTargetRepository;
    private final MemberRepository memberRepository;

    // 유저가 속해있는 그룹과 그룹에 속해있는 유저 조회 ( eunae )
    public Map<Integer, List<Care_group>> groupList(String memberId) {
        // 특정 memberId가 멤버가 속한 그룹을 group_class를 기준으로 그룹화하는 Map
        Map<Integer, List<Care_group>> groupedByClass = new HashMap<>();

        // 특정 memberId가 멤버가 속한 그룹 리스트를 가져옴.
        Optional<List<Care_group>> careGroups = groupRepository.findByClassNumGrouped(memberId);
        if(careGroups.isEmpty()) {
            throw new MemberExcption(ErrorCode.NOT_FOUND);
        }
        // 가져온 그룹 리스트를 group_class를 기준으로 그룹화.
        for (Care_group careGroup : careGroups.get()) {
            Optional<Member> member = memberRepository.findByMemberId(careGroup.getMember().getMember_id());
            int groupClass = careGroup.getGroup_key();

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
            Care_target careTarget = careTargetRepository.getCareGroupNum(p.getPet_num());

            int groupNum = careTarget.getGroup_num();
            if (!petInformationByGroup.containsKey(groupNum)) {
                petInformationByGroup.put(groupNum, new ArrayList<>());
            }
            petInformationByGroup.get(groupNum).add(p);
        }

        return petInformationByGroup;
    }

    public int careGroupAndTagetInsert(Care_group careGroup) {


        return 0;
    }
}
