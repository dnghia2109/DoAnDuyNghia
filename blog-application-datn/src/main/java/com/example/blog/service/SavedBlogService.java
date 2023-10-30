package com.example.blog.service;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.dto.SavedBlogDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.SavedBlog;
import com.example.blog.entity.User;
import com.example.blog.exception.NotFoundException;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.SavedBlogRepository;
import com.example.blog.security.ICurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

        SavedBlog savedBlog = SavedBlog.builder()
                .blog(blog)
                .user(curUser)
                .build();
        savedBlogRepository.save(savedBlog);
        return new SavedBlogDto(savedBlog);
    }

    // TODO: Người dùng xoá blog khỏi danh sách
    public void removeSavedBlog(Integer savedBlogId) {
        User curUser = iCurrentUser.getUser();
//        SavedBlog savedBlog = savedBlogRepository.findById(savedBlogId).orElseThrow(() -> {
//            throw new NotFoundException("Không tìm thấy bài viết được lưu có id - " + savedBlogId);
//        });
        SavedBlog savedBlog = savedBlogRepository.findByIdBlog(savedBlogId, curUser).orElseThrow(() -> {
            throw new NotFoundException("Không tìm thấy bài viết được lưu có id - " + savedBlogId);
        });
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
}
