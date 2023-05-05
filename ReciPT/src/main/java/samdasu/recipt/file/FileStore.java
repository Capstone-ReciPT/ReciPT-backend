package samdasu.recipt.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.entity.ImageFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * 실제 파일 업로드, 다운로드 구현
 */
@Component
public class FileStore {
    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    /**
     * 여러개 업로드
     */
    public List<ImageFile> storeFiles(List<MultipartFile> multipartFiles) throws IOException {
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
        multipartFile.transferTo(new File(getFullPath(storeFileName))); //파일 저장
        return ImageFile.UploadFile(originalFilename, storeFileName);
    }

    private String createStoreFileName(String originalFilename) {
        //image.png 저장시 uuid써서 서버에 저장하는 파일명 생성
        //"qwe-qwe-123-qwe-qwe.png"
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
