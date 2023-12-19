package com.example.blog.service;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.dto.BlogDto;
import com.example.blog.dto.BlogSendMailDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Category;
import com.example.blog.entity.Tag;
import com.example.blog.entity.User;
import com.example.blog.exception.BadRequestException;
import com.example.blog.exception.NotFoundException;
import com.example.blog.mapper.BlogMapper;
import com.example.blog.repository.*;
import com.example.blog.request.UpsertBlogRequest;
import com.example.blog.security.ICurrentUser;
import com.example.blog.utils.Utils;
import com.github.slugify.Slugify;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BlogService {
    private final TagRepository tagRepository;
    private final UserRepository userRepository;
    private final BlogRepository blogRepository;
    private final CategoryRepository categoryRepository;
    private final ICurrentUser iCurrentUser;
    private final EntityManager entityManager;
    private final RoleRepository roleRepository;


    // TODO: Lấy ra các bài viết cho trang chủ
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

    // Lấy chi tiết bài viết phía quản trị
    public BlogDto getBlogDtoById(Integer id) {
        Blog blog =  blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });
        return BlogMapper.toDto(blog);
    }

    // Lấy ra chi tiết bài viết dành cho phía client
    public BlogDto getBlogDtoByIdForPublic(Integer id) {
        Blog blog =  blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });
        blog.setViews(blog.getViews() + 1);
        blogRepository.save(blog);
        return BlogMapper.toDto(blog);
    }

    // Lấy ra danh sách các bài viết có approve status là pending (admin)
    public Page<BlogDto> getBlogsWithApproveStatus(Integer page, Integer pageSize, String approvalStatus) {
        Page<BlogDto> pageInfo = blogRepository.getBlogsWithApproveStatus(PageRequest.of(page - 1, pageSize,
                Sort.by("createdAt").descending()), EApprovalStatus.valueOf(approvalStatus));
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

        // Tìm kiếm các tags
        List<Tag> tags = tagRepository.findByIdIn(request.getTagsId());

        // Tìm kiếm category
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(null);

        // User đang login
        User user = iCurrentUser.getUser();

        // Tao blog
        Slugify slugify = Slugify.builder().build();

        Blog blog = new Blog();
        blog.setTitle(request.getTitle());
        blog.setSlug(slugify.slugify(request.getTitle()));
        blog.setDescription(request.getDescription());
        blog.setContent(request.getContent());
        blog.setThumbnail(request.getThumbnail());
        blog.setStatus(request.getStatus());
//        if (!request.getStatus()) {
//            blog.setApprovalStatus(EApprovalStatus.NOT_READY);
//        }
        blog.setApprovalStatus(EApprovalStatus.PENDING);
        blog.setCategory(category);
        blog.setTags(tags);
        blog.setUser(user);

        blogRepository.save(blog);
        BlogDto blogDto = new BlogDto(blog);
        return BlogMapper.toDto(blog);
    }

    @Transactional
    public BlogDto updateBlog(Integer id, UpsertBlogRequest request) {
        User curUser = iCurrentUser.getUser();
        Blog blog = blogRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + id);
        });

        // TODO: Validate thông tin (nếu cần thiết) - validation

        // Tìm kiếm các tags
        List<Tag> tags = tagRepository.findByIdIn(request.getTagsId());

        // Tìm kiếm category
        Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(null);

        Slugify slugify = Slugify.builder().build();
        blog.setTitle(request.getTitle());
        blog.setSlug(slugify.slugify(request.getTitle()));
        blog.setDescription(request.getDescription());
        blog.setContent(request.getContent());
        blog.setThumbnail(request.getThumbnail());
        blog.setCategory(category);
        blog.setTags(tags);

//        // Chỉ tác giả của bài viết mới có thể cập nhật được trạng thái hiển thị (status) của bài viết
//        if (!blog.getStatus().equals(request.getStatus())) {
//            if (!blog.getUser().equals(curUser)) {
//                throw new BadRequestException("Bạn không thể cập nhật trạng thái hiển thị của bài viết do không phải tác giả");
//            } else {
//                blog.setStatus(request.getStatus());
//            }
//        }

        // Trường hợp này sử dụng cho việc update khi các blog bị admin từ chối phê duyệt
        if (blog.getApprovalStatus() == EApprovalStatus.NOT_APPROVE) {
            blog.setApprovalStatus(EApprovalStatus.PENDING);
            blog.setNote(null);
        }

