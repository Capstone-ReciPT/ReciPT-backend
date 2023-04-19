package samdasu.recipt.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import samdasu.recipt.handler.AuthFailureHandler;
import samdasu.recipt.handler.AuthSuccessHandler;
import samdasu.recipt.service.UserLoginServiceImpl;

@RequiredArgsConstructor
@EnableWebSecurity //시큐리티 필터가 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserLoginServiceImpl userLoginService;
    private final AuthSuccessHandler authSuccessHandler;
    private final AuthFailureHandler authFailureHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() { //비밀번호 암호화를 위해 사용 시큐리티는 비밀번호가 암호화 되있어야 사용가능
        return new BCryptPasswordEncoder(); //회원가입 할때 사용
    }

    // 시큐리티가 대신 로그인해주는데 password를 가로채는데
    // 해당 password가 뭘로 해쉬화해서 회원가입이 되었는지 알아야
    // 같은 해쉬로 암호화해서 DB에 있는 해쉬랑 비교가능
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userLoginService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() //csrf토큰 비활성화(테스트시 걸어두는게 좋음) 시큐리티는 csrf토큰이 있어야 접근가능함
                .authorizeRequests()//인가 요청이 오면
                .antMatchers("/", "/home", "/login/**", "/css/**", "/signup/**") //해당 경로들은
                .permitAll() //접근 허용
                .anyRequest() //다른 모든 요청은
                .authenticated() //인증이 되야 들어갈 수 있다.

                .and()
                .formLogin() //로그인 폼은
                .usernameParameter("loginId") //로그인 페이지를 우리가 만든 페이지로 등록한다.
                .passwordParameter("password")
                .loginPage("/login")
                .loginProcessingUrl("/login/action") //스프링 시큐리티가 해당 주소로 요청오는 로그인을 가로채서 대신 로그인해줌 (서비스의 loadUserByName로 알아서)
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)

                .and()
                .logout() //로그아웃
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()

                .and()
                .sessionManagement() //중복 로그인
                .maximumSessions(1)  //세션 최대 허용 수
                .maxSessionsPreventsLogin(false) // false이면 중복 로그인하면 이전 로그인이 풀린다.
                .expiredUrl("/login?error=true&exception=Session Expired!");
    }
}
