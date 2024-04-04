package com.example.catdog.pet.photo;

import com.example.catdog.enum_column.Resign_yn;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import java.util.UUID;

@Service


public class PhotoService {

    private PhotoRepository photoRepository;

    private Path photoPath;

    public PhotoService(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;

        photoPath = Paths.get("src/main/resources/static/petphoto/upload/")
                .toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.photoPath);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void upload(Photo photo, MultipartFile photoFile) {

        String uuid=UUID.randomUUID().toString();
        try {
            File dest = new File(photoPath +
                    "/" +uuid+ photoFile.getOriginalFilename());
            photoFile.transferTo(dest);
        } catch (Exception e) {
            e.printStackTrace();
        }

//경로받는 곳
        Link link = WebMvcLinkBuilder
                .linkTo(
                        WebMvcLinkBuilder
                                .methodOn(PhotoController.class)
                                .getImage(photoFile.getOriginalFilename())
                )
                .withRel("download");
        photo.setRoute(link.getHref().toString());
        photo.setResign_yn(Resign_yn.N);
        photoRepository.save(photo);
        System.out.println(photo);

    }

    // N으로 바꾸는 거임 delete 구문 아님
    public void deletePhoto(int photo_num) {
        Optional<Photo> photo = photoRepository.findById(photo_num);
        if(photo.isEmpty()){
            System.out.println("삭제할 사진이 존재하지 않습니다.");
        }
        photo.get().setResign_yn(Resign_yn.Y);
        photoRepository.save(photo.get());
    }
}
