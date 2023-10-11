package samdasu.recipt.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import samdasu.recipt.security.config.jwt.JwtAuthenticationFilter;
import samdasu.recipt.security.config.jwt.JwtTokenProvider;

@Configuration
@EnableWebSecurity // Spring Security 설정 클래스
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate redisTemplate;

//    @Bean
//    public WebSecurityCustomizer webSecurityCustomizer() {
//        // ACL(Access Control List, 접근 제어 목록)의 예외 URL 설정
//        return web ->
//                web.ignoring()
//                        .antMatchers("/api/signup")
//                        .antMatchers("/api/login");
////        .antMatchers("/**");
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 인터셉터로 요청을 안전하게 보호하는 방법 설정
        http
                // jwt 토큰 사용을 위한 설정
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // 예외 처리
                .and()
                .authorizeRequests() // '인증' 필요
                .antMatchers("/api/home").permitAll()

                .antMatchers("/api/search/**").permitAll()

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

//                .antMatchers("/api/chat/**").authenticated()
                .antMatchers("/api/signup", "/api/login", "/api/authority", "/api/reissue", "/api/logout").permitAll()
                .antMatchers("/api/userTest").hasRole("USER")
                .antMatchers("/api/adminTest").hasRole("ADMIN")

                .antMatchers("/actuator/**").permitAll()

                .anyRequest().authenticated()
//                .anyRequest().permitAll();

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider, redisTemplate), UsernamePasswordAuthenticationFilter.class);
        // JwtAuthenticationFilter를 UsernamePasswordAuthentictaionFilter 전에 적용시킨다.
    }
}
