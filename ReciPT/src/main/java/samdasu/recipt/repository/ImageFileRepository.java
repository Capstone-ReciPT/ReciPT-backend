package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.ImageFile;

import java.util.Optional;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {

    Optional<ImageFile> findById(Long imageId);

    Optional<ImageFile> findByFilename(String filename);
}
