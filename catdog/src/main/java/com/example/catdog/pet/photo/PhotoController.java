package com.example.catdog.pet.photo;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/photo")
@Tag(name = "PhotoController", description = "Photo의 정보 조회,등록,삭제가 가능합니다.")
public class PhotoController {

    private PhotoService photoService;

    private Path photoPath;

    //생성자
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
        photoPath = Paths.get("src/main/resources/static/petphoto/upload/")
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.photoPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Operation(summary = "반려동물 사진 등록",
            description = "유효성 검사 후 반려동물 사진 업로드")
    @PostMapping(value = "",consumes = MULTIPART_FORM_DATA_VALUE)
    public String PhotoUpload(@Valid @RequestPart(value = "photoDto") PhotoDto photoDto,
                              @RequestPart(value = "file") MultipartFile photoFile) {
        ModelMapper mapper = new ModelMapper();
        Photo photo = mapper.map(photoDto, Photo.class);
        photoService.upload(photo, photoFile);
        return "업로드가 완료되었습니다.";
    }

    @Operation(summary = "반려동물 사진 조회",
            description = "PhotoName을 통해 반려동물 사진 조회")
    @GetMapping("/{PhotoName}")
    public ResponseEntity<Resource> getImage(@PathVariable String PhotoName) {
        Path photoPath = this.photoPath.resolve(PhotoName).normalize();
        Resource resource = null;
        try {
            resource = new UrlResource((photoPath.toUri()));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(resource);
    }

    @Operation(summary = "반려동물 사진 삭제",
            description = "photoNum을 통해 반려동물 사진 삭제")
    @DeleteMapping("/{photoNum}")
    public ResponseEntity<String> deletedbyPet(@PathVariable int photoNum){
        photoService.deletePhoto(photoNum);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("삭제됨");
    }
}