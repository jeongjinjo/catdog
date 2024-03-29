package com.example.catdog.pet;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.PetException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PetService {
    @Autowired //petRepository 의 인스턴트를 주입할 수 있는 어노테이션.
    PetRepository petRepository;

    //PET 정보 등록 기능
    public Pet createPet(Pet pet) {
        //Pet 을 반환하기 때문에
        return petRepository.save(pet);
    }//save 메소드는 Spring Data JPA 에서 제공하는 메소드, 주어진 앤티티를 데이터베이스에 저장하고 저장된 앤티티를 반환한다.

   // member_id로 조회 가능
    public Pet getPetBymemberId(String member_id){
        Pet pet=petRepository.findByMemberId(member_id);
        return pet;
    }

    //pet 정보 조회 기능
    public Pet getPetById(int pet_num) {

        Optional<Pet> optionalPet =petRepository.findById(pet_num);
        return optionalPet.get();
    }

    public Pet updatePet(int pet_num, PetDTO petDto) {
        //        updatedPet.setId(idx);
        Optional<Pet> optionalPet = petRepository.findById(pet_num);
        if (optionalPet.isPresent()) {
            Pet existingPet = optionalPet.get();
            ModelMapper mapper = new ModelMapper();
            mapper.map(petDto, existingPet);
            //
            return petRepository.save(existingPet);

        } else {
            return null;
        }
    }

    public void deletePet(int pet_num) {
        petRepository.deleteById(pet_num);
    }

    //전체 조회가 굳이 필요하나?
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    // 내가 등록한 반려동물 중 그룹에 등록되지 않은 반려동물 목록 조회 기능 ( eunae )
    public List<Pet> getGroupNotInPet(String id) {
        return petRepository.findByGroupNotInPet(id);
    }

    // eunae 펫등록 테스트
    public Pet petCreate(Pet pet) {
        int petCount = petRepository.countByMemberIdIsMyPet(pet.getMember_id());
        // 로그인한 사람가 등록한 반려동물이 5마리 초과일 시 반려동물 등록불가하도록 진행
        if(petCount >= 5) {
            throw new PetException(ErrorCode.PET_REGISTRATION_RESTRICTIONS);
        }
        // 본격적인 펫 등록
        pet.setResign_yn(Resign_yn.N);
        Pet db = petRepository.save(pet);
        return db;
    }
}

