package com.example.blog.controller;

import com.example.blog.constant.EFeedbackStatus;
import com.example.blog.entity.UserFeedback;
import com.example.blog.request.UserFeedbackRequest;
import com.example.blog.service.UserFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserFeedbackController {

    @Autowired
    private UserFeedbackService userFeedbackService;

    @PostMapping("/api/v1/feedback")
    public ResponseEntity<?> sendFeedback(@RequestBody UserFeedbackRequest userFeedbackRequest) {
        return new ResponseEntity<>(userFeedbackService.create(userFeedbackRequest), HttpStatus.CREATED);
    }

    @PutMapping("/api/v1/admin/feedback/{id}")
    public ResponseEntity<?> replyFeedback(@PathVariable Integer id, @RequestBody UserFeedbackRequest userFeedbackRequest) {
        return new ResponseEntity<>(userFeedbackService.replyFeedback(id, userFeedbackRequest), HttpStatus.OK);
    }

    @GetMapping("/dashboard/admin/feedback/not-reply")
    public String getAllFeedbackNotReply(@RequestParam(required = false, defaultValue = "1") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         Model model) {
        Page<UserFeedback> pageInfo = userFeedbackService.getAllUserFeedbackByStatus(EFeedbackStatus.NOT_REPLY, page, pageSize);
        return "admin/feedback/index-not-reply";
    }

    @GetMapping("/dashboard/admin/feedback/reply")
    public String getAllFeedbackReply(@RequestParam(required = false, defaultValue = "1") Integer page,
                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                      Model model) {
        Page<UserFeedback> pageInfo = userFeedbackService.getAllUserFeedbackByStatus(EFeedbackStatus.REPLY, page, pageSize);
        return "admin/feedback/index-not-reply";
    }

    @DeleteMapping("/api/v1/admin/feedback/{id}")
    public ResponseEntity<?> deleteFeedback(@PathVariable Integer id) {
        userFeedbackService.delete(id);
        return new ResponseEntity<>("Xóa thành công !", HttpStatus.OK);
    }

}
