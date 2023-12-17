package com.example.blog.service;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.dto.SavedBlogDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.SavedBlog;
import com.example.blog.entity.User;
import com.example.blog.exception.BadRequestException;
import com.example.blog.exception.NotFoundException;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.SavedBlogRepository;
import com.example.blog.security.ICurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SavedBlogService {
    @Autowired
    private SavedBlogRepository savedBlogRepository;
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private ICurrentUser iCurrentUser;



    // TODO: Người dùng lưu bài viết vào danh sách
    public SavedBlogDto addBlogToSavedList(Integer blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> {
           throw new NotFoundException("Không tìm thấy bài viết có id - " + blogId);
        });
        User curUser = iCurrentUser.getUser();
        Optional<SavedBlog> optionalSavedBlog = savedBlogRepository.findByBlogAndUser(blog, curUser);
        if (optionalSavedBlog.isPresent()) {
            throw new BadRequestException("Bài viết đã tồn tại trong Danh sách yêu thích!");
        }
        SavedBlog savedBlog = SavedBlog.builder()
                .blog(blog)
                .user(curUser)
                .build();
        savedBlogRepository.save(savedBlog);
        blog.setViews(blog.getViews() - 1);
        blogRepository.save(blog);
        return new SavedBlogDto(savedBlog);
    }

    // TODO: Người dùng xoá blog khỏi danh sách
    public void removeSavedBlog(Integer blogId, String type) {
        User curUser = iCurrentUser.getUser();
        SavedBlog savedBlog = savedBlogRepository.findByIdBlog(blogId, curUser).orElseThrow(() -> {
            throw new NotFoundException("Không tìm thấy bài viết được lưu có id - " + blogId);
        });

        // Khắc phục trường hợp xóa blog khỏi DS lưu làm tăng lượt view
        if (type.equalsIgnoreCase("detail-page")) {
            Blog blog = blogRepository.findById(blogId).orElseThrow(() -> {
                throw new NotFoundException("Không tìm thấy bài viết có id - " + blogId);
            });
            blog.setViews(blog.getViews() - 1);
            blogRepository.save(blog);
        }

        //savedBlogRepository.deleteById(savedBlog.getId());
        savedBlogRepository.delete(savedBlog);
    }


    // TODO: Người dùng lấy ra danh sách các blog đã lưu
    public Page<SavedBlogDto> getAllSavedBlogs(Integer page, Integer pageSize) {
        User curUser = iCurrentUser.getUser();
        Page<SavedBlogDto> pageInfo = savedBlogRepository.findAll(PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()))
                //.stream().filter(savedBlog -> savedBlog.getBlog().getStatus() && savedBlog.getBlog().getApprovalStatus() == EApprovalStatus.APPROVE)
                .map(SavedBlogDto::new);
        Page<SavedBlogDto> pageInfo2 = savedBlogRepository.getAllSavedBlogs(PageRequest.of(page - 1, pageSize, Sort.by("createdAt").descending()), curUser)
                //.stream().filter(savedBlog -> savedBlog.getBlog().getStatus() && savedBlog.getBlog().getApprovalStatus() == EApprovalStatus.APPROVE)
                .map(SavedBlogDto::new);
        return pageInfo2;
    }

    // TODO: Check bài viết đã nằm trong danh sách SavedList chưa
    public boolean blogIsSaved(Integer blogId) {
        Blog blog = blogRepository.findById(blogId).orElseThrow(() -> {
            throw new NotFoundException("Không tìm thấy bài viết có id - " + blogId);
        });
        User curUser = iCurrentUser.getUser();
        Optional<SavedBlog> savedBlogOptional = savedBlogRepository.findByBlogAndUser(blog, curUser);

        if (savedBlogOptional.isPresent()) {
            return true;
        }
        return false;
    }
}
