package com.example.catdog.careGroup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<CareGroup, Integer> {

    /** 등록한 그룹 찾기 ( eunae )
     *
     * @param memberId 권한이 HOST인 member_id(로그인 한 유저)
     * @param groupNum 등록된 그룹의 그룹 번호
     * @return
     */
//    @Query(value = "")
//    public Optional<CareGroup> findGroupHostInfo(@Param("memberId") String memberId, @Param("groupNum") int groupNum);

}
