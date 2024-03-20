package com.example.catdog.group;

import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.MemberExcption;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class GroupService {
    private final GroupRepository groupRepository;

    public Map<Integer, List<CareGroup>> groupList(String memberId) {
        // memberId가 123123인 멤버가 속한 그룹을 group_class를 기준으로 그룹화하는 Map
        Map<Integer, List<CareGroup>> groupedByClass = new HashMap<>();

        // memberId가 123123인 멤버가 속한 그룹 리스트를 가져옴.
        Optional<List<CareGroup>> careGroups = groupRepository.findByClassNumGrouped(memberId);

        if(careGroups.isEmpty()) {
            throw new MemberExcption(ErrorCode.NOT_FOUND);
        }

        // 가져온 그룹 리스트를 group_class를 기준으로 그룹화.
        for (CareGroup careGroup : careGroups.get()) {
            int groupClass = careGroup.getGroup_class();
            if (!groupedByClass.containsKey(groupClass)) {
                groupedByClass.put(groupClass, new ArrayList<>());
            }
            groupedByClass.get(groupClass).add(careGroup);
        }

        // 그룹화된 결과를 반환
        return groupedByClass;
    }
}
