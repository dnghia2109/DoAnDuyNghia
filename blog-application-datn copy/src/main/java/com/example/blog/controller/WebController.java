package com.example.blog.controller;

import com.example.blog.dto.BlogDto;
//import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.projection.CategoryWebPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.entity.User;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.security.ICurrentUser;
import com.example.blog.service.BlogService;
import com.example.blog.service.CategoryService;
import com.example.blog.service.SavedBlogService;
import com.example.blog.service.WebService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @GetMapping("/home")
    public String getIndex(Model model) {
        //List<Category> categories = categoryRepository.findAll();
        List<Category> categories = categoryRepository.findAllCategoriesWithLatestBlogs();
        model.addAttribute("categories", categories);
        List<Blog> blogs = blogRepository.findAll();
        model.addAttribute("blogs", blogs);

        return "web/index";
    }

    @GetMapping("/")
    public String getHome(Model model) {
        Page<BlogPublic> publicPage = webService.getAllBlog(0, 5);
        List<CategoryWebPublic> categoryList = webService.getTop5Category();

        model.addAttribute("page", publicPage);
        model.addAttribute("categoryList", categoryList);
        return "web/main";
    }

    @GetMapping("/fragment/header-user")
    public String getHeaderUser(Model model) {
        List<CategoryWebPublic> categoryList = webService.getTop5Category();
        User user = iCurrentUser.getUser();
        User curUser = userRepository.findByEmail(user.getEmail()).get();

        User user1 = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User đang login = {}", user1.getName());
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("curUser", user1);
        return "fragments/header-user";
    }


    @GetMapping("/user/info")
    public String getUserLoginDetailInfo(Model model) {
        User curUser = webService.getUserDetailPage();
        model.addAttribute("user", curUser);
        return "web/tag";
    }


    @GetMapping("/page/{page}")
    public String getPageBlog(@PathVariable Integer page, Model model) {
        if(page.equals(1)) {
            return "redirect:/";
        }
        Page<BlogPublic> publicPage = webService.getAllBlog(page - 1, 5);
        List<CategoryWebPublic> categoryList = webService.getTop5Category();

        model.addAttribute("page", publicPage);
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("currentPage", page);
        return "web/page";
    }

    @GetMapping("search")
    public String searchBlog() {
        return "web/search";
    }

    @GetMapping("categories")
    public String getAllCategory(Model model) {
        List<CategoryWebPublic> categoryList = webService.getAllCategory();
        model.addAttribute("categoryList", categoryList);
        return "web/tag";
    }

    @GetMapping("categories/{categoryName}")
    public String getBlogsOfCategory(@PathVariable String categoryName, Model model) {
        List<BlogPublic> blogList = webService.getBlogsOfCategory(categoryName);
        model.addAttribute("categoryName", categoryName);
        model.addAttribute("blogList", blogList);
        return "web/tagDetail";
    }

    @GetMapping("blogs/{blogId}/{blogSlug}")
    public String getBlogDetail(@PathVariable Integer blogId, @PathVariable String blogSlug, Model model) {
        BlogPublic blogPublic = webService.getBlogDetail(blogId, blogSlug);
        model.addAttribute("blog", blogPublic);
        return "web/blogdetail";
    }


    @GetMapping("/api/v1/public/blogs/search")
    public ResponseEntity<?> searchBlog(@RequestParam String keyword) {
        List<BlogDto> blogPublicList = webService.searchBlog1(keyword);
        return ResponseEntity.ok(blogPublicList);
    }

    // TODO: Hiển thị trang chủ
    @GetMapping("/homepage")
    public String getHomePageNew(Model model) {
        List<CategoryDto> categories = categoryService.getAllCategoryPublic();
        List<BlogDto> blogs = blogService.getBlogDtos();
        List<BlogDto> lastestBlogs = blogService.getLastestNew();
        User curUser = webService.getUserDetailPage();
        model.addAttribute("user", curUser);
        model.addAttribute("blogList", blogs);
        model.addAttribute("blogLastestList", lastestBlogs);
        model.addAttribute("categoryList", categories);
        return "public/homepage1";
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
                                     @RequestParam(required = false, defaultValue = "id") String sortField,
                                     @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                     @RequestParam(required = false, defaultValue = "") String keyword,
                                     @RequestParam(name = "startDate", required = false)
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                                     @RequestParam(name = "endDate", required = false)
                                         @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                                     Model model) {
//        Page<BlogDto> blogsSearchResult = blogService.
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
