package samdasu.recipt.controller.dto.ImageFile;

import lombok.Getter;
import lombok.Setter;
import samdasu.recipt.entity.Profile;

@Getter
@Setter
public class ProfileDto {
    private String filename;
    private String type;
    private byte[] profileData;

    public ProfileDto(String filename, String type, byte[] profile) {
        this.filename = filename;
        this.type = type;
        this.profileData = profile;
    }

    public static ProfileDto createImageFileRequestDto(String filename, String type, byte[] profile) {
        return new ProfileDto(filename, type, profile);
    }

    public ProfileDto(Profile profile) {
        filename = profile.getFilename();
        type = profile.getType();
        profileData = profile.getProfileData();
    }
}
