package com.example.blog.security;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

//@Configuration
//@EnableWebSecurity
//@EnableMethodSecurity(
//        securedEnabled = true,
//        jsr250Enabled = true
//)
//@AllArgsConstructor
//public class SecurityConfig {
//    private final CustomFilter customFilter;
//    private final CustomAccessDenied customAccessDenied;
//    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
//    private final UserDetailsService userDetailsService;
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public AuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setPasswordEncoder(passwordEncoder());
//        provider.setUserDetailsService(userDetailsService);
//        return provider;
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
//        return configuration.getAuthenticationManager();
//    }
//
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        String[] PUBLIC = {
//                "/",
//                "/roles",
//                "api/v1/auth/*",
//                "api/v1/java03/*",
//                "api/v1/java04/*",
//                "api/v1/mysql/lv01/*","api/v1/mysql/lv02/*"
//        };
//        http
//            .csrf(c -> c.disable())
//            .authorizeHttpRequests((authz) -> authz
//                    .anyRequest().permitAll()
//            )
//            .logout((logout) -> logout
//                    .logoutUrl("/api/v1/admin/logout")
//                    .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
//                    .deleteCookies("JSESSIONID")
//                    .invalidateHttpSession(true)
//                    .permitAll()
//            )
//            .exceptionHandling(exceptionHandling -> exceptionHandling
//                    .accessDeniedHandler(customAccessDenied)
//                    .authenticationEntryPoint(customAuthenticationEntryPoint)
//            )
//            //.authenticationProvider(authenticationProvider)
//            .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
//
//        return http.build();
////            .csrf()
////                .disable()
////                .authorizeHttpRequests()
////                .requestMatchers(PUBLIC).permitAll()
////                .requestMatchers("/admin/blogs/own-blogs", "/admin/blogs/create",
////                        "/api/v1/admin/blogs").hasAnyRole("AUTHOR", "ADMIN")
////                .requestMatchers("/admin/blogs", "").hasRole("ADMIN")
////                //.requestMatchers("/api/v1/java03", /api/v1/java04).hasAuthority("ROLE_AUTHOR")
////                .anyRequest().authenticated()
////            .and()
////                .logout()
////                    .logoutUrl("/logout-handle")
////                    .logoutSuccessUrl("/")
////                    .invalidateHttpSession(true)
////                    .deleteCookies("JSESSIONID")
////                    .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
////                    .permitAll()
////            .and()
////                .exceptionHandling()
////                .authenticationEntryPoint(customAuthenticationEntryPoint)
////                .accessDeniedHandler(customAccessDenied)
////            .and()
////                .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
////        return http.build();
//    }
//}
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomFilter customFilter;

    @Autowired
    private CustomUserDetailsService customUserDetailService;

    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Autowired
    private CustomAccessDenied customAccessDenied;


    @Bean
    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return new CustomAccessDenied();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/admin-assets/**","/admin-lte/**","/assets/**", "/css/**", "/img/**",
                "/js/**", "/vendor/**" ,"/error/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/homepage/**", "/admin/login", "/api/v1/**").permitAll()
                .antMatchers("/dashboard/blogs/own-blogs",
                             "/dashboard/blogs/create", "/dashboard/blogs/{id}/detail",
                             "/api/v1/admin/blogs/**")
                    .hasAnyRole("AUTHOR", "ADMIN")
                .antMatchers("/api/v1/admin/**","/dashboard/admin/**").hasRole("ADMIN")
                .anyRequest().authenticated()
//                .antMatchers("/profile").hasRole("USER")
//                .antMatchers("/admin/blogs").hasAnyRole("EDITOR", "ADMIN")
//                .antMatchers("/admin/users").hasRole("ADMIN")
//                .and()
//                    .formLogin()
//                    .loginPage("/login")
//                    .loginProcessingUrl("/login-process")
//                    .usernameParameter("username")
//                    .passwordParameter("password")
//                    .defaultSuccessUrl("/", true)
//                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/api/v1/admin/logout")
                    .logoutSuccessUrl("/")
                    .deleteCookies("JSESSIONID")
                    .invalidateHttpSession(true)
                    .permitAll()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .accessDeniedHandler(customAccessDenied)
                .and()
                    .addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}