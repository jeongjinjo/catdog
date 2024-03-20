package com.example.catdog.pet;

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
    private Long pet_num;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private String pet_name; //(사람 id 랑 외래키 연결)

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name="petandmember_id")
//    private Member member; //Member테이블과의 관계 설정

    //Member member
    //불러서
    //외래키 지정

    @Column(nullable = false)
    private int pet_age;

    @Column(nullable = false)
    private float pet_kg;

    private String disease;
    //없다면 체크를 눌러서 disease없음에 해당되는건 어떨까//
    //질병 ex( 심장병,방광염 , , , ) 쉼표로 구분되게 해야함.

    private String resign_yn;


    @Enumerated(EnumType.STRING)
    private Gender gender;

}