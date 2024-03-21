package com.example.catdog.pet;

import com.example.catdog.enum_column.Gender;
import com.example.catdog.enum_column.Type;
import jakarta.persistence.*;
import lombok.*;

@Entity // 이 클래스가 JPA 앤티티임을 나타낸다. JPA가 이 클래스와 데이터베이스 테이블 간의 매핑을 수행한다.
@Getter
@Setter
@ToString
@NoArgsConstructor //Lombok 어노테이션 , 매개변수가 없는 생성자와 모든 필드를 매개변수로 받는 생성자를 자동으로 생성함.
@AllArgsConstructor
@Builder
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pet_num;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String type_detail;

    @Column(nullable = false)
    private String pet_name; //(사람 id 랑 외래키 연결)

    private int age;

    private float kg;

    private String disease;
    //없다면 체크를 눌러서 disease없음에 해당되는건 어떨까//
    //질병 ex( 심장병,방광염 , , , ) 쉼표로 구분되게 해야함.

    private String resign_yn;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String member_id;

    //이렇게하면... pet_num과 id 연결 되는 것
    /*@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="petandmember_id")
    private Member member; //Member테이블과의 관계 설정*/

    //Member member
    //불러서
    //외래키 지정

    /*private int pet_age;

    private float pet_kg;*/

}