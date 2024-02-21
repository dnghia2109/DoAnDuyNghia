package com.example.blog.controller;

import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.SavedBlogDto;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.security.ICurrentUser;
import com.example.blog.service.*;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SavedBlogController {
    private final SavedBlogService savedBlogService;
    private final WebService webService;
    private final ICurrentUser iCurrentUser;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BlogService blogService;
    private final BlogRepository blogRepository;
    private final CategoryService categoryService;
    private final AdvertisementService advertisementService;

    public SavedBlogController(SavedBlogService savedBlogService,
                               WebService webService,
                               ICurrentUser iCurrentUser,
                               UserRepository userRepository,
                               CategoryRepository categoryRepository,
                               BlogService blogService,
                               BlogRepository blogRepository,
                               CategoryService categoryService,
                               AdvertisementService advertisementService) {
        this.savedBlogService = savedBlogService;
        this.webService = webService;
        this.iCurrentUser = iCurrentUser;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.blogService = blogService;
        this.blogRepository = blogRepository;
        this.categoryService = categoryService;
        this.advertisementService = advertisementService;
    }

    @GetMapping("/saved-blog-list")
    public String getSetSavedListPage(@RequestParam(required = false, defaultValue = "1") int page,
                                      @RequestParam(required = false, defaultValue = "10") int pageSize,
                                      Model model) {
        Page<SavedBlogDto> pageInfo = savedBlogService.getAllSavedBlogs(page, pageSize);
        List<CategoryDto> categories = categoryService.getAllCategoryPublic();
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("currentPage", page);
        model.addAttribute("categoryList", categories);
        return "public/saved-blog-list";
    }


    @PostMapping("/api/v1/blogs/saved-list/{blogId}")
    public ResponseEntity<?> addBlogToSavedList(@PathVariable Integer blogId) {
        return ResponseEntity.ok(savedBlogService.addBlogToSavedList(blogId));
    }


    @GetMapping("/api/v1/blogs/saved-list")
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "1") Integer page,
                                    @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        Page<?> uu = savedBlogService.getAllSavedBlogs(page, pageSize);
        return ResponseEntity.ok(savedBlogService.getAllSavedBlogs(page, pageSize));
    }


    @DeleteMapping("/api/v1/blogs/saved-list/{blogId}")
    public ResponseEntity<?> delete(@PathVariable Integer blogId, @RequestParam(required = false, defaultValue = "") String type) {
        savedBlogService.removeSavedBlog(blogId, type);
        return ResponseEntity.ok("Xoa thanh cong");
    }
}
