package samdasu.recipt.domain.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.service.ImageFileService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/image")
public class ImageFileApiController {

    private final ImageFileService imageFileService;

    // 업로드
    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam(value = "image") MultipartFile file) throws IOException {
        String uploadImage = imageFileService.uploadImage(file);
        return ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
    }

    // 다운로드 -> 파일 확장자로 콘텐츠 유형 동적으로 결정
    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName) {
        byte[] downloadImage = imageFileService.downloadImage(fileName);
        String fileExtension = getFileExtension(fileName);

        MediaType contentType;
        if (fileExtension.equalsIgnoreCase("png")) {
            contentType = MediaType.IMAGE_PNG;
        } else if (fileExtension.equalsIgnoreCase("jpg") || fileExtension.equalsIgnoreCase("jpeg")) {
            contentType = MediaType.IMAGE_JPEG;
        } else {
            contentType = MediaType.APPLICATION_OCTET_STREAM;
        }

        return ResponseEntity.status(HttpStatus.OK)
                .contentType(contentType)
                .body(downloadImage);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return "";
    }

//    // 다운로드
//    @GetMapping("/{fileName}")
//    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName) {
//        byte[] downloadImage = imageFileService.downloadImage(fileName);
//        return ResponseEntity.status(HttpStatus.OK)
//                .contentType(MediaType.valueOf("image/png"))
//                .body(downloadImage);
//    }
}

