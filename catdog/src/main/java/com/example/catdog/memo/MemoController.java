package com.example.catdog.memo;

import com.example.catdog.enum_column.Resign_yn;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("memo")
@Tag(name = "MemoController", description = "반려동물 별 메모 컨트롤러")
public class MemoController {

    private final MemoService memoService;
    private final MemoRepository memoRepository;

    /**
     * @param memo : 메모
     * @return : 반려동물 별 메모 등록하기
     */
    @Operation(summary = "반려동물의 메모 등록"
            , description = "사용 키: memo, pet_num /메모에 값을 넣기 위한 위한 INSERT 기능/")
    @PostMapping()
    public Memo insertMemo(@RequestBody Memo memo){
        Memo memos = Memo.builder()
                .memo(memo.getMemo())
                .date(LocalDateTime.now())
                .pet_num(memo.getPet_num())
                .resign_yn(Resign_yn.N)
                .build();
        Memo dbMemo = memoRepository.save(memos);
        return dbMemo;
    }

    /**
     * @param loginId : 로그인 한 아이디
     * @param petNum : 반려동물 고유번호
     * @param date : 날짜 확인
     * @return : 반려동물 별 메모 조회하기
     */
    @Operation(summary = "로그인 한 아이디를 이용해 반려동물의 메모 확인"
            , description = "메모를 가져오기 위한 SELECT 기능 / date 예시 : 2024/03/28")
    @GetMapping()
    public ResponseEntity<List<Object>> selectMemo(@RequestParam String loginId, Integer petNum, Date date){
        List<Object> memo = memoService.todayMemoList(loginId, petNum, date);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memo);
    }

    /**
     * @return : 반려동물 별 메모 수정하기
     */
    @Operation(summary = "반려동물의 메모 수정. 삭제는 따로 없음"
            , description = "사용 키: memo_num, memo /메모를 수정하기 위한 UPDATE 기능/")
    @PutMapping()
    public ResponseEntity<Memo> updateMemo(@RequestBody @Valid MemoDTO memoDTO){
        memoDTO.setDate(LocalDateTime.now());
        ModelMapper modelMapper = new ModelMapper();
        Memo memo = modelMapper.map(memoDTO, Memo.class);
        Memo updateMemo = memoService.updateMemo(memo);
        return ResponseEntity.status(HttpStatus.OK).body(updateMemo);
    }
}
