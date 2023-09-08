package com.example.newsportal.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private CustomFilter customFilter;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Autowired
    private CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Provider tìm kiếm user(tìm qua userDetailService) và so sánh password (ss pass qua passEncoder)
    @Bean
    public AuthenticationProvider authenticationProvider(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(customUserDetailsService);
        return provider;
    }

    // authenticationManager sẽ quản lý việc xác thực
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        String[] PUBLIC = {
                "/",
                "api/v1/auth/*",
                "api/v1/java03/*",
                "api/v1/java04/*",
                "api/v1/mysql/lv01/*","api/v1/mysql/lv02/*"
        };
        http
            .csrf()
                .disable()
            .authorizeHttpRequests()
                .requestMatchers(PUBLIC).permitAll()
                .requestMatchers("/api/v1/java01/").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/v1/java02/").hasRole("ADMIN")
                //.requestMatchers("/api/v1/java03", /api/v1/java04).hasAuthority("ROLE_AUTHOR")
                .anyRequest().authenticated()
            .and()
                .logout()
                .logoutUrl("/logout-handle")
                .logoutSuccessUrl("/")
//                .invalidateHttpSession(true)
//                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler((new HttpStatusReturningLogoutSuccessHandler(HttpStatus.OK)))
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                .permitAll()
            .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // không sử dụng đến session để lưu session mỗi khi login
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(customAuthenticationEntryPoint)
                .accessDeniedHandler(customAccessDeniedHandler)
            .and()
                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

// @Configuration dùng để đánh dấu một class là một class cấu hình và cho phép sử dụng @Bean để tạo các bean tùy chỉnh.
// @Bean dùng trong một class cấu hình (@Configuration) để xác định một phương thức tạo và cấu hình một bean cụ thể.