package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.RegisterRecipeThumbnail;

import java.util.Optional;

public interface RegisterRecipeThumbnailRepository extends JpaRepository<RegisterRecipeThumbnail, Long> {

    Optional<RegisterRecipeThumbnail> findById(Long thumbnailId);

    Optional<RegisterRecipeThumbnail> findByFilename(String filename);
}
