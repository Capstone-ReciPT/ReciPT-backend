package samdasu.recipt.utils.Image;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.domain.controller.dto.Register.RegisterResponseDto;
import samdasu.recipt.domain.controller.dto.User.UserResponseDto;
import samdasu.recipt.domain.entity.RegisterRecipe;
import samdasu.recipt.domain.entity.User;
import samdasu.recipt.domain.exception.ProfileNotFoundException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 폴더 생성 부분: 유저마다 폴더 1개씩 만들게끔 수정하기
 * 반복되는 코드 리팩토링 필요
 */
@Service
@Slf4j
public class UploadService {

    @Value("${image.user.path}")
    private String userImage;

    @Value("${image.register.path}")
    private String registerImage;

    public AttachImage uploadOne(String path, MultipartFile uploadFile, String folderName){
        if (imageCheck(uploadFile)) return null;

        /* 폴더 생성 */
        File uploadPath = new File(path, folderName);

        if(!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        /* 이미저 정보 담는 객체 */
        AttachImage attachImage = new AttachImage();
        attachImage.setUploadPath(folderName);

        /* 파일 이름 */
        String uploadFileName = uploadFile.getOriginalFilename();
        attachImage.setFileName(uploadFileName);
        /* uuid 적용 파일 이름 */
        String uuid = UUID.randomUUID().toString();
        uploadFileName = uuid + "_" + uploadFileName;
        attachImage.setSavedName("s_" + uploadFileName);

        /* 파일 위치, 파일 이름을 합친 File 객체 */
        File saveFile = new File(uploadPath, uploadFileName);

        /* 파일 저장 */
        try {
            uploadFile.transferTo(saveFile);

            /* 방법 2 */ // 라이브러리 사용
            File thumbnailFile = new File(uploadPath, attachImage.getSavedName());
            BufferedImage bo_image = ImageIO.read(saveFile);

            //비율
            double ratio = 3;
            //넓이 높이
            int width = (int) (bo_image.getWidth() / ratio);
            int height = (int) (bo_image.getHeight() / ratio);

            Thumbnails.of(saveFile)
                    .size(width, height)
                    .toFile(thumbnailFile);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (saveFile.exists()) {
                saveFile.delete();
            }
        }
        return attachImage;
    }

    private static boolean imageCheck(final MultipartFile uploadFile) {
        /* 이미지 파일 체크 */
        File checkfile = new File(uploadFile.getOriginalFilename());
        String type = null;

        try {
            type = Files.probeContentType(checkfile.toPath());
            log.info("MIME TYPE : " + type);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(!type.startsWith("image")) {
            return true;
        }
        return false;
    }

    public List<AttachImage> uploadGtOne(String path, MultipartFile[] uploadFile, String folderName){
        if (imageCheck(uploadFile)) return null;

        /* 폴더 생성 */
        File uploadPath = new File(path, folderName);

        if(!uploadPath.exists()) {
            uploadPath.mkdirs();
        }

        /* 이미저 정보 담는 객체 */
        List<AttachImage> res = new ArrayList<>();

        for (MultipartFile multipartFile : uploadFile) {
            AttachImage attachImage = new AttachImage();
            attachImage.setUploadPath(folderName);

            /* 파일 이름 */
            String uploadFileName = multipartFile.getOriginalFilename();
            attachImage.setFileName(uploadFileName);
            /* uuid 적용 파일 이름 */
            String uuid = UUID.randomUUID().toString();
            uploadFileName = uuid + "_" + uploadFileName;
            attachImage.setSavedName("s_" + uploadFileName);

            /* 파일 위치, 파일 이름을 합친 File 객체 */
            File saveFile = new File(uploadPath, uploadFileName);


            /* 파일 저장 */
            try {
                multipartFile.transferTo(saveFile);

                /* 방법 2 */ // 라이브러리 사용
                File thumbnailFile = new File(uploadPath, attachImage.getSavedName());
                BufferedImage bo_image = ImageIO.read(saveFile);

                //비율
                double ratio = 3;
                //넓이 높이
                int width = (int) (bo_image.getWidth() / ratio);
                int height = (int) (bo_image.getHeight() / ratio);

                Thumbnails.of(saveFile)
                        .size(width, height)
                        .toFile(thumbnailFile);

            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if (saveFile.exists()) {
                    saveFile.delete();
                }
            }
            res.add(attachImage);
        }
        return res;
    }

    private static boolean imageCheck(final MultipartFile[] uploadFile) {
        /* 이미지 파일 체크 */
        for(MultipartFile multipartFile: uploadFile) {

            File checkfile = new File(multipartFile.getOriginalFilename());
            String type = null;

            try {
                type = Files.probeContentType(checkfile.toPath());
                log.info("MIME TYPE : " + type);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(!type.startsWith("image")) {
                return true;
            }

        }// for
        return false;
    }

    public ResponseEntity<byte[]> getUserProfile(String folderName,String fileName) {
        File file = new File(userImage + "/" + folderName + "/" + fileName);

        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders header = new HttpHeaders();

            header.add("Content-type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        }catch (IOException e) {
            throw new ProfileNotFoundException("Fail: Profile Not Found!", e);
        }
        return result;
    }

    public ResponseEntity<byte[]> getRegisterProfile(String folderName, String fileName) {
        File file = new File(registerImage + "/" + folderName + "/" + fileName);

        ResponseEntity<byte[]> result = null;
        try {
            HttpHeaders header = new HttpHeaders();

            header.add("Content-type", Files.probeContentType(file.toPath()));
            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

        }catch (IOException e) {
            throw new ProfileNotFoundException("Fail: Profile Not Found!", e);
        }
        return result;
    }
}
