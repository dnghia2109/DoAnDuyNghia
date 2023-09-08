package vn.techmaster.finalproject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceCustom userDetailsServiceCustom;

    @Autowired
    private AuthenticationEntryPointCustom authenticationEntryPointCustom;

    @Autowired
    private AuthorizationFilterCustom authorizationFilterCustom;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceCustom).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                    .antMatchers("/css/**","/fonts/**","/images/**","/js/**"
                            ,"/","/forgot/**","/register/**","/login","/register-employee"
                            ,"/register-employer","/saving-user-employee","/saving-user-employer"
                            ,"/validate","/upload-avatar","/forgot","/forgot-request","/new-password"
                            ,"/validate-forgot-user","/login-authenticate","/employer/**"
                            ,"/home/**","/photo/**","/search","/home-page/**","/job-detail"
                            ,"/upload-cv","/applied-job","/pdf/**","/change-password"
                            ,"/saving-password","/save-list-for-applicant"
                            ,"/apply-list-for-applicant","/change-info-applicant"
                            ,"/saving-change-applicant","/applicant-info",
                            "/accept-applicant","/reject-applicant",
                            "apply-list-in-job").permitAll()
                    .antMatchers("/profile").hasRole("APPLICANT")
                    .antMatchers("/add-job").hasAnyRole("EMPLOEYER")
                    .antMatchers("/admin/users").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPointCustom)
                .and()
                    .formLogin()
                    .loginPage("/login")
                .and()
                    .logout()
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JOBHUNT_SESSION")
                    .permitAll()
                .and()
                    .addFilterBefore(authorizationFilterCustom, UsernamePasswordAuthenticationFilter.class);
    }
}
