package samdasu.recipt.service;

//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//import samdasu.recipt.entity.ImageFile;
//import samdasu.recipt.exception.ResourceNotFoundException;
//import samdasu.recipt.repository.ImageFileRepository;
//import samdasu.recipt.repository.Review.ReviewRepository;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.UUID;
//
//@Service
//@Transactional(readOnly = true)
//@RequiredArgsConstructor
//public class ImageFileService {
//    /**
//     * 고민: review 저장시 image파일도 같이 @transactional 걸어야할거 같은데... reviewservice에서 저장시키는게 맞을까?
//     * 아님 controller에서 직접 저장시키고 review 따로 저장?
//     */
//
//    @Value("${file.dir}")
//    private String fileDir;
//    private final ReviewRepository reviewRepository;
//    private final ImageFileRepository imageFileRepository;
//
//    public String getFullPath(String filename) {
//        return filename + filename;
//    }
//
//    /**
//     * 여러개 업로드
//     */
//    @Transactional
//    public List<ImageFile> saveFiles(List<MultipartFile> multipartFiles) throws IOException {
//        List<ImageFile> storeFileResult = new ArrayList<>();
//        for (MultipartFile multipartFile : multipartFiles) {
//            if (!multipartFile.isEmpty()) {
//                storeFileResult.add(storeFile(multipartFile));
//            }
//        }
//        return storeFileResult;
//    }
//
//    public ImageFile storeFile(MultipartFile multipartFile) throws IOException {
//        if (multipartFile.isEmpty()) {
//            return null;
//        }
//
//        String originalFilename = multipartFile.getOriginalFilename();
//        String storeFileName = createStoreFileName(originalFilename); //업로드 파일 명
//        multipartFile.transferTo(new File(getFullPath(storeFileName))); //파일 저장
//        return ImageFile.createImageFile(originalFilename, storeFileName);
//    }
//
//    private String createStoreFileName(String originalFilename) {
//        String uuid = UUID.randomUUID().toString();
//        String ext = extractExt(originalFilename);
//        return uuid + "." + ext;
//    }
//
//    /**
//     * .txt / .png 인지 뒤에 확장자 뽑기
//     */
//    private String extractExt(String originalFilename) {
//        int pos = originalFilename.lastIndexOf(".");
//        return originalFilename.substring(pos + 1);
//    }
//
//    public List<ImageFile> findImageAll() {
//        return imageFileRepository.findAll();
//    }
//
//
//    public ImageFile findOne(Long imageId) {
//        ImageFile imageFile = imageFileRepository.findById(imageId)
//                .orElseThrow(() -> new ResourceNotFoundException("Fail: No ImageFile Info"));
//        return imageFile;
//    }
//}

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.entity.ImageFile;
import samdasu.recipt.exception.ResourceNotFoundException;
import samdasu.recipt.repository.ImageFileRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ImageFileService {
    private final ImageFileRepository imageFileRepository;

    /**
     * 1번에 1개 이미지 저장
     */
    @Transactional
    public void saveImage(ImageFile files) {
        ImageFile imageFile = ImageFile.createImageFile(files.getStoreFilename(), files.getOriginalFilename(), files.getFileUrl());

        imageFileRepository.save(imageFile);
    }

    /**
     * 다중 이미지 저장
     */
    @Transactional
    public void saveImages(Long imageId) {
        ImageFile imageFile = imageFileRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No ImageFile Info"));
        imageFileRepository.save(imageFile);
    }

    public ImageFile findByImageId(Long imageId) {
        return imageFileRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No ImageFile Info"));
    }

    public ImageFile findByOriginName(String originalFilename) {
        return imageFileRepository.findByOriginalFilename(originalFilename)
                .orElseThrow(() -> new ResourceNotFoundException("Fail: No ImageFile Info"));
    }

}


