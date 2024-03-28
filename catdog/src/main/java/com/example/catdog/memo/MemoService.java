package com.example.catdog.memo;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.MemoException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    // 반려동물 별 메모 조회하기
    public List<Object> todayMemoList(String member_id, Integer pet_num, Date date){
        List<Object> memo = memoRepository.dateMemo(member_id, pet_num, date);
        if(memo.isEmpty()){
            throw new MemoException(ErrorCode.NOT_FOUND);
        }
        return memo;
    }
    
    // 반려동물 별 메모 수정하기
    public Memo updateMemo(Memo memo){
        Optional<Memo> optionalMemo = memoRepository.findById(memo.getMemo_num());

        if(optionalMemo.isPresent()){
            Memo checkmemo = optionalMemo.get();

            if ( checkmemo.getDate().toLocalDate().isEqual(LocalDate.now())
                    && checkmemo.getResign_yn() == Resign_yn.N
                ) {
                checkmemo.setMemo(memo.getMemo());
                checkmemo.setDate(memo.getDate());

                Memo updatedMemo = memoRepository.save(checkmemo);
                return updatedMemo;
            }
            else {
                throw new MemoException(ErrorCode.NOT_FOUND);
            }
        }
        return null;
    }
}
