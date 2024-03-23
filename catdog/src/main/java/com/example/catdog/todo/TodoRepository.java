package com.example.catdog.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Integer> {
    // 로그인 한 사람이 속한 그룹별 반려동물 의 할 일
    @Query(value = "SELECT ct.group_num, d.pet_num, d.todo, d.start_date, d.start_id " +
            "FROM Todo d " +
            "JOIN Care_target ct ON ct.pet_num=d.pet_num " +
            "WHERE ct.group_num IN ( " +
            "   SELECT cg.group_key " +
            "   FROM Care_group cg " +
            "   JOIN Member m ON m.member_id=cg.member.member_id " +
            "   WHERE m.member_id= :id " +
            "   AND cg.resign_yn='N' " +
            "   AND m.resign_yn='N' " +
            ") "
    )
    public Optional<List<Object[]>> findByGroupPetTodo(@Param("id") String id);
}
