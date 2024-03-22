package com.example.catdog.care_group;

import com.example.catdog.care_target.CareTargetRepository;
import com.example.catdog.care_target.Care_target;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
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

    // 그룹 등록 ( eunae )
    @Transactional
    public int careGroupAndTagetInsert(Care_group careGroup) {
        int result = 0;

        // 1. HOST인 주인장의 정보와 그룹을 만든다
        //   1.1. 근데 그룹이 3개 이상이면 INSERT 못하게 해야하잖아?
        Long groupCount = groupRepository.countByMemberIdAndResignYn(careGroup.getMember().getMember_id());
        if(groupCount > 2L) {
            throw new MemberExcption(ErrorCode.GROUP_REGISTRATION_RESTRICTIONS);
        }
        //   1.2. 그룹 정상 등록
        careGroup.setGroup_num(groupRepository.findNextGroupNum());
        careGroup.setRole(Role.HOST);
        careGroup.setResign_yn(Resign_yn.N);
        careGroup.setGroup_key(groupRepository.findNextGroupNum());
        Care_group cg = groupRepository.save(careGroup);

        // 2. 1번이 정상적으로 진행되면 그룹에 GUEST를 넣어준다.
        Optional<Care_group> findCgInfo
                = groupRepository.findGoupHostInfo(careGroup.getMember().getMember_id(), groupRepository.findNextGroupNum());
        //   2.1. 등록이 되지 않았다면 예외처리
        if(findCgInfo.isEmpty()) {
            throw new MemberExcption(ErrorCode.NOT_FOUND);
        }
        //   2.2. GUEST를 최대 3명까지 등록할 수 있도록 진행하기


        // 3. 2번이 정상적으로 진행되면 그룹에 등록할 반려동물을 등록해준다.

        if(cg != null) {
            result = 1;
        }



        return result;
    }
}
