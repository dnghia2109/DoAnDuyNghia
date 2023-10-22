package com.example.blog.service;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.dto.BlogDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.entity.User;
import com.example.blog.exception.NotFoundException;
import com.example.blog.mapper.BlogMapper;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CategoryRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.UpsertBlogRequest;
import com.example.blog.security.ICurrentUser;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final ICurrentUser iCurrentUser;
    private final ModelMapper modelMapper;


    public Page<BlogPublic> getAllBlog(Integer page, Integer pageSize) {
        Page<BlogPublic> pageInfo = blogRepository.findBlogs(PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));
        return pageInfo;
    }

//    public Page<BlogPublic> getAllOwnBlog(Integer page, Integer pageSize) {
//        User user = iCurrentUser.getUser();
//
//        Page<BlogPublic> pageInfo = blogRepository.findByUser_IdOrderByCreatedAtDesc(
//                user.getId(),
//                PageRequest.of(page - 1, pageSize)
//        );
//
//        return pageInfo;
//    }

//    public List<BlogPublic> getAllOwnBlog() {
//        User user = iCurrentUser.getUser();
//
//        List<BlogPublic> pageInfo = blogRepository.findByUser_IdOrderByCreatedAtDesc(user.getId());
//        return pageInfo;
//    }

//    @Transactional
//    public BlogPublic createBlog(UpsertBlogRequest request) {
//        // TODO: Validate thông tin (nếu cần thiết) - validation
//
//        // Tìm kiếm category
//        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(null);
//
//        // User đang login
//        User user = iCurrentUser.getUser();
//
//        // Tao blog
//        Slugify slugify = Slugify.builder().build();
//        Blog blog = Blog.builder()
//                .title(request.getTitle())
//                .slug(slugify.slugify(request.getTitle()))
//                .content(request.getContent())
//                .description(request.getDescription())
//                .thumbnail(request.getThumbnail())
//                .status(request.getStatus())
//                .approvalStatus(EApprovalStatus.PENDING)
//                .category(category)
//                .comments(new ArrayList<>())
//                .user(user)
//                .build();
//
//        blogRepository.save(blog);
//        BlogDto blogDto = new BlogDto(blog);
//        return BlogPublic.of(blog);
//    }

    public BlogPublic getBlogById(Integer id) {
        Blog blog =  blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });
        return BlogPublic.of(blog);
    }

//    @Transactional
//    public BlogPublic updateBlog(Integer id, UpsertBlogRequest request) {
//        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
//            throw new NotFoundException("Not found blog with id = " + id);
//        });
//
//        // TODO: Validate thông tin (nếu cần thiết) - validation
//
//        // Tìm kiếm category
//        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(null);
//
//        Slugify slugify = Slugify.builder().build();
//        blog.setTitle(request.getTitle());
//        blog.setSlug(slugify.slugify(request.getTitle()));
//        blog.setDescription(request.getDescription());
//        blog.setContent(request.getContent());
//        blog.setStatus(request.getStatus());
//        blog.setThumbnail(request.getThumbnail());
//        blog.setCategory(category);
//
//        blogRepository.save(blog);
//        return BlogPublic.of(blog);
//    }

    @Transactional
    public void deleteBlog(Integer id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });

        blogRepository.delete(blog);
    }

    /*
    * @author: Lai Duy Nghia
    * @since: 10/10/2023 21:46
    * @description:
    * @update:
    *
    * */

//    // Danh sách các blog (ở phía quản trị)
//    public Page<BlogDto> getBlogDto(Integer page, Integer pageSize) {
//        return blogRepository.findBlogsDto(PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));
//    }
//
//    // Lấy chi tiết bài viết
//    public BlogDto getBlogDtoById(Integer id) {
//        Blog blog =  blogRepository.findById(id).orElseThrow(() -> {
//            throw new NotFoundException("Not found blog with id = " + id);
//        });
//        return BlogMapper.toDto(blog);
//    }


    public List<BlogDto> getBlogDtos() {
        return blogRepository.getAllBlogDtos();
    }
    
    
