package com.example.catdog.pet;

import com.example.catdog.enum_column.Gender;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Type;
import com.example.catdog.pet.photo.Photo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity // 이 클래스가 JPA 앤티티임을 나타낸다. JPA가 이 클래스와 데이터베이스 테이블 간의 매핑을 수행한다.
@Getter
@Setter
@ToString
@NoArgsConstructor //Lombok 어노테이션 , 매개변수가 없는 생성자와 모든 필드를 매개변수로 받는 생성자를 자동으로 생성함.
@AllArgsConstructor
@Builder
@Schema(description = "반려동물에 대한 정보입니다.")
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(title = "반려동물 고유번호", description = "자동으로 1씩 증가합니다.")
    private int pet_num;

    @Schema(title = "반려동물 종류", description = "개와 고양이, 기타로 지정해두었습니다.")
    @Enumerated(EnumType.STRING)
    private Type type;

    @Schema(title = "반려동물 종류 상세", description = "종류를 기타로 해두었을 시, 반려동물에 대한 상세 종류를 작성합니다.")
    private String type_detail;

    @Schema(title = "반려동물 이름")
    @Column(nullable = false)
    private String pet_name; //(사람 id 랑 외래키 연결)

    @Schema(title = "반려동물 나이")
    private int age;

    @Schema(title = "반려동물 몸무게", description = "실수 적용 가능합니다.")
    private float kg;

    @Schema(title = "반려동물 질병", description = "(,)을 기준으로 여러개의 질병을 넣을 수 있습니다.")
    private String disease;

    @Schema(title = "삭제여부")
    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;

    @Schema(title = "반려동물 성별", description = "암컷, 수컷, 중성으로 이루어져있습니다.")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Schema(title = "반려동물 등록자", description = "반려동물을 등록한 회원 아이디입니다.")
    private String member_id;

    @OneToOne
    @JoinColumn(name="photo_num", unique = true, insertable = false, updatable = false)
    @Schema(title = "반려동물 사진", description = "반려동물 등록 시, 같이 등록될 프로필입니다.")
    private Photo photo;

    // (gayoung) 메인페이지의 사진 루트를 보여주기 위해서.
    @Transient // 데이터베이스와 매핑되지 않음
    private String photoRoute;
}