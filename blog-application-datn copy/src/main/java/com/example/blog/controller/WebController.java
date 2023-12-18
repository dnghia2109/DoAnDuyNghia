package com.example.blog.controller;

import com.example.blog.dto.BlogDto;
//import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.projection.CategoryWebPublic;
import com.example.blog.entity.Advertisement;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.entity.User;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.security.ICurrentUser;
import com.example.blog.service.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
@Slf4j
public class WebController {
    private final WebService webService;
    private final ICurrentUser iCurrentUser;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final BlogService blogService;
    private final BlogRepository blogRepository;
    private final CategoryService categoryService;
    private final SavedBlogService savedBlogService;
    private final AdvertisementService advertisementService;

    // TODO: Hiển thị trang chủ
    @GetMapping("/homepage")
    public String getHomePageNew(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategoryPublic();
        List<BlogDto> lastestBlogs = blogService.getLastestNew();
        // List Blog hiển thị tin nóng
        List<BlogDto> lastestNewsWithTagTinNong = blogService.getLastestNewsWithTagTinNong();
        List<Advertisement> advertisementListOnLeftSide = advertisementService.getAdvertisementByDisplayOrder(1);
        List<Advertisement> advertisementListOnRightSide = advertisementService.getAdvertisementByDisplayOrder(2);
        List<Advertisement> advertisementListBottom = advertisementService.getAdvertisementByDisplayOrder(3);
        User curUser = webService.getUserDetailPage();
        model.addAttribute("user", curUser);
        model.addAttribute("blogLastestList", lastestBlogs);
        model.addAttribute("blogHotList", lastestNewsWithTagTinNong);
        model.addAttribute("categoryList", categories);
        model.addAttribute("advertisementListOnLeftSide", advertisementListOnLeftSide);
        model.addAttribute("advertisementListOnRightSide", advertisementListOnRightSide);
        model.addAttribute("advertisementListBottom", advertisementListBottom);
        return "public/home2";
    }

    // TODO: Hiển thị chi tiết bài viết
    @GetMapping("/homepage/blogs/{blogId}/{blogSlug}")
    public String getBlogDetailPublic(@PathVariable Integer blogId, @PathVariable String blogSlug, Model model) {
        List<CategoryDto> categories = categoryService.getAllCategoryPublic();
        BlogDto blog = blogService.getBlogDtoByIdForPublic(blogId);
        Boolean isExistInSavedList = savedBlogService.blogIsSaved(blogId);
        User curUser = webService.getUserDetailPage();
        model.addAttribute("user", curUser);
        model.addAttribute("blog", blog);
        model.addAttribute("isExistInSavedList", isExistInSavedList);
        model.addAttribute("categoryList", categories);
        return "public/detail-blog";
    }

    // TODO: Hiển thị trang tìm kiếm bài viết
    @GetMapping("/blogs")
    public String getBlogsSearchPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                     @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                     @RequestParam(required = false, defaultValue = "publishedAt") String sortField,
                                     @RequestParam(required = false, defaultValue = "desc") String sortDir,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false, defaultValue = "all") String categoryId,
                                     @RequestParam(required = false, defaultValue = "all") String time,
                                     Model model) {
        Integer categoryIdAsInteger = null;
        if (!StringUtils.isEmpty(categoryId) && !"all".equalsIgnoreCase(categoryId)) {
            categoryIdAsInteger = Integer.parseInt(categoryId);
        }
        Page<BlogDto> pageInfoDto = blogService.getSearchBlogsTest(page, pageSize, sortField, sortDir, keyword, categoryIdAsInteger, time);
//        Page<BlogDto> pageInfoDto = blogService.getSearchBlogsTest(page, pageSize, sortField, sortDir, keyword, Integer.valueOf(categoryId), time);
        List<CategoryDto> categoryList = categoryService.getAllCategoryPublic();
        List<BlogDto> lastestBlogs = blogService.getLastestNew();
        String sortReverseDirection = sortDir.equalsIgnoreCase("asc") ? "desc" : "asc";
        User curUser = webService.getUserDetailPage();
        model.addAttribute("user", curUser);
        model.addAttribute("pageInfo", pageInfoDto);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("blogLastestList", lastestBlogs);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortReverseDir", sortReverseDirection);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("time", time);
        model.addAttribute("keyword", keyword);
        model.addAttribute("categorySearchId", categoryId);

        return "public/search";
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
