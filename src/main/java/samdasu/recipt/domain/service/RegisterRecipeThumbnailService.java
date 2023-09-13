package samdasu.recipt.domain.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.repository.RegisterRecipeThumbnailRepository;
import samdasu.recipt.utils.Image.ImageUtils;
import samdasu.recipt.domain.entity.RegisterRecipeThumbnail;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegisterRecipeThumbnailService {

    private final RegisterRecipeThumbnailRepository thumbnailRepository;

    public Long uploadImage(MultipartFile file) throws IOException {
        log.info("upload file: {}", file);
        RegisterRecipeThumbnail saveThumbnail = thumbnailRepository.save(
                RegisterRecipeThumbnail.createThumbnail(file.getOriginalFilename(), file.getContentType(), ImageUtils.compressImage(file.getBytes())));

        if (saveThumbnail != null) {
            log.info("imageFile: {}", saveThumbnail);
            log.info("file uploaded successfully : {}", file.getOriginalFilename());
            return saveThumbnail.getThumbnailId();
        }

        return null;
    }

    // 이미지 파일로 압축하기
    public byte[] downloadImage(Long profileId) {
        RegisterRecipeThumbnail thumbnail = thumbnailRepository.findById(profileId)
                .orElseThrow(RuntimeException::new);

        log.info("download imageFile: {}", thumbnail);

        return ImageUtils.decompressImage(thumbnail.getThumbnailData());
    }
}
