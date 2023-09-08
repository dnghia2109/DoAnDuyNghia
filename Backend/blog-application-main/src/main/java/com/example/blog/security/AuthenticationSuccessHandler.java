package com.example.blog.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.CookieRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.stereotype.Component;
import org.thymeleaf.extras.springsecurity6.auth.AuthUtils;

import java.io.IOException;

@Component
public class AuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private AuthUtils authUtils;

    private RequestCache requestCache = new CookieRequestCache();

    @Autowired
    public AuthenticationSuccessHandler(AuthUtils authUtils) {
        this.authUtils = authUtils;
        super.setRequestCache(requestCache);
        super.setDefaultTargetUrl("/app");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        //do whatever you need here specific to your auth success process
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
