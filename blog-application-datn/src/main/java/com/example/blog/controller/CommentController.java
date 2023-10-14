package com.example.blog.controller;

import com.example.blog.dto.projection.CommentPublic;
import com.example.blog.request.CommentRequest;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
@RequestMapping("api/v1/comments")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/")
    public ResponseEntity<?> getAllComment(@RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(commentService.getAllCommentTestApi(page, pageSize));
    }

    @PostMapping("/{blogId}")
    public ResponseEntity<?> addComment(@PathVariable int blogId, @RequestBody CommentRequest commentRequest) {
//         commentService.createComment(blogId, commentRequest);
        return ResponseEntity.ok(commentService.createComment(blogId, commentRequest));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable int commentId, @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.updateComment(commentId, commentRequest));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId, @RequestBody CommentRequest commentRequest) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Xóa thành công");
    }
}
