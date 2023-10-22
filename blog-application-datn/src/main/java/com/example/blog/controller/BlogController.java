package com.example.blog.controller;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.dto.BlogDto;
import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.User;
import com.example.blog.request.UpsertBlogRequest;
import com.example.blog.security.ICurrentUser;
import com.example.blog.service.BlogService;
import com.example.blog.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final CategoryService categoryService;
    private final ICurrentUser iCurrentUser;

    /*
    * @author: Lai Duy Nghia
    * @since: 07/10/2023 14:34
    * @description:  Lấy danh sách bàio viết theo BlogDto
    * @update:
    *
    * */
    @GetMapping("/api/blogDtos")
    public ResponseEntity<?> getBlogDto(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        List<BlogDto> list = blogService.getBlogDto(page, pageSize).getContent();

        return new ResponseEntity<>(blogService.getBlogDto(page, pageSize), HttpStatus.OK);
    }

    @GetMapping("/blogs-dto")
    public String getBlogDtoPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                 Model model) {
        Page<BlogDto> pageInfo =  blogService.getBlogDto(page, pageSize);
        model.addAttribute("page", pageInfo);
        model.addAttribute("currentPage", page);
        return "public/home";
    }

    // Lấy ra danh sách blog theo ApprovalStatus
    @GetMapping("/api/blogs/approval-status")
    public ResponseEntity<?> getBlogsByApprovalStatus(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                      @RequestParam String approvalStatus) {
        return new ResponseEntity<>(blogService.getBlogsWithApproveStatus(page, pageSize, approvalStatus), HttpStatus.OK);
    }

    // Lấy ra ds bài viết của người dùng đang đăng nhập
    @GetMapping("/api/blogs/own-blogs")
    public ResponseEntity<?> getBlogsDtoByUserLogin(@RequestParam(required = false, defaultValue = "1") Integer page,
                                        @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        //List<BlogDto> list = blogService.getBlogDto(page, pageSize).getContent();

        return new ResponseEntity<>(blogService.getAllOwnBlog(page, pageSize), HttpStatus.OK);
    }



    // Danh sách tất cả bài viết
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    @GetMapping("/admin/blogs")
//    public String getBlogPage1(@RequestParam(required = false, defaultValue = "1") Integer page,
//                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
//                              Model model) {
//        Page<BlogPublic> pageInfo = blogService.getAllBlog(page, pageSize);
////        model.addAttribute("page", pageInfo);
//
//
//        Page<BlogDto> pageInfoDto = blogService.getBlogDto(page, pageSize);
//        model.addAttribute("page1", pageInfoDto);
//        model.addAttribute("currentPage", page);
//
//        return "admin/blog/blog-index";
//    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/admin/blogs")
    public String getBlogsPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<BlogDto> pageInfoDto = blogService.getBlogDto(page, pageSize);
        model.addAttribute("page1", pageInfoDto);
        model.addAttribute("currentPage", page);
        return "admin/blog/blog-index";
    }

    // Danh sách bài viết của tôi
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    @GetMapping("/dashboard/blogs/own-blogs")
    public String getOwnBlogsPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                 @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                 Model model) {
//        Page<BlogPublic> pageInfo = blogService.getAllOwnBlog(page, pageSize);
        Page<BlogDto> pageInfo = blogService.getAllOwnBlog(page, pageSize);
        model.addAttribute("page", pageInfo);
        model.addAttribute("currentPage", page);
        return "admin/blog/own-blog";
    }

    // Danh sách bài viết có approvalStatus là PENDING
    // Todo: Cần chỉnh lại trang HTML cho page này
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/admin/blogs/pending")
    public String getBlogsPendingPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                              @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                              Model model) {
        Page<BlogDto> pageInfoDto = blogService.getBlogsWithApproveStatus(page, pageSize, "PENDING");
        model.addAttribute("page", pageInfoDto);
        model.addAttribute("currentPage", page);
        return "admin/blog/blog-pending";
    }

    // Tạo bài viết
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    @GetMapping("/dashboard/blogs/create")
    public String getBlogCreatePage(Model model) {
        //List<CategoryPublic> categoryList = categoryService.getAllCategory();
        List<CategoryDto> categoryList = categoryService.getAllCategories();
        model.addAttribute("categoryList", categoryList);
        return "admin/blog/blog-create";
    }

    // Chi tiết bài viết
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    @GetMapping("/dashboard/blogs/{id}/detail")
    public String getBlogDetailPage(@PathVariable Integer id, Model model) {
        BlogDto blog = blogService.getBlogDtoById(id);
        //List<CategoryPublic> categoryList = categoryService.getAllCategory();
        List<CategoryDto> categoryList = categoryService.getAllCategories();
        model.addAttribute("blog", blog);
        model.addAttribute("categoryList", categoryList);
        return "admin/blog/blog-detail";
    }

    // Danh sách API
    // 1. Tạo bài viết
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    @PostMapping("/api/v1/admin/blogs")
    public ResponseEntity<?> createBlog(@RequestBody UpsertBlogRequest request) {
        return new ResponseEntity<>(blogService.createBlog(request), HttpStatus.CREATED); // 201
    }

    // 2. Cập nhật bài viết
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    @PutMapping("/api/v1/admin/blogs/{id}")
    public ResponseEntity<?> updateBlog(@PathVariable Integer id, @RequestBody UpsertBlogRequest request) {
        return ResponseEntity.ok(blogService.updateBlog(id, request)); // 200
    }

    // 3. Xóa bài viết
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    @DeleteMapping("/api/v1/admin/blogs/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build(); // 204
    }

    // 4. Phê duyệt bài viết
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/admin/approve/{id}")
    public ResponseEntity<?> approveBlog(@PathVariable Integer id) {
        return new ResponseEntity<>(blogService.approveBlog(id), HttpStatus.OK);
    }

    // 5. Không phê duyêt bài viết
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/api/v1/admin/not-approve/{id}")
    public ResponseEntity<?> notApproveBlog(@PathVariable Integer id, @RequestBody UpsertBlogRequest request) {
        return new ResponseEntity<>(blogService.notApproveBlog(id, request), HttpStatus.OK);
    }

//    @PostMapping("/api/v1/blogs-all")
//    public ResponseEntity<?> getBlogs() {
//        return ResponseEntity.ok(blogService.getBlogById());
//    }
}
