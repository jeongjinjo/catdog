package com.example.catdog.pet;

import com.example.catdog.enum_column.Gender;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Type;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.PetException;
import com.example.catdog.pet.photo.Photo;
import com.example.catdog.pet.photo.PhotoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.example.catdog.exception.ErrorCode.PET_REGISTRATION_RESTRICTIONS;


@Service
@RequiredArgsConstructor
public class PetService {
    private final PetRepository petRepository;
    private ErrorCode errorCode;
    private final PhotoRepository photoRepository;

    //PET 정보 등록 기능
    public Pet createPet(Pet pet) {
        // Pet 을 반환하기 때문에
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String member_id = authentication.getName();
        pet.setMember_id(member_id);
        int petCount = petRepository.countByMemberIdIsMyPet(pet.getMember_id());
        pet.setResign_yn(Resign_yn.N);

        if(petCount < 5){
            return petRepository.save(pet);
        }else{
            throw new PetException (errorCode.PET_REGISTRATION_RESTRICTIONS);
        }

    }//save 메소드는 Spring Data JPA 에서 제공하는 메소드, 주어진 앤티티를 데이터베이스에 저장하고 저장된 앤티티를 반환한다.

   // member_id로 조회 가능
    public List<Pet> getPetsByMemberId(String member_id){
        return petRepository.findByMemberId(member_id);
    }

    //pet 정보 조회 기능
    public Pet getPetById(int pet_num) {

        Optional<Pet> optionalPet =petRepository.findById(pet_num);
        return optionalPet.get();
    }

    public Pet updatePet(int pet_num, String pet_name, String disease, int age, Type type,  Gender gender, float kg) {
        //        updatedPet.setId(idx);
        Optional<Pet> optionalPet = petRepository.findById(pet_num);
        System.out.println(optionalPet);
        if (optionalPet.isPresent()) {
            Pet dbPet = optionalPet.get();
            dbPet.setPet_num(pet_num);
            dbPet.setAge(age);
            dbPet.setDisease(disease);
            dbPet.setKg(kg);
            dbPet.setGender(gender);
            dbPet.setType(type);
            dbPet.setPet_name(pet_name);
            return petRepository.save(dbPet);
        }
        return new Pet();
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
//    public Pet petCreate(Pet pet) {
//        int petCount = petRepository.countByMemberIdIsMyPet(pet.getMember_id());
//        // 로그인한 사람가 등록한 반려동물이 5마리 초과일 시 반려동물 등록불가하도록 진행
//        if(petCount >= 5) {
//            throw new PetException(ErrorCode.PET_REGISTRATION_RESTRICTIONS);
//        }
//        // 본격적인 펫 등록
//        pet.setResign_yn(Resign_yn.N);
//        Pet db = petRepository.save(pet);
//        return db;
//    }

}

