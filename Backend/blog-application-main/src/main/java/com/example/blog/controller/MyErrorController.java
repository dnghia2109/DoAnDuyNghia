//package com.example.blog.controller;
//
//import jakarta.servlet.RequestDispatcher;
//import jakarta.servlet.http.HttpServletRequest;
//import org.springframework.boot.web.servlet.error.ErrorController;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Controller;
//
//@Controller
//public class MyErrorController implements ErrorController {
//    public String handleError(HttpServletRequest request){
//        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
//        String referer = request.getRequestURL().toString();
//        if (status != null) {
//            int statusCode = Integer.parseInt(status.toString());
//            if (statusCode == HttpStatus.NOT_FOUND.value()) {
//                return "error/404-error";
//            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
//                return "error/500-error";
//            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
//                return "error/403-error";
//            } else if (statusCode == HttpStatus.UNAUTHORIZED.value()) {
//                return "error/401-error";
//            }
//        }
//        return "error/404-error";
//    }
//}
