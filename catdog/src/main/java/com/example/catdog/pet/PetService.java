package com.example.catdog.pet;

import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class PetService {
    @Autowired //petRepository 의 인스턴트를 주입할 수 있는 어노테이션.
    PetRepository petRepository;

    //PET 정보 등록 기능
    @Transactional
    public Pet createPet(Pet pet) {
        //Pet 을 반환하기 때문에
        int petCount = petRepository.myPetCountByMemberId(pet.getMember_id());
        System.out.println("petCount >>>> " + petCount);

//        if(petCount < 6){
//            return petRepository.save(pet);
//        }else{
//            throw new RuntimeException("펫을 더이상 등록할 수 없습니다. 5마리 이상입니다.");
//        }

        return null;
    }//save 메소드는 Spring Data JPA 에서 제공하는 메소드, 주어진 앤티티를 데이터베이스에 저장하고 저장된 앤티티를 반환한다.

   // member_id로 조회 가능
    public Pet getPetByMemberId(String member_id){
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


}

