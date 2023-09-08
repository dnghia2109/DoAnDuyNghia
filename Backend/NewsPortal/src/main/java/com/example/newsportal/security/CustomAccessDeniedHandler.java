package com.example.newsportal.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.newsportal.response.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

// Xử lý lỗi 403: forbidden
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        // Tạo object trả veef message
        ErrorResponse message = new ErrorResponse(HttpStatus.FORBIDDEN, "Bạn không có quyền");

        // Convert Object -> Json
        ObjectMapper objectMapper = new ObjectMapper();
        String messageJSON = objectMapper.writeValueAsString(message);

        // Trả về cho client
        response.addHeader("Content-type", "application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(messageJSON);
    }
}
