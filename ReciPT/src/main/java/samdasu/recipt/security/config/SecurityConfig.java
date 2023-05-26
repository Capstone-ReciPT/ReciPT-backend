package samdasu.recipt.security.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import samdasu.recipt.domain.repository.UserRepository;
import samdasu.recipt.security.config.jwt.JwtAuthenticationFilter;
import samdasu.recipt.security.config.jwt.JwtAuthorizationFilter;
import samdasu.recipt.security.oauth.OAuthService;

@Configuration
@EnableWebSecurity // 시큐리티 활성화 -> 기본 스프링 필터체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OAuthService oAuthService;

    @Autowired
    private CorsConfig corsConfig;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                    .antMatchers("/api/signup");
        };
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilter(corsConfig.corsFilter())
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .headers().frameOptions().disable() // h2 console 접속을 위해
                .and()
//                .formLogin().disable()
//                .httpBasic().disable()

                .addFilter(new JwtAuthenticationFilter(authenticationManager()))
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userRepository))
                .authorizeRequests()
                .antMatchers("/login/**").permitAll()
                .antMatchers("/loginForm").permitAll()

                .antMatchers("/api/home").permitAll()

                .antMatchers("/api/search").permitAll()

                .antMatchers("/api/review/recipe/**").permitAll()
                .antMatchers("/api/review/register/**").permitAll()

                .antMatchers("/api/register/**").permitAll()
                .antMatchers("/api/register/save/**").authenticated()
                .antMatchers("/api/register/insert/**").authenticated()
                .antMatchers("/api/register/cancel/**").authenticated()

                .antMatchers("/api/db/**").permitAll()
                .antMatchers("/api/db/save/**").authenticated()
                .antMatchers("/api/db/insert/**").authenticated()
                .antMatchers("/api/db/cancel/**").authenticated()

                .antMatchers("/api/category/**").permitAll()

                .antMatchers("/api/chat/**").authenticated()

                .anyRequest().authenticated()

                .and()
                .oauth2Login() // OAuth2 로그인 설정 시작점
                .userInfoEndpoint() // OAuth2 로그인 성공 이후 사용자 정보를 가져올 때 설정 담당
                .userService(oAuthService); // OAuth2 로그인 성공 시, 후작업을 진행할 UserService 인터페이스 구현체 등록
    }
}







