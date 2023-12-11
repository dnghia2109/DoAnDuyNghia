package com.example.blog.controller;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.dto.BlogDto;
import com.example.blog.dto.CategoryDto;
import com.example.blog.dto.TagDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.projection.CategoryPublic;
import com.example.blog.entity.User;
import com.example.blog.request.UpsertBlogRequest;
import com.example.blog.security.ICurrentUser;
import com.example.blog.service.*;
import com.example.blog.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class BlogController {
    private final BlogService blogService;
    private final CategoryService categoryService;
    private final TagService tagService;
    private final SavedBlogService savedBlogService;
    private final CommentService commentService;
    private final WebService webService;
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

    // TODO: Tìm kiếm bài vết (test)
    @GetMapping("/api/blogs/")
    public ResponseEntity<?> getSearchBlogs(@RequestParam(required = false, defaultValue = "1") Integer page,
                                            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                            @RequestParam(required = false, defaultValue = "id") String sortField,
                                            @RequestParam(required = false, defaultValue = "asc") String sortDir,
                                            @RequestParam(required = false, defaultValue = "") String keyword,
                                            @RequestParam(required = false, defaultValue = "") String time) {
        Page<BlogDto> result = blogService.getSearchBlogsTest(page, pageSize, sortField, sortDir, keyword, time);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("X-Total-Count", String.valueOf(result.getTotalElements()));
        return new ResponseEntity<>(result, httpHeaders, HttpStatus.OK);
    }

    /*
    * @author: Lai Duy Nghia
    * @since: 22/11/2023 15:55
    * @description:  API view for admin
    * @update:
    *
    * */

    // TODO: Danh sách tất cả bài viết trong hệ thống
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/dashboard/admin/blogs")
    public String getBlogsPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                               @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                               @RequestParam(required = false, defaultValue = "id") String sortField,
                               @RequestParam(required = false, defaultValue = "asc") String sortDir,
                               @RequestParam(required = false, defaultValue = "") String keyword,
                               @RequestParam(required = false, defaultValue = "") String time,
                               @RequestParam(name = "startDate", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
                               @RequestParam(name = "endDate", required = false)
                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate,
                               Model model) {
//        Page<BlogDto> pageInfoDto = blogService.getBlogDto(page, pageSize);
//        Page<BlogDto> pageInfoDto = blogService.getBlogsDashboard(page, pageSize, sortField, sortDir, keyword, time);
        Page<BlogDto> pageInfoDto = blogService.getSearchBlogs(page, pageSize, sortField, sortDir, keyword, startDate, endDate);
        String sortReverseDirection = sortDir.equalsIgnoreCase("asc") ? "desc" : "asc";
        model.addAttribute("page1", pageInfoDto);
        model.addAttribute("currentPage", page);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortReverseDir", sortReverseDirection);
        model.addAttribute("sortDir", sortDir);
        return "admin/blog/blog-index";
    }


    // TODO: Danh sách bài viết của tôi
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

    // TODO: Danh sách bài viết chờ được phê duyệt có approvalStatus là PENDING (ADMIN)
    // TODO: Cần chỉnh lại HTML cho page này
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

    // TODO: Danh sách các bài viết bị từ chối phê duyệt (các tác giả)
    //    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
        @GetMapping("/dashboard/blogs/pending")
        public String getBlogsPendingByUserPage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                Model model) {
            Page<BlogDto> pageInfoDto = blogService.getBlogsPendingByUser(page, pageSize);
            model.addAttribute("page", pageInfoDto);
            model.addAttribute("currentPage", page);
            return "admin/blog/blog-not-approve";
        }


    // TODO: Danh sách các bài viết bị từ chối phê duyệt (các tác giả)
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    @GetMapping("/dashboard/blogs/not-approve")
    public String getBlogsNotApprovePage(@RequestParam(required = false, defaultValue = "1") Integer page,
                                         @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                         Model model) {
        Page<BlogDto> pageInfoDto = blogService.getBlogsNotApproveByUser(page, pageSize);
        model.addAttribute("page", pageInfoDto);
        model.addAttribute("currentPage", page);
        return "admin/blog/blog-not-approve";
    }

    // TODO: Tạo bài viết
    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_AUTHOR')")
    @GetMapping("/dashboard/blogs/create")
    public String getBlogCreatePage(Model model) {
        //List<CategoryPublic> categoryList = categoryService.getAllCategory();
        List<CategoryDto> categoryList = categoryService.getAllCategories();
        List<TagDto> tagList = tagService.getAllTags().stream().map(TagDto::new).toList();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("tagList", tagList);
        return "admin/blog/blog-create";
    }

    // TODO: Chi tiết bài viết
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

// ================================================================================================================================================
    /*
    * @author: Lai Duy Nghia
    * @since: 07/12/2023 15:02
    * @description:  Danh sách RestAPI thao tác với blog
    * @update:
    *
    * */


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


    // TODO: Lấy ra danh sách blog theo ApprovalStatus
    @GetMapping("/api/blogs/approval-status")
    public ResponseEntity<?> getBlogsByApprovalStatus(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                      @RequestParam(required = false, defaultValue = "10") Integer pageSize,
                                                      @RequestParam String approvalStatus) {
        return new ResponseEntity<>(blogService.getBlogsWithApproveStatus(page, pageSize, approvalStatus), HttpStatus.OK);
    }

    // TODO: Lấy ra ds bài viết của người dùng đang đăng nhập
    @GetMapping("/api/blogs/own-blogs")
    public ResponseEntity<?> getBlogsDtoByUserLogin(@RequestParam(required = false, defaultValue = "1") Integer page,
                                                    @RequestParam(required = false, defaultValue = "10") Integer pageSize) {
        //List<BlogDto> list = blogService.getBlogDto(page, pageSize).getContent();

        return new ResponseEntity<>(blogService.getAllOwnBlog(page, pageSize), HttpStatus.OK);
    }

    // TODO: Test lấy 5 bài viết mới nhất (gửi mail tự động)
    @GetMapping("/api/blogs/new-blogs")
    public ResponseEntity<?> getTop5NewestBlogs() {
        return new ResponseEntity<>(blogService.getTop5NewestBlogs(), HttpStatus.OK);
    }

    // TODO: Chi tiết bài viết API
    @GetMapping("/api/blogs/{blogId}/{blogSlug}")
    public ResponseEntity<?> getTop5NewestBlogs(@PathVariable Integer blogId, @PathVariable String blogSlug) {
        return new ResponseEntity<>(blogService.getBlogDtoById(blogId), HttpStatus.OK);
    }
}
