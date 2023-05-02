//package samdasu.recipt.config.security;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//public class SecurityAuthenticationFilter extends OncePerRequestFilter {
//    @Autowired
//    private CustomUserDetailsService customUserDetailsService;
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request,
//                                    HttpServletResponse response,
//                                    FilterChain filterChain)
//            throws ServletException, IOException {
//
//        String loginId = "testA";
//        //아무 값이나 집어넣음.
//        UserDetails authentication = customUserDetailsService.loadUserByUsername(loginId);
//
//        UsernamePasswordAuthenticationToken auth =
//                //여기있는 super.setAuthenticated(true); 를 타야함.
//                new UsernamePasswordAuthenticationToken(authentication, "testA", authentication.getAuthorities());
//        SecurityContextHolder.getContext().setAuthentication(auth);
//        auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//        filterChain.doFilter(request, response);
//
//    }
//}
