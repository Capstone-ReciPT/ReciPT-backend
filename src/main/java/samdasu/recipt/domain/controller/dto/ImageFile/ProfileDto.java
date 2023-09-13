package samdasu.recipt.domain.controller.dto.ImageFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import samdasu.recipt.domain.entity.Profile;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDto {
    private Long profileId;
    private String filename;
    private String type;
    private byte[] profileData;

    public ProfileDto(Long profileId, String filename, String type, byte[] profileData) {
        this.profileId = profileId;
        this.filename = filename;
        this.type = type;
        this.profileData = profileData;
    }

    public static ProfileDto createImageFileRequestDto(Long profileId, String filename, String type, byte[] profile) {
        return new ProfileDto(profileId, filename, type, profile);
    }

    public ProfileDto(Profile profile) {
        filename = profile.getFilename();
        type = profile.getType();
        profileData = profile.getProfileData();
    }
}
