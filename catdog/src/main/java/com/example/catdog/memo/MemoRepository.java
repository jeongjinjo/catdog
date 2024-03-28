package com.example.catdog.memo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MemoRepository extends JpaRepository<Memo, Integer> {

    // 로그인 한 사람의 그룹별 반려동물의 메모 .로그인한 사람의 값, 그룹별 반려동물의 고유번호, 원하는 날짜
    @Query(value = "SELECT ct.group_num, m.pet_num, m.memo, m.date " +
            "FROM Memo m " +
            "JOIN CareTarget ct ON ct.pet_num=m.pet_num " +
            "JOIN CareGroup cg ON cg.group_num=ct.group_num " +
            "WHERE cg.group_num IN ( " +
            "   SELECT careGroup.group_num " +
            "   FROM CareGroupMember " +
            "   WHERE member.member_id = :member_id " +
            "   AND resign_yn='N' " +
            ") " +
            "AND m.pet_num = :pet_num " +
            "AND cg.resign_yn='N' " +
            "AND m.resign_yn='N' " +
            "AND DATE(m.date) = :date "
    )
    List<Object> dateMemo(@Param("member_id") String member_id, @Param("pet_num") Integer memo_num, @Param("date") Date date);
}
