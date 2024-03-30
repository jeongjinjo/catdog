package com.example.catdog.pet;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {
    //save,findById,findAll,deletedById 등의 메소드들이 자동으로 제공된다.

    // 그룹에 등록되지 않은 펫 확인하기 ( eunae )
    @Query(value = "SELECT p " +
            " FROM Pet p " +
            "WHERE NOT EXISTS (" +
            "    SELECT 1 " +
            "      FROM CareTarget c " +
            "     WHERE c.pet_num = p.pet_num " +
            ") " +
            "  AND p.member_id = :id"
    )
    public List<Pet> findByGroupNotInPet(@Param("id") String id);

    // 로그인 한 사람의 그룹별 반려동물 정보 ( gayoung )
    @Query(value = "SELECT DISTINCT cg.group_num " +
            ", cg.group_name " +
            ", p.pet_num " +
            ", p.pet_name " +
            ", p.gender " +
            ", p.age" +
            ", p.disease " +
            ", ph.route " +
            "FROM Pet p " +
            "LEFT JOIN Photo ph ON ph.photo_num = p.photo.photo_num " +
            "JOIN CareTarget ct ON ct.pet_num = p.pet_num " +
            "JOIN CareGroup cg ON cg.group_num = ct.group_num " +
            "WHERE cg.group_num IN ( " +
            "   SELECT cgm.careGroup.group_num " +
            "   FROM CareGroupMember cgm " +
            "   WHERE cgm.member.member_id = :loginId " +
            "   AND cgm.resign_yn = 'N' " +
            ") " +
            "AND p.resign_yn = 'N' " +
            "AND (ph.resign_yn = 'N' OR ph.resign_yn IS NULL) "
    )
    public Optional<List<Object[]>> findByGroupInfoPet(@Param("loginId") String id);

//     NOTE 로그인 한 사람의 그룹별 반려동물 정보 ( eunae ) CHECK 03.25 수정완료
    @Query(value = "SELECT p " +
                    " FROM Pet p " +
                    "  JOIN CareTarget ct " +
                    "    ON ct.pet_num = p.pet_num " +
                    "  LEFT JOIN Photo photo " +
                    "    ON p.photo.photo_num = photo.photo_num " +
                    " WHERE ct.group_num IN (" +
                    "   SELECT cg.group_num " +
                    "     FROM CareGroup cg " +
                    "     JOIN CareTarget ct2 " +
                    "       ON cg.group_num = ct2.group_num " +
                    "     JOIN CareGroupMember cgm " +
                    "       ON cgm.careGroup.group_num = cg.group_num " +
                    "    WHERE cgm.member.member_id = :member_id " +
                    "      AND cgm.resign_yn = 'N' " +
                    ")  " +
                    "AND p.resign_yn = 'N' " +
                    "AND (photo.resign_yn = 'N' OR photo.resign_yn IS NULL)"
    )
    public List<Pet> getGroupInfoPet(@Param("member_id") String member_id);


    //member_id를 통해 pet 조회할 때//
    //리스트로 받아야됨 , p로 받아도되고 다른거 그냥 다 적어도 됨.
    //pet_name 이거만 성공시ㅣ면 됨.


    @Query(value="SELECT p FROM Pet p WHERE p.member_id = :member_id")
    List<Pet> findByMemberId(@Param("member_id") String memberId);

//    Pet countById(Pet);

    @Query(value = "SELECT COUNT(*) FROM Pet p WHERE p.member_id = :member_id")
    int countByMemberIdIsMyPet(@Param("member_id") String memberId);

}
