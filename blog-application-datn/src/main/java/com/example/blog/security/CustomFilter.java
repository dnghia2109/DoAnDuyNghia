package com.example.blog.security;


import com.example.blog.exception.UserNotActiveException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@AllArgsConstructor
public class CustomFilter extends OncePerRequestFilter {
    private final CustomUserDetailsService customUserDetailsService;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Lấy ra email trong session
        String userEmail = (String) request.getSession().getAttribute("MY_SESSION");
        log.info("email = {}", userEmail);
        if (userEmail == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // Lấy ra thông tin của user
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userEmail);

        // Tạo đối tượng phân quyền
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                userDetails.getUsername(),
                null,
                userDetails.getAuthorities()
        );

        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        filterChain.doFilter(request, response);
    }
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        // Lấy thông tin username (email)  trong session
//        String userEmail = (String) request.getSession().getAttribute("MY_SESSION");
//
//        // Tạo đối tượng xác thực
//        UsernamePasswordAuthenticationToken authentication = getAuthentication(userEmail);
//        if(authentication == null){
//            filterChain.doFilter(request, response);
//            return;
//        }
//
//        // Lưu vào trong context
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        filterChain.doFilter(request, response);
//    }
//
//    public UsernamePasswordAuthenticationToken getAuthentication(String userEmail) {
//        if (userEmail == null) {
//            return null;
//        }
//
//        // Lấy ra thông tin của user theo email
//        UserDetails user = customUserDetailsService.loadUserByUsername(userEmail);
//
//        // Tạo đối tượng xác thực
//        return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
//    }
}
