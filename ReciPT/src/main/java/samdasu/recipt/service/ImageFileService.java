package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.controller.dto.ImageFile.ImageFileDto;
import samdasu.recipt.controller.dto.ImageFile.ImageFileRequestDto;
import samdasu.recipt.entity.ImageFile;
import samdasu.recipt.repository.Review.ReviewRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ImageFileService {
    /**
     * 고민: review 저장시 image파일도 같이 @transactional 걸어야할거 같은데... reviewservice에서 저장시키는게 맞을까?
     * 아님 controller에서 직접 저장시키고 review 따로 저장?
     */
    private final ReviewRepository reviewRepository;
    private final ImageFileRequestDto imageFileRequestDto;

    public String getFullPath(String filename) {
        return imageFileRequestDto.getFileDir() + filename;
    }

    /**
     * 여러개 업로드
     */
    public List<ImageFile> saveFiles(ImageFileDto imageFileDto, List<MultipartFile> multipartFiles) throws IOException {
        List<ImageFile> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(storeFile(multipartFile));
            }
        }
        return storeFileResult;
    }

    public ImageFile storeFile(MultipartFile multipartFile) throws IOException {
        if (multipartFile.isEmpty()) {
            return null;
        }

        String originalFilename = multipartFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename); //업로드 파일 명
        multipartFile.transferTo(new File(getFullPath(imageFileRequestDto.getFileDir()), storeFileName)); //파일 저장
        return ImageFile.createImageFile(imageFileRequestDto.getFileDir(), originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    /**
     * .txt / .png 인지 뒤에 확장자 뽑기
     */
    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