// ============================================================================================================================================================================

    /*
    * @author: Lai Duy Nghia
    * @since: 14/10/2023 14:20
    * @description:  
    * @update: 
    *
    * */

    // Danh sách các blog (ở phía quản trị)
    public Page<BlogDto> getBlogDto(Integer page, Integer pageSize) {
        return blogRepository.findBlogsDto(PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));
    }

    // Lấy chi tiết bài viết
    public BlogDto getBlogDtoById(Integer id) {
        Blog blog =  blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });
        return BlogMapper.toDto(blog);
    }

    // Lấy ra danh sách các bài viết có approve status là pending (admin)
    public Page<BlogDto> getBlogsWithApproveStatus(Integer page, Integer pageSize, String approvalStatus) {
        Page<BlogDto> pageInfo = blogRepository.getBlogsWithApproveStatus(PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()), EApprovalStatus.valueOf(approvalStatus));
        return pageInfo;
    }


    public Page<BlogDto> getAllOwnBlog(Integer page, Integer pageSize) {
        User user = iCurrentUser.getUser();
        Page<BlogDto> pageInfo = blogRepository.findByUser_IdOrderByCreatedAtDesc(user.getId(),
                PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));
        return pageInfo;
    }

    @Transactional
    public BlogDto createBlog(UpsertBlogRequest request) {
        // TODO: Validate thông tin (nếu cần thiết) - validation

        // Tìm kiếm category
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(null);

        // User đang login
        User user = iCurrentUser.getUser();

        // Tao blog
        Slugify slugify = Slugify.builder().build();
        Blog blog = Blog.builder()
                .title(request.getTitle())
                .slug(slugify.slugify(request.getTitle()))
                .content(request.getContent())
                .description(request.getDescription())
                .thumbnail(request.getThumbnail())
                .status(request.getStatus())
                .approvalStatus(EApprovalStatus.PENDING)
                .category(category)
                .comments(new ArrayList<>())
                .user(user)
                .build();

        blogRepository.save(blog);
        BlogDto blogDto = new BlogDto(blog);
        return BlogMapper.toDto(blog);
    }

    @Transactional
    public BlogDto updateBlog(Integer id, UpsertBlogRequest request) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });

        // TODO: Validate thông tin (nếu cần thiết) - validation

        // Tìm kiếm category
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(null);

        Slugify slugify = Slugify.builder().build();
        blog.setTitle(request.getTitle());
        blog.setSlug(slugify.slugify(request.getTitle()));
        blog.setDescription(request.getDescription());
        blog.setContent(request.getContent());
        blog.setStatus(request.getStatus());
        blog.setThumbnail(request.getThumbnail());
        blog.setCategory(category);

        // Trường hợp này sử dụng cho việc update khi các blog bị admin từ chối phê duyệt
        if (blog.getApprovalStatus() == EApprovalStatus.NOT_APPROVE) {
            blog.setApprovalStatus(EApprovalStatus.PENDING);
            blog.setNote(" ");
        }

        blogRepository.save(blog);
        return BlogMapper.toDto(blog);
    }

    // TODO: Phê duyệt bài viết (ADMIN)
    @Transactional
    public BlogDto approveBlog(Integer id) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });

        blog.setApprovalStatus(EApprovalStatus.APPROVE);
        blog.setNote(null);
        blogRepository.save(blog);

        return BlogMapper.toDto(blog);
    }

    // TODO: Không phê duyện bài viết (ADMIN)
    @Transactional
    public BlogDto notApproveBlog(Integer id, UpsertBlogRequest request) {
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });

        blog.setApprovalStatus(EApprovalStatus.NOT_APPROVE);
        blog.setNote(request.getNote());
        blogRepository.save(blog);
        return BlogMapper.toDto(blog);
    }

    // TODO: Danh sách các bài viết của cá nhân bị từ chối phê duyệt (AUTHOR or ADMIN)
    public Page<BlogDto> getBlogsNotApproveByUser(Integer page, Integer pageSize) {
        User user = iCurrentUser.getUser();
//        Page<BlogDto> pageInfo = blogRepository.findByUser_IdOrderByCreatedAtDesc(user.getId(),
//                PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()));
        Page<BlogDto> pageInfo = blogRepository.findByUser_IdAndApprovalStatus(user.getId(), EApprovalStatus.NOT_APPROVE,
                PageRequest.of(page - 1, pageSize, Sort.by("createdAt").ascending()));
        return pageInfo;
    }



    // TODO: hệ thống gửi mail tự động
//    @Shcheduled()
//    public void scheduleFixedDelayTask() {
//        System.out.println(
//                "Fixed delay task - " + System.currentTimeMillis() / 1000);
//    }
        
}
