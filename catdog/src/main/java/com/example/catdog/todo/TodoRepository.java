package com.example.catdog.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {

    // 로그인 한 사람이 속한 그룹별, 날짜별 반려동물 의 할 일
    @Query(value = "SELECT ct.group_num " +
            ", d.todo_num " +
            ", d.pet_num " +
            ", d.todo " +
            ", d.start_date " +
            ", d.start_id " +
            "FROM Todo d " +
            "JOIN CareTarget ct ON ct.pet_num=d.pet_num " +
            "JOIN CareGroup cg ON cg.group_num=ct.group_num " +
            "WHERE cg.group_num IN ( " +
            "   SELECT careGroup.group_num" +
            "   FROM CareGroupMember " +
            "   WHERE member.member_id = :loginId " +
            "   AND resign_yn='N' " +
            ") " +
            "AND d.pet_num= :petNum " +
            "AND cg.resign_yn='N' " +
            "AND d.resign_yn='N' " +
            "AND DATE(d.start_date) = :date " +
            "ORDER BY d.todo_num desc "
    )
    public Optional<List<Object[]>> findByGroupPetTodo(
            @Param("loginId") String id, @Param("petNum") Integer pet_num, @Param("date") Date date);

    // 날짜별로 할일이 있는지 없는지 확인해서 달력에 할일의 유무만 확인하기
    @Query(value = "SELECT COUNT(*) " +
            "FROM Todo t " +
            "WHERE DATE(t.start_date) = :date "
    )
    int check(@Param("date") Date date);
}