//        // Trường hợp này sử dụng cho việc update lại ApproveStatus khi mà blog có status là "nháp"
//        // được author đổi trạng thái thành "công khai" (case này chỉ sử dụng cho tác giả của bài viết)
//        if((!blog.getStatus().equals(request.getStatus())) && (request.getStatus()) && (blog.getApprovalStatus() == EApprovalStatus.NOT_READY)) {
//            blog.setApprovalStatus(EApprovalStatus.PENDING);
//        }

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
        blog.setPublishedAt(LocalDateTime.now());
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

    // TODO: anh sách các bài viết của cá nhân chờ được phê duyệt PENDING (AUTHOR or ADMIN)
    public Page<BlogDto> getBlogsPendingByUser(Integer page, Integer pageSize) {
        User user = iCurrentUser.getUser();
        Page<BlogDto> pageInfo = blogRepository.findByUser_IdAndApprovalStatus(user.getId(), EApprovalStatus.PENDING,
                PageRequest.of(page - 1, pageSize, Sort.by("createdAt").ascending()));
        return pageInfo;
    }

    // TODO: Danh sách các bài viết của cá nhân bị từ chối phê duyệt NOT_APPROVE (AUTHOR or ADMIN)
    public Page<BlogDto> getBlogsNotApproveByUser(Integer page, Integer pageSize) {
        User user = iCurrentUser.getUser();
        Page<BlogDto> pageInfo = blogRepository.findByUser_IdAndApprovalStatus(user.getId(), EApprovalStatus.NOT_APPROVE,
                PageRequest.of(page - 1, pageSize, Sort.by("createdAt").ascending()));
        return pageInfo;
    }

    // TODO: Lấy ra 5 bài viết mới nhất cho mỗi category và có trạng thái hợp lệ (client)
    public List<BlogDto> getBlogsEachCate(Integer categoryId) {
        return blogRepository.getBlogsByCategory(categoryId).stream()
            .sorted(Comparator.comparing(Blog::getPublishedAt).reversed())
            .limit(5).map(blog -> {
                BlogDto blogDto = new BlogDto();
                blogDto.setId(blog.getId());
                blogDto.setTitle(blog.getTitle());
                blogDto.setAuthor(blog.getUser().getName());
                blogDto.setSlug(blog.getSlug());
                blogDto.setThumbnail(blog.getThumbnail());
                blogDto.setDescription(blog.getDescription());
                blogDto.setPublishedAt(blog.getPublishedAt());
                return blogDto;
            }).collect(Collectors.toList());
    }

    // TODO: Lấy ra 5 bài viết mới nhất trong hệ thống (client)
    public List<BlogDto> getLastestNew() {
        return blogRepository.getLastestNewsOrderByPublish(EApprovalStatus.APPROVE, Pageable.ofSize(5))
                .stream()
                .map(BlogDto::new)
                .collect(Collectors.toList());
    }


    // TODO: lấy ra 5 bài viết mới nhất (sử dụng cho gửi mail tự động) (client)
    public List<BlogDto> getTop5NewestBlogs() {
        return blogRepository.findAll().stream()
            .filter(blog -> blog.getStatus() && blog.getApprovalStatus() == EApprovalStatus.APPROVE)
            .sorted(new Comparator<Blog>() {
                @Override
                public int compare(Blog o1, Blog o2) {
                    return o2.getCreatedAt().compareTo(o1.getCreatedAt());
                }
            })
            .map(BlogDto::new)
            .limit(5)
            .collect(Collectors.toList());
    }

    public List<BlogSendMailDto> getTop5NewestBlogss() {
        return blogRepository.findAll().stream()
            .filter(blog -> blog.getStatus() && blog.getApprovalStatus() == EApprovalStatus.APPROVE)
            .sorted(new Comparator<Blog>() {
                @Override
                public int compare(Blog o1, Blog o2) {
                    return o2.getCreatedAt().compareTo(o1.getCreatedAt());
                }
            })
            .map(BlogSendMailDto::new)
            .limit(5)
            .collect(Collectors.toList());
    }

    // TODO: Xóa bài viết
    public void deleteBlog(Integer id) {

    }

    // TODO: Tìm kiếm bài viết
    public Page<BlogDto> getSearchBlogs(Integer page, Integer pageSize, String sortField, String sortDirection, String keyword, LocalDateTime startDate, LocalDateTime endDate) {
        Page<BlogDto> blogDtos = blogRepository.findBlogsDto(PageRequest.of(page - 1, pageSize, parseSortParameter(sortField, sortDirection)));
        Page<BlogDto> searchResult = blogRepository.searchBlogs(keyword, startDate, endDate, PageRequest.of(page - 1, pageSize, parseSortParameter(sortField, sortDirection)));
        return searchResult;
    }
    public Page<BlogDto> getBlogsDashboard(Integer page, Integer pageSize, String sortField, String sortDirection, String keyword, String time) {
        Page<BlogDto> blogDtos = blogRepository.findBlogsDto(PageRequest.of(page - 1, pageSize, parseSortParameter(sortField, sortDirection)));
        Page<BlogDto> searchResult = blogRepository
                .searchBlogsByFilter(keyword, time, PageRequest.of(page - 1, pageSize, parseSortParameter(sortField, sortDirection)))
                .map(BlogDto::new);
        return searchResult;
    }

    // method chuẩn
    public Page<BlogDto> getSearchBlogsTest(Integer page, Integer pageSize, String sortField, String sortDirection, String keyword, Integer categoryId, String time) {
        LocalDateTime searchStartTime = Utils.convertTimeStringToLocalDateTime(time);
        LocalDateTime searchEndTime = LocalDateTime.now();
        if (searchStartTime == null) {
            return blogRepository.findAllByStatusAndApprovalStatus(EApprovalStatus.APPROVE, keyword, categoryId, PageRequest.of(page - 1, pageSize, parseSortParameter(sortField, sortDirection)));
        }
        Page<BlogDto> searchResult = blogRepository
                .searchBlogs(keyword, categoryId, searchStartTime, searchEndTime , EApprovalStatus.APPROVE, PageRequest.of(page - 1, pageSize, parseSortParameter(sortField, sortDirection)))
                .map(BlogDto::new);
        return searchResult;
    }

    private Sort parseSortParameter(String sortField, String sortDirection) {
        if (!sortField.isEmpty() && sortField.equalsIgnoreCase("user")) {
            sortField = "user.name";
        }

        if (!sortField.isEmpty() && sortField.equalsIgnoreCase("category")) {
            sortField = "category.name";
        }

        if (!sortField.isEmpty()) {
            return Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        }

        // Trả về Sort không sắp xếp nếu sort không hợp lệ
        return Sort.unsorted();
    }


    public List<BlogDto> getLastestNewsWithTagTinNong() {
        Tag tag = tagRepository.findByName("tinnong")
                .orElseThrow(() -> new BadRequestException("Không tìm thấy tag tinnong"));
//        List<BlogDto> blogDtoList = blogRepository.findByTag(tag).stream().map(BlogDto::new).collect(Collectors.toList());
        List<BlogDto> blogDtoList = blogRepository.findByTags_Id(tag.getId()).stream()
                .sorted(Comparator.comparing(Blog::getPublishedAt).reversed())
                .limit(5)
                .map(blog -> {
                    BlogDto blogDto = new BlogDto();
                    blogDto.setId(blog.getId());
                    blogDto.setTitle(blog.getTitle());
                    blogDto.setAuthor(blog.getUser().getName());
                    blogDto.setSlug(blog.getSlug());
                    blogDto.setCategory(blog.getCategory());
                    blogDto.setThumbnail(blog.getThumbnail());
                    blogDto.setDescription(blog.getDescription());
                    blogDto.setPublishedAt(blog.getPublishedAt());
                    return blogDto;
                })
                .collect(Collectors.toList());
        return blogDtoList;
    }
}
