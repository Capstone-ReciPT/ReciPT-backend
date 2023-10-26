package samdasu.recipt.security.config.jwt;

public interface JwtProperties {
    //    String SECRET = "삼다수"; // 우리 서버만 알고 있는 비밀값
//    int EXPIRATION_TIME = 864000000; // 10일 (1/1000초)
    String AUTHORITIES_KEY = "auth";
    String TOKEN_PREFIX = "Bearer";
    String HEADER_STRING = "Authorization";
    long ACCESS_TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000L;              // 24시간
    long REFRESH_TOKEN_EXPIRE_TIME = 7 * 24 * 60 * 60 * 1000L;    // 7일
}
