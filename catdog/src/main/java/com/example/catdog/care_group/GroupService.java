package com.example.catdog.care_group;

import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.MemberExcption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Map<Integer, List<Care_group>> groupList(String memberId) {
        // memberId가 123123인 멤버가 속한 그룹을 group_class를 기준으로 그룹화하는 Map
        Map<Integer, List<Care_group>> groupedByClass = new HashMap<>();

        // memberId가 123123인 멤버가 속한 그룹 리스트를 가져옴.
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
}
