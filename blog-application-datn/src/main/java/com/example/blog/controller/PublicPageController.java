package com.example.blog.controller;

import com.example.blog.dto.BlogDto;
import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.TagDto;
import com.example.blog.entity.User;
import com.example.blog.security.ICurrentUser;
import com.example.blog.service.*;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class PublicPageController {
    private final BlogService blogService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final CommentService commentService;
    private final UserService userService;
    private final ICurrentUser iCurrentUser;
    private final SavedBlogService savedBlogService;
    private final WebService webService;
    private final ModelMapper modelMapper;

    public PublicPageController(BlogService blogService, CategoryService categoryService, TagService tagService, CommentService commentService, UserService userService, ICurrentUser iCurrentUser, SavedBlogService savedBlogService, WebService webService,
                                ModelMapper modelMapper) {
        this.blogService = blogService;
        this.categoryService = categoryService;
        this.tagService = tagService;
        this.commentService = commentService;
        this.userService = userService;
        this.iCurrentUser = iCurrentUser;
        this.savedBlogService = savedBlogService;
        this.webService = webService;
        this.modelMapper = modelMapper;
    }

    /*
    * @author: Lai Duy Nghia
    * @since: 27/11/2023 23:04
    * @description:
    * @update:
    *
    * */

    // TODO: Hiển thị trang chủ
    @GetMapping("/homepage")
    public String getHomePageNew(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategoryPublic();
        List<BlogDto> blogs = blogService.getBlogDtos();
        List<TagDto> tags = tagService.getAllTags().stream()
                .map((tag) -> modelMapper.map(tag, TagDto.class))
                .collect(Collectors.toList());
        User curUser = webService.getUserDetailPage();
        model.addAttribute("user", curUser);
        model.addAttribute("blogList", blogs);
        model.addAttribute("categoryList", categories);
        model.addAttribute("tagList", tags);
        return "public/homepage";
    }

    // TODO: Hiển thị chi tiết bài viết
    @GetMapping("/homepage/blogs/{blogId}/{blogSlug}")
    public String getBlogDetail(@PathVariable Integer blogId, @PathVariable String blogSlug, Model model) {
        List<CategoryDto> categories = categoryService.getAllCategoryPublic();
        BlogDto blog = blogService.getBlogDtoById(blogId);
        Boolean isExistInSavedList = savedBlogService.blogIsSaved(blogId);
        User curUser = webService.getUserDetailPage();
        model.addAttribute("user", curUser);
        model.addAttribute("blog", blog);
        model.addAttribute("isExistInSavedList", isExistInSavedList);
        model.addAttribute("categoryList", categories);
        return "public/detail-blog";
    }

    // TODO: Hiển thị trang tìm kiếm bài viết
    @GetMapping("/homepage/blogs/search")
    public String getBlogsSearchPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                     @RequestParam(required = false, defaultValue = "id") String sortField,
                                     @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                     @RequestParam String keyword,
                                     Model model) {
//        Page<BlogDto> blogsSearchResult = blogService.
        return "public/blogs";
    }

    // TODO: Hiển thị Trang thông tin tài khoản
    @GetMapping("/user/{id}/info")
    public String getUserProfile() {

        return "public/detail-blog";
    }

    @GetMapping("/api/v1/public/blogs/cate")
    public ResponseEntity<?> getBlogByCate(@RequestParam String keyword) {
        List<CategoryDto> categories = categoryService.getAllCategoryPublic();
        return ResponseEntity.ok(categories);
    }


}
