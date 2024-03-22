package com.example.catdog.care_group;

import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Role;
import com.example.catdog.exception.ErrorCode;
import com.example.catdog.exception.MemberExcption;
import com.example.catdog.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GroupRepository extends JpaRepository<Care_group, Integer> {


    // 그룹 계층 번호를 기준으로 내가 속해있는 그룹 조회
    @Query(value = "SELECT g  " +
                    " FROM Care_group g " +
                    " JOIN Member m" +
                    "   ON g.member.member_id = m.member_id  " +
                    " WHERE g.group_key IN ( " +
                        "      SELECT gr.group_key " +
                        "        FROM Care_group gr " +
                        "       WHERE gr.member.member_id = :member_id " +
                        "         AND gr.resign_yn = 'N'" +
                    ")")
    Optional<List<Care_group>> findByClassNumGrouped(@Param("member_id") String memberId);

    // care_insert > HOST
    default int insertCareGroup(String group_name
//                                , String member_id
                                , Member member
                                , Role role
                                , Resign_yn resign_yn
                                , int group_key) {
        int result = 0;
        Care_group careGroup = new Care_group(group_name, member, role, resign_yn, group_key);

        Care_group cg = save(careGroup);

        if(cg != null) {
            result = 1;
        } else {
            throw new MemberExcption(ErrorCode.NOT_FOUND);
        }

        return result;
    }



}
