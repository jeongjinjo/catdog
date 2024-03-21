package com.example.catdog.main;

import com.example.catdog.enum_column.Gender;
import com.example.catdog.pet.Pet;
import com.example.catdog.pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MainService {

    private final PetRepository petRepository;

    // 로그인 한 회원의 그룹별 반려동물 정보
    public Map<String, List<Object>> getGroupInfoPet(String id){
        Optional<List<Object[]>> petinfo = petRepository.findByGroupInfoPet(id);
        Map<String, List<Object>> groupPets = new HashMap<>();

        for(Object[] info : petinfo.get()){
            String groupname = (String) info[0];

            Pet pet = new Pet();

            pet.setPet_name((String) info[1]);
            pet.setGender((Gender) info[2]);
            pet.setAge((Integer) info[3]);
            pet.setDisease((String) info[4]);

            groupPets.putIfAbsent(groupname, new ArrayList<>());

            groupPets.get(groupname).add(pet);
        }
        return groupPets;
    }
}
