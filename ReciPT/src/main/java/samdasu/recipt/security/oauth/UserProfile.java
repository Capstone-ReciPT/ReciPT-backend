package samdasu.recipt.security.oauth;

import samdasu.recipt.domain.entity.User;

public class UserProfile {
    private final String oauthId;
    private final String username;
    private final String email;
    private final String imgUrl;

    public UserProfile(String oauthId, String username, String email, String imgUrl) {
        this.oauthId = oauthId;
        this.username = username;
        this.email = email;
        this.imgUrl = imgUrl;
    }

    public User toUser() {
        return User.createOauth(oauthId, username, email, imgUrl);
    }

    public String getOauthId() {
        return oauthId;
    }

    public String getName() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
