package com.example.catdog.pet;

import com.example.catdog.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pet")

public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private PetRepository petRepository;

    //Member 테이블에 member_id를 통해 pet 조회
    @Operation(summary = "반려동물 정보 조회",
            description = "사용자 ID를 이용해 반려동물 정보 조회")
    @GetMapping("/select/member/{member_id}")
    public ResponseEntity<Pet> getPetById(@PathVariable String member_id){
        Pet pet =petService.getPetByMemberId(member_id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pet);
    }

    // id의 pet_num 을 이용해 pet의 정보 조회(개별적으로 조회 하는 기능)
    @Operation(summary = "반려동물 개별 정보 조회",
            description = "펫 등록시 자동으로 생성된 번호인 pet_num을 이용해 반려동물 정보 조회")
    @GetMapping("/select/{pet_num}")
    public ResponseEntity<Pet> getPet(@PathVariable int pet_num) {
        Pet pet = petService.getPetById(pet_num);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pet);
    }

    //애완동물 등록 (애완동물 5개까지 등록되여야 함)
    @Operation(summary = "반려동물 정보 등록",
            description = "반려동물 나이,질병,체중 등 케어에 필요한 정보 등록")
    @PostMapping("/insert")
    public ResponseEntity<Pet> addPet(@Valid @RequestBody PetDTO petDto) {
        //클라이언트가 전송한 요청을 UserDto 객체로 받아온다..
        //@RequestBody는 HTTP 요청 본문을 해당 객체로 변환.
        //@Valid 는 UserDto 객체의 유효성 검사를 수행한다.
        //유효성 검사를 통과한 데이터만 처리한다.
        ModelMapper mapper = new ModelMapper();
        Pet pet = mapper.map(petDto, Pet.class);
        Pet dbpet = petService.createPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(dbpet);
    }
    @Operation(summary = "반려동물 정보 수정",
            description = "pet_num을 이용해 반려동물의 정보를 수정")
    //수정
    @PutMapping("/update/{pet_num}")
    public ResponseEntity<Pet> updatePet(@RequestBody @Valid PetDTO petDto) {
        ModelMapper mapper=new ModelMapper();
        Pet pet=mapper.map(petDto,Pet.class);

        //Pet pet은 된게 없으니까 pet.getPet_num() 해서 Pet에 있는걸 가지고 와줘야함.
        Pet dbPet=petService.updatePet(pet.getPet_num(), petDto); //getpet_num이 아니라 어떻게 getPet_num을 가지고 오는겨?! 물음표 ?!?!?

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(dbPet);
    }

    //삭제
    @Operation(summary = "반려동물 정보 삭제",
            description = "pet_num을 이용해 반려동물의 정보를 삭제")
    @DeleteMapping("delete/{pet_num}")
    public ResponseEntity<String> deletedbyPet(@PathVariable int pet_num){
         petService.deletePet(pet_num);
         return ResponseEntity.status(HttpStatus.ACCEPTED).body("삭제 완료되었습니다.");
    }
}
