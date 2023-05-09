package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import samdasu.recipt.Image.ImageUtils;
import samdasu.recipt.entity.Profile;
import samdasu.recipt.repository.ProfileRepository;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;

    public Long uploadImage(MultipartFile file) throws IOException {
        log.info("upload file: {}", file);
        Profile saveProfile = profileRepository.save(
                Profile.createProfile(file.getOriginalFilename(), file.getContentType(), ImageUtils.compressImage(file.getBytes())));

        if (saveProfile != null) {
            log.info("imageFile: {}", saveProfile);
            log.info("file uploaded successfully : {}", file.getOriginalFilename());
            return saveProfile.getProfileId();
        }

        return null;
    }

    // 이미지 파일로 압축하기
    public byte[] downloadImage(Long profileId) {
        Profile profile = profileRepository.findById(profileId)
                .orElseThrow(RuntimeException::new);

        log.info("download imageFile: {}", profile);

        return ImageUtils.decompressImage(profile.getProfileData());
    }
}
