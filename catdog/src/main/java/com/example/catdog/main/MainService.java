package com.example.catdog.main;

import com.example.catdog.enum_column.Gender;
import com.example.catdog.member.Member;
import com.example.catdog.member.MemberRepository;
import com.example.catdog.pet.Pet;
import com.example.catdog.pet.PetRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class MainService {

    private final PetRepository petRepository;
    private final MemberRepository memberRepository;

    // 로그인 한 회원의 그룹별 반려동물 정보
    public Map<Integer, List<Object>> getGroupInfoPet(String id){
//        Optional<List<Object[]>> petInfo = petRepository.findByGroupInfoPet(id);
        Map<Integer, List<Object>> groupPets = new HashMap<>();
//
//        for(Object[] info : petInfo.get()){
//            Integer groupName = (Integer) info[0];
//
//            Pet pet = new Pet();
//
//            pet.setPet_name((String) info[1]);
//            pet.setGender((Gender) info[2]);
//            pet.setAge((Integer) info[3]);
//            pet.setDisease((String) info[4]);
//
//            groupPets.putIfAbsent(groupName, new ArrayList<>());
//
//            groupPets.get(groupName).add(pet);
//        }
        return groupPets;
    }
    // 로그인 한 회원이 속한 그룹의 그룹별 회원닉네임
    public Map<Integer, List<Object>> getGroupInfoMember(String id){
        Optional<List<Object[]>> memberInfo = memberRepository.findByGroupMember(id);
        Map<Integer, List<Object>> groupMembers = new HashMap<>();

        for(Object[] info : memberInfo.get()){
            Integer groupName = (Integer) info[0];

            Member member = new Member();

            member.setNickname((String) info[1]);

            groupMembers.putIfAbsent(groupName, new ArrayList<>());
            groupMembers.get(groupName).add(member);
        }
        return groupMembers;
    }
}
