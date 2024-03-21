package com.example.catdog.main;

import com.example.catdog.care_group.Care_group;
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
    public List<Pet> getGroupInfoPet(String id){
        List<Object[]> petinfo = petRepository.findByGroupInfoPet(id);
        List<Pet> pets = new ArrayList<>();

        for(Object[] info : petinfo){
            Pet pet = new Pet();
            Care_group careGroup = new Care_group();

            careGroup.setGroup_name((String) info[0]);
            pet.setPet_name((String) info[1]);
            pet.setGender((Gender) info[2]);
            pet.setAge((Integer) info[3]);
            pet.setDisease((String) info[4]);

            pets.add(pet);
        }
        return pets;
    }
}
