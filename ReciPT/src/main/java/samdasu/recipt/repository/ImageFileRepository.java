package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.ImageFile;

public interface ImageFileRepository extends JpaRepository<ImageFile, Long> {
}
