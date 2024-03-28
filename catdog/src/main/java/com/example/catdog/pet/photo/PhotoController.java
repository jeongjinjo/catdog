package com.example.catdog.pet.photo;

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

    @PostMapping(value = "/upload",consumes = MULTIPART_FORM_DATA_VALUE)
    public String PhotoUpload(@Valid @RequestPart(value = "photoDto") PhotoDto photoDto,
                              @RequestPart(value = "file") MultipartFile photoFile) {
        ModelMapper mapper = new ModelMapper();
        Photo photo = mapper.map(photoDto, Photo.class);
        photoService.upload(photo, photoFile);
        return "업로드가 완료되었습니다.";
    }

    @GetMapping("/select/{PhotoName}")
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

    @DeleteMapping("delete/{photo_num}")
    public ResponseEntity<String> deletedbyPet(@PathVariable int photo_num){
        photoService.deletePhoto(photo_num);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("삭제됨");
    }
}