package com.example.blog.controller;

import com.example.blog.dto.CommentDto;
import com.example.blog.dto.projection.CommentPublic;
import com.example.blog.request.CommentRequest;
import com.example.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

//@RestController
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/dashboard/admin/comments")
    public String getCommentPageForAdmin(@RequestParam(required = false, defaultValue = "1") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         Model model) {
        Page<CommentDto> pageInfo = commentService.getAllCommentTestApi(page, pageSize);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("currentPage", page);
        return "/admin/comment/comment-list";
    }

    @GetMapping("/api/v1/comments")
    public ResponseEntity<?> getAllComment(@RequestParam(required = false, defaultValue = "0") Integer page,
                                           @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(commentService.getAllCommentTestApi(page, pageSize));
    }

    @PostMapping("api/v1/comments/{blogId}")
    public ResponseEntity<?> addComment(@PathVariable int blogId, @RequestBody CommentRequest commentRequest) {
//         commentService.createComment(blogId, commentRequest);
        return ResponseEntity.ok(commentService.create(blogId, commentRequest));
    }

    @PutMapping("api/v1/comments/{commentId}")
    public ResponseEntity<?> updateComment(@PathVariable int commentId, @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.update(commentId, commentRequest));
    }

    @DeleteMapping("api/v1/comments/{commentId}")
    public ResponseEntity<?> deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok("Xóa thành công");
    }

    @DeleteMapping("api/v1/admin/comments/{commentId}")
    public ResponseEntity<?> deleteCommentAdmin(@PathVariable int commentId) {
        commentService.deleteCommentAdmin(commentId);
        return ResponseEntity.ok("Xóa bình luận thành công");
    }
}
