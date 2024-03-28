package com.example.catdog.pet.photo;

import com.example.catdog.enum_column.Resign_yn;
import jakarta.persistence.*;
import lombok.*;

@Entity // 이 클래스가 JPA 앤티티임을 나타낸다. JPA가 이 클래스와 데이터베이스 테이블 간의 매핑을 수행한다.
@Getter
@Setter
@ToString
@NoArgsConstructor //Lombok 어노테이션 , 매개변수가 없는 생성자와 모든 필드를 매개변수로 받는 생성자를 자동으로 생성함.
@AllArgsConstructor
@Builder
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int photo_num;

    private String route;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;
}
