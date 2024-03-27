package com.example.catdog.memo;

import com.example.catdog.enum_column.Resign_yn;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("memo")
public class MemoController {

    private final MemoService memoService;
    private final MemoRepository memoRepository;

    /**
     * @param memo : 메모
     * @return : 반려동물 별 메모 등록하기
     */
    @PostMapping()
    public Memo getMemo(@RequestBody Memo memo){
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
     * @param id : 로그인 한 아이디
     * @param petNum : 반려동물 고유번호
     * @param date : 날짜 확인
     * @return : 반려동물 별 메모 조회하기
     */
    @GetMapping()
    public ResponseEntity<List<Object>> todayMemo(@RequestParam String id, Integer petNum, Date date){
    //public ResponseEntity<List<Object>> todayMemo(@RequestBody @Valid MemoDTO memoDTO){

      //  memoDTO.setDate(LocalDateTime.now());

        //ModelMapper modelMapper = new ModelMapper();

        List<Object> memo = memoService.todayMemoList(id, petNum, date);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(memo);
    }

    /**
     * @return : 반려동물 별 메모 수정하기
     */
    @PutMapping()
    public ResponseEntity<Memo> update(@RequestBody @Valid MemoDTO memoDTO){
        memoDTO.setDate(LocalDateTime.now());

        ModelMapper modelMapper = new ModelMapper();
        Memo memo = modelMapper.map(memoDTO, Memo.class);

        Memo updateMemo = memoService.updateMemo(memo);

        return ResponseEntity.status(HttpStatus.OK).body(updateMemo);
    }
}
