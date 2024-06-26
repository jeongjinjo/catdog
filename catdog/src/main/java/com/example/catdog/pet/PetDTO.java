package com.example.catdog.pet;

import com.example.catdog.enum_column.Gender;
import com.example.catdog.enum_column.Resign_yn;
import com.example.catdog.enum_column.Type;
import com.example.catdog.member.Member;
import com.example.catdog.pet.photo.Photo;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "반려동물 유효성 검사를 위한 객체입니다.")
public class PetDTO {

    @Schema(description = "반려동물 고유 번호")
    private int pet_num;

    @NotNull(message="CAT,DOG,ETC둘 중 하나를 선택해주세요.")
    @Schema(description = "반려동물 종류")
    private Type type;

    @Size(max = 50, message = "종류 상세는 50자까지 입력이 가능합니다.")
    @Schema(description = "반려동물 종류 상세", example = "고슴도치, 뱀, 햄스터 등")
    private String type_detail;

    @NotNull(message = "반려동물 이름을 작성해주세요.")
    @Size(max = 50, message = "이름은 50자까지 입력이 가능합니다.")
    @Schema(description = "반려동물 이름")
    private String pet_name;

    @NotNull(message="반려동물의 나이를 입력해주세요.")
    @Schema(description = "반려동물 나이")
    private int age;

    @NotNull(message="반려동물의 몸무게를 입력해주세요.")
    @Schema(description = "반려동물 몸무게")
    private float kg;

    @NotNull(message="반려동물의 질병을 입력해주세요.")
    @Size(max = 100, message = "질병은 100자까지 입력이 가능합니다.")
    @Schema(description = "반려동물 질병")
    private String disease;

    @Schema(description = "삭제여부")
    private Resign_yn resign_yn;

    @NotNull(message="반려동물의 성별을 입력해주세요.")
    @Schema(description = "반려동물 성별")
    private Gender gender;

    @Schema(description = "반려동물 프로필 사진")
    private Photo photo_num;

    @Schema(description = "반려동물 소유자")
    private String member_id;
}
