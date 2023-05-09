package samdasu.recipt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import samdasu.recipt.entity.Profile;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findById(Long profileId);

    Optional<Profile> findByFilename(String filename);
}
