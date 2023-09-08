package com.example.blog.controller;

import com.example.blog.request.CommentRequest;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    @PostMapping("/api/add-comment/{blogId}")
    public ResponseEntity<?> sendComment(@RequestBody CommentRequest request, @PathVariable int blogId) {
        return ResponseEntity.ok(commentService.createComment(request, blogId));
    }
}
