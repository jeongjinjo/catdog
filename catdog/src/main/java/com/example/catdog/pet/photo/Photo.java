package com.example.catdog.pet.photo;

import com.example.catdog.enum_column.Resign_yn;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "photo")
@NoArgsConstructor
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private int photo_num;

    private String route;

    @Enumerated(EnumType.STRING)
    private Resign_yn resign_yn;
}
