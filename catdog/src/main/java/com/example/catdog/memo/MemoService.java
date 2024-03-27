package com.example.catdog.memo;

import com.example.catdog.enum_column.Resign_yn;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemoService {

    private final MemoRepository memoRepository;

    // 반려동물 별 메모 조회하기
    public List<Object> todayMemoList(String id, Integer petNum, Date date){
        List<Object> memo = memoRepository.dateMemo(id, petNum, date);

        return memo;
    }
    
    // 반려동물 별 메모 수정하기
    public Memo updateMemo(Memo memo){
        Optional<Memo> optionalMemo = memoRepository.findById(memo.getMemo_num());

        if(optionalMemo.isPresent()){
            Memo checkmemo = optionalMemo.get();

            if ( checkmemo.getDate().toLocalDate().isEqual(LocalDate.now())
                    && checkmemo.getResign_yn() == Resign_yn.N
                )
            {
                checkmemo.setMemo(memo.getMemo());
                checkmemo.setDate(memo.getDate());

                Memo updatedMemo = memoRepository.save(checkmemo);
                return updatedMemo;
            }
        }
        return null;
    }
}
