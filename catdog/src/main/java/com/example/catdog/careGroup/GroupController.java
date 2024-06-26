package com.example.catdog.careGroup;

import com.example.catdog.careGroup.dto.RequestDTO;
import com.example.catdog.careGroup.member.CareGroupMember;
import com.example.catdog.exception.CareGroupException;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.member.Member;
import com.example.catdog.member.MemberService;
import com.example.catdog.pet.Pet;
import com.example.catdog.pet.PetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("group")
@Tag(name = "CareGroupController", description = "그룹관리 관련 컨트롤러")
public class GroupController {
    private final GroupService groupService;
    private final PetService petService;
    private final MemberService memberService;

    // NOTE 유저가 속해있는 그룹 정보 조회 ( eunae ) CHECK 03.21 확인완료 03.22 확인완료
    @Operation(summary = "멤버가 속해있는 그룹 정보 조회"
            , description = "그룹관리 페이지의 화면을 만들어주기 위한 SELECT 기능")
    @Parameters(
            @Parameter(
                    description = "그룹 조회를 위해 아이디를 입력해주세요.",
                    name = "id",
                    required = true
            )
    )
    @GetMapping("{loginId}")
    public ResponseEntity<Map<Integer, List<CareGroupMember>>> groupList(@PathVariable String loginId) {
        Map<Integer,List<CareGroupMember>> list = groupService.groupList(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // NOTE 그룹에 속해있지 않은 내 반려동물 조회 ( eunae ) CHECK 03.21 확인완료 / 03.22 확인완료
    @Operation(summary = "그룹에 속해있지 않은 내 반려동물 조회"
            , description = "그룹에 내 반려동물을 등록하려고 할 때 사용할 수 있는 SELECT 기능")
    @Parameters(
            @Parameter(
                    description = "반려동물 조회를 위해 아이디를 입력해주세요.",
                    name = "id",
                    required = true
            )
    )
    @GetMapping("pet/{loginId}")
    public ResponseEntity<List<Pet>> getGroupNotInPet(@PathVariable String loginId) {
        List<Pet> pet = petService.getGroupNotInPet(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(pet);
    }

    // NOTE 그룹 내에 등록할 유저 검색 ( eunae ) CHECK 03.22 확인완료 / 03.28 수정완료
    @Operation(summary = "그룹 내에 등록할 유저 검색"
            , description = "나를 제외한 모든 유저를 닉네임 또는 아이디를 통해 검색할 수 있는 SELECT 기능")
    @Parameters({
            @Parameter(
                    description = "그룹 내 권한 체크를 위해 아이디를 입력해주세요.",
                    name = "loginId",
                    required = true
            ),
            @Parameter(
                    description = "조회할 아이디나 닉네임을 입력해주세요.",
                    name = "searchIdAndNickname",
                    required = true
            )
    })
    @GetMapping("search/{loginId}/{searchIdAndNickname}")
    public ResponseEntity<List<Member>> memberGroupInvite(@PathVariable String loginId, @PathVariable String searchIdAndNickname) {
        List<Member> list = memberService.memberGroupInvite(loginId, searchIdAndNickname);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // NOTE 그룹에 속해있는 반려동물 확인하기 ( eunae ) CHECK 03.22 확인완료
    @Operation(summary = "그룹에 속해있는 반려동물 조회"
            , description = "나를 제외한 모든 유저를 닉네임 또는 아이디를 통해 검색할 수 있는 SELECT 기능")
    @GetMapping("groupInPet/{loginId}")
    public ResponseEntity<Map<Integer, List<Pet>>> getGroupInfoPet(@PathVariable String loginId) {
        Map<Integer,List<Pet>> list = groupService.getGroupInfoPet(loginId);
        return ResponseEntity.status(HttpStatus.OK).body(list);
    }

    // NOTE 그룹 등록 ( eunae ) CHECK 03.23 생성
    @Operation(summary = "그룹 등록"
            , description = "그룹 등록 시, 회원과 반려동물도 동시에 등록할 수 있는 INSERT 기능 / groupDTO에 group_num는 삭제한 후 테스트 해주세요.")
    @PostMapping
    public ResponseEntity<Integer> careGroupAndMemberAndTargetInsert(@Valid @RequestBody RequestDTO requestDTO) {
        ModelMapper mapper = new ModelMapper();
        CareGroup careGroupDb = mapper.map(requestDTO.getGroupDTO(), CareGroup.class);
        int result = groupService.groupInsert(careGroupDb
                , requestDTO.getMember_id()
                , requestDTO.getPet_num()
                , requestDTO.getCurrent_member_id());
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 그룹 삭제 ( eunae ) CHECK 03.24 생성 03.28 수정완료
    @Operation(summary = "그룹 삭제"
            , description = "삭제할 그룹 번호와 로그인한 아이디만 가져오면 그룹 삭제가 이루어지는 UPDATE 기능")
    @Parameters({
            @Parameter(
                    description = "그룹번호를 입력해주세요.",
                    name = "groupNum",
                    required = true
            ),
            @Parameter(
                    description = "그룹 내 권한 체크를 위해 아이디를 입력해주세요.",
                    name = "loginId",
                    required = true
            )
    })
    @PutMapping("{groupNum}/{loginId}")
    public ResponseEntity<Integer> careGroupAllDelete(@PathVariable int groupNum, @PathVariable String loginId) {
        int result = groupService.groupDelete(groupNum, loginId);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 그룹 수정 - 인원 삭제 및 등록 ( eunae ) CHECK 03.25 생성 / 03.27 수정완료
    @Operation(summary = "그룹 수정 - 인원 삭제 및 등록"
            , description = "멤버 목록에서 멤버를 지우면 삭제여부가 Y로 변하고 다시 등록하면 N으로 바뀌는 UPDATE 기능")
    @Parameters({
            @Parameter(
                    description = "그룹번호를 입력해주세요.",
                    name = "groupNum",
                    required = true
            ),
            @Parameter(
                    description = "그룹 내 권한 체크를 위해 아이디를 입력해주세요.",
                    name = "loginId",
                    required = true
            ),
            @Parameter(
                    description = "등록 및 삭제가 될 타겟(멤버)을 입력해주세요.",
                    name = "targetMember",
                    required = true
            )
    })
    @PutMapping("{groupNum}/{loginId}/{targetMember}")
    public ResponseEntity<Integer> groupInMemberOutUpdate(@PathVariable int groupNum
                                                        , @PathVariable String loginId
                                                        , @PathVariable String targetMember) {
        int result = groupService.groupInMemberInAndOutUpdate(groupNum, loginId, targetMember);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 그룹 수정 - 반려동물 삭제 ( eunae ) CHECK 03.25 생성
    @Operation(summary = "그룹 수정 - 반려동물 삭제"
            , description = "펫 목록에서 펫을 지우면 DB에서 삭제되는 DELETE 기능")
    @Parameters({
            @Parameter(
                    description = "그룹번호를 입력해주세요.",
                    name = "groupNum",
                    required = true
            ),
            @Parameter(
                    description = "그룹 내 권한 체크를 위해 아이디를 입력해주세요.",
                    name = "loginId",
                    required = true
            ),
            @Parameter(
                    description = "삭제할 반려동물 번호를 입력해주세요.",
                    name = "delTargetPet",
                    required = true
            )
    })
    @DeleteMapping("{groupNum}/{loginId}/{delTargetPet}")
    public ResponseEntity<Integer> groupInPetOutUpdate(@PathVariable int groupNum
            , @PathVariable String loginId
            , @PathVariable int delTargetPet) {
        int result = groupService.groupInPetOutUpdate(groupNum, loginId, delTargetPet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 그룹 수정 - 반려동물 등록 ( eunae ) CHECK 03.27 생성
    @Operation(summary = "그룹 수정 - 반려동물 등록"
            , description = "펫 목록에서 펫을 추가하면 반려동물이 등록되는 INSERT 기능")
    @Parameters({
            @Parameter(
                    description = "그룹번호를 입력해주세요.",
                    name = "groupNum",
                    required = true
            ),
            @Parameter(
                    description = "그룹 내 권한 체크를 위해 아이디를 입력해주세요.",
                    name = "loginId",
                    required = true
            ),
            @Parameter(
                    description = "그룹에 등록할 반려동물 번호를 입력해주세요.",
                    name = "inTargetPet",
                    required = true
            )
    })
    @PostMapping("{groupNum}/{loginId}/{inTargetPet}")
    public ResponseEntity<Integer> groupInPetInUpdate(@PathVariable int groupNum
            , @PathVariable String loginId
            , @PathVariable int inTargetPet) {
        int result = groupService.groupInPetInUpdate(groupNum, loginId, inTargetPet);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // NOTE 그룹 수정 ( eunae ) CHECK 03.27 생성 03.28 수정완료
    @Operation(summary = "그룹 수정"
            , description = "현재 로그인한 아이디의 권한을 확인하여 그룹에 대한 정보를 수정할 수 있는 UPDATE 기능")
    @Parameters({
            @Parameter(
                    description = "수정할 그룹의 번호를 입력해주세요.",
                    name = "groupNum",
                    required = true
            ),
            @Parameter(
                    description = "권한 체크를 위한 아이디 입력은 필수입니다.",
                    name = "id",
                    required = true
            )
    })
    @PutMapping("up/{groupNum}/{loginId}")
    public ResponseEntity<Integer> careGroupUpdate(@PathVariable int groupNum, @PathVariable String loginId, @RequestBody String groupName) {
        String gn = groupName.replace("\"", "");
        int result = groupService.groupUpdate(groupNum, loginId, gn);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
