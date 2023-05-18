package samdasu.recipt.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.repository.ImageFileRepository;
import samdasu.recipt.utils.Image.ImageUtils;
import samdasu.recipt.domain.entity.ImageFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageFileService {

    private final ImageFileRepository imageFileRepository;

    public String uploadImage(MultipartFile file) throws IOException {
        log.info("upload file: {}", file);
        ImageFile imageFile = imageFileRepository.save(
                ImageFile.createImageFile(file.getOriginalFilename(), file.getContentType(), ImageUtils.compressImage(file.getBytes())));

        if (imageFile != null) {
            log.info("imageFile: {}", imageFile);
            return "file uploaded successfully : " + file.getOriginalFilename();
        }

        return null;
    }

    // 이미지 파일로 압축하기
    public byte[] downloadImage(String fileName) {
        ImageFile imageFile = imageFileRepository.findByFilename(fileName)
                .orElseThrow(RuntimeException::new);

        log.info("download imageFile: {}", imageFile);

        return ImageUtils.decompressImage(imageFile.getImageData());
    }
}
