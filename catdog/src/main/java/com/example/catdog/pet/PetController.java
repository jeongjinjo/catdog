package com.example.catdog.pet;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/pet")
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private PetRepository petRepository;
    //반려동물 전체 조회
    //굳이 필요 함? ? ? ? ?   그냥 id로 조회하면되잖음..? 전체 조회할 일이 있을까

    // id를 통해 반려동물 조회 (id가 중복 허용이 안되니까..) 이게 편할 듯 id가 pet_num과 연결.
    @GetMapping("/select/{pet_num}")
    public ResponseEntity<Pet> getPet(@PathVariable int pet_num) {
        Pet pet = petService.getPetById(pet_num);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pet);
    }
    //애완동물 등록
    @PostMapping("/insert")
    public ResponseEntity<Pet> addPet(@Valid @RequestBody PetDto petDto) {
        //클라이언트가 전송한 요청을 UserDto 객체로 받아온다..
        //@RequestBody는 HTTP 요청 본문을 해당 객체로 변환.
        //@Valid 는 UserDto 객체의 유효성 검사를 수행한다.
        //유효성 검사를 통과한 데이터만 처리한다.

        ModelMapper mapper = new ModelMapper(); //mapper도 dependencies가 필요.
        Pet pet = mapper.map(petDto, Pet.class);

        Pet dbpet = petService.createPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(dbpet);
    }
    //수정
    @PutMapping("/update/{pet_num}")
    public ResponseEntity<Pet> updatePet(@PathVariable Long pet_num, @Valid @RequestBody PetDto petDto) {




        return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
    }
    //삭제


}
