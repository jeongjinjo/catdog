package com.example.catdog.main;

import com.example.catdog.careGroup.CareGroup;
import com.example.catdog.careGroup.GroupRepository;
import com.example.catdog.enum_column.Gender;
import com.example.catdog.member.Member;
import com.example.catdog.member.MemberRepository;
import com.example.catdog.pet.Pet;
import com.example.catdog.pet.PetRepository;
import com.example.catdog.todo.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MainService {

    private final PetRepository petRepository;

    // 로그인 한 회원의 그룹별 반려동물 정보
    public Map<Integer, List<Object>> getGroupInfoPet(String id){
        Optional<List<Object[]>> petInfo = petRepository.findByGroupInfoPet(id);
        Map<Integer, List<Object>> groupPets = new HashMap<>();

        for(Object[] info : petInfo.get()){
            Integer groupNum = (Integer) info[0];

            Pet pet = new Pet();

            String groupName = (String) info[1];
            pet.setGroupName(groupName);
            pet.setPet_num((Integer) info[2]);
            pet.setPet_name((String) info[3]);
            pet.setGender((Gender) info[4]);
            pet.setAge((Integer) info[5]);
            pet.setDisease((String) info[6]);

            String photoRoute = (String) info[7];
            pet.setPhotoRoute(photoRoute);

            groupPets.putIfAbsent(groupNum, new ArrayList<>());
            groupPets.get(groupNum).add(pet);
        }
        return groupPets;
    }
}
