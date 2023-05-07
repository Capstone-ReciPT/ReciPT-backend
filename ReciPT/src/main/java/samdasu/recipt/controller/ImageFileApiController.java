package samdasu.recipt.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.service.ImageFileService;

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

    // 다운로드
    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName) {
        byte[] downloadImage = imageFileService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(downloadImage);
    }
}

