package com.example.catdog.pet;

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
    PetDto petDto;

    //PET 정보 등록 기능
    public Pet createPet(Pet pet) {
        //Pet 을 반환하기 때문에
        return petRepository.save(pet);
    }//save 메소드는 Spring Data JPA 에서 제공하는 메소드, 주어진 앤티티를 데이터베이스에 저장하고 저장된 앤티티를 반환한다.

    //pet 정보 조회 기능
    public Pet getPetById(Long pet_num) {

        Optional<Pet> optionalPet =petRepository.findById(pet_num);
        return optionalPet.get();
    }

    public Pet updatePet(Long pet_num, PetDto petDto) {
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

    public void deletePet(Long pet_num) {
        petRepository.deleteById(pet_num);
    }

    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }
}

