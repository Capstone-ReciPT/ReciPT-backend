package samdasu.recipt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import samdasu.recipt.controller.dto.ImageFile.PhotoDto;
import samdasu.recipt.controller.dto.ImageFile.PhotoResponseDto;
import samdasu.recipt.entity.ImageFile;
import samdasu.recipt.repository.ImageFileRepository;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PhotoService {
    private final ImageFileRepository photoRepository;

    /**
     * 이미지 개별 조회
     */
    @Transactional(readOnly = true)
    public PhotoDto findByFileId(Long id) {

        ImageFile entity = photoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 파일이 존재하지 않습니다."));

        PhotoDto photoDto = PhotoDto.createPhotoDto(entity.getOriginalFilename(), entity.getFileUrl(), entity.getFileSize());

        return photoDto;
    }

    /**
     * 이미지 전체 조회
     */
    @Transactional(readOnly = true)
    public List<PhotoResponseDto> findAllByBoard(Long ImageId) {

        List<ImageFile> photoList = photoRepository.findAllById(ImageId);

        return photoList.stream()
                .map(PhotoResponseDto::new)
                .collect(Collectors.toList());
    }

}