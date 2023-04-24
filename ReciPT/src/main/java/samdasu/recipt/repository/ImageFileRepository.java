package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.ImageFile;

import java.util.List;
import java.util.Optional;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
    Optional<ImageFile> findByImageId(Long ImageFileId);

    List<ImageFile> findImageFilesByOriginalFilename(String originalFilename);
}
