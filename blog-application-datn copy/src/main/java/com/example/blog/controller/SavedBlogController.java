package com.example.blog.controller;

import com.example.blog.repository.SavedBlogRepository;
import com.example.blog.service.SavedBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class SavedBlogController {
    @Autowired
    private SavedBlogService savedBlogService;

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUTHOR', 'ROLE_USER')")
    @PostMapping("/api/v1/blogs/saved-list/{blogId}")
    public ResponseEntity<?> addBlogToSavedList(@PathVariable Integer blogId) {
        return ResponseEntity.ok(savedBlogService.addBlogToSavedList(blogId));
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUTHOR', 'ROLE_USER')")
    @GetMapping("/api/v1/blogs/saved-list")
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        return ResponseEntity.ok(savedBlogService.getAllSavedBlogs(page, pageSize));
    }

//    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUTHOR', 'ROLE_USER')")
    @DeleteMapping("/api/v1/blogs/saved-list/{blogId}")
    public ResponseEntity<?> delete(@PathVariable Integer blogId, @RequestParam(required = false, defaultValue = "") String type) {
        savedBlogService.removeSavedBlog(blogId, type);
        return ResponseEntity.ok("Xoa thanh cong");
    }

}
