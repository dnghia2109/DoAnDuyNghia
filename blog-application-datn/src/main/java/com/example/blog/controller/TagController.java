package com.example.blog.controller;

import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.TagDto;
import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.request.TagRequest;
import com.example.blog.request.UpsertCategoryRequest;
import com.example.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class TagController {

    @Autowired
    private TagService tagService;

    // TODO: Danh sách tất cả các tag trong hệ thống:
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/admin/tags")
    public String getBlogPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "15") Integer pageSize,
                              Model model) {
        //Page<CategoryPublic> pageInfo = categoryService.getAllCategory(page, pageSize);
        Page<TagDto> pageInfo = tagService.getAllTagForAdmin(page, pageSize);
        model.addAttribute("page", pageInfo);
        model.addAttribute("currentPage", page);
        return "admin/tag/tag-list";
    }


    // Danh sách API
    // TODO: Tạo tag
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("api/v1/admin/tags")
    public ResponseEntity<?> createTag(@RequestBody TagRequest request) {
        TagDto tag = tagService.createTag(request);
        return new ResponseEntity<>(tag, HttpStatus.CREATED); // status code 201
    }

    // 4. Cập nhật category
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("api/v1/admin/tags/{id}")
    public ResponseEntity<?> updateTag(@RequestBody TagRequest request, @PathVariable Integer id) {
        TagDto category = tagService.updateTag(id, request);
        return ResponseEntity.ok(category);
    }

    // 5. Xóa category
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("api/v1/admin/tags/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
        tagService.deleteTag(id);
        return ResponseEntity.noContent().build();
    }

}
