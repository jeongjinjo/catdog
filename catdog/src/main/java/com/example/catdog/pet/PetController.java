package com.example.catdog.pet;

import com.example.catdog.enum_column.Gender;
import com.example.catdog.member.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pet")
@Tag(name = "PetController", description = "Pet의 정보 조회,등록,수정,삭제 기능을 위한 Controller입니다.")
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private PetRepository petRepository;

    //Member 테이블에 member_id를 통해 pet 조회
    @Operation(summary = "반려동물 정보 조회",
            description = "사용자 ID를 이용해 반려동물 정보 조회")
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<Pet>> getPetById(@PathVariable String memberId){
        List<Pet> pets =petService.getPetsByMemberId(memberId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pets);
    }

    // id의 pet_num 을 이용해 pet의 정보 조회(개별적으로 조회 하는 기능)
    @Operation(summary = "반려동물 개별 정보 조회",
            description = "펫 등록시 자동으로 생성된 번호인 pet_num을 이용해 반려동물 정보 조회")
    @GetMapping("/{petNum}")
    public ResponseEntity<Pet> getPet(@PathVariable int petNum) {
        Pet pet = petService.getPetById(petNum);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pet);
    }

    //애완동물 등록 (애완동물 5개까지 등록 가능)
    @Operation(summary = "반려동물 정보 등록",
            description = "반려동물 나이,질병,체중 등 케어에 필요한 정보 등록")
    @PostMapping("")
    public ResponseEntity<Pet> addPet(@Valid @RequestBody PetDTO petDto) {
        ModelMapper mapper = new ModelMapper();
        Pet pet = mapper.map(petDto, Pet.class);
        Pet dbpet = petService.createPet(pet);
        return ResponseEntity.status(HttpStatus.CREATED).body(dbpet);
    }

    @Operation(summary = "반려동물 정보 수정",
            description = "petNum을 이용해 반려동물의 정보를 수정")
    @PutMapping("/{petNum}")
    public ResponseEntity<Pet> updatePet(@PathVariable int petNum , @RequestBody @Valid PetDTO petDto) {
        ModelMapper mapper=new ModelMapper();
        Pet pet=mapper.map(petDto,Pet.class);
        pet = petService.updatePet(petNum, pet.getPet_name(), pet.getDisease(), pet.getAge(), pet.getType(), pet.getGender(), pet.getKg());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(pet);
    }

    //삭제
    @Operation(summary = "반려동물 정보 삭제",
            description = "petNum을 이용해 반려동물의 정보를 삭제")
    @DeleteMapping("/{petNum}")
    public ResponseEntity<String> deletedbyPet(@PathVariable int petNum){
         petService.deletePet(petNum);
         return ResponseEntity.status(HttpStatus.ACCEPTED).body("삭제 완료되었습니다.");
    }
}