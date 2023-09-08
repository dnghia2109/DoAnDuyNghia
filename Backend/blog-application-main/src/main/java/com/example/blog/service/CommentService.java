package com.example.blog.service;

import com.example.blog.entity.Blog;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.exception.NotFoundException;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.request.CommentRequest;
import com.example.blog.security.ICurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final ICurrentUser iCurrentUser;

    private final CommentRepository commentRepository;
    @Autowired
    private BlogRepository blogRepository;


    public Comment createComment(CommentRequest request, Integer blogId) {
        // User đang login
        User user = iCurrentUser.getUser();

        Blog blog =  blogRepository.findById(blogId).orElseThrow(() -> {
            throw new NotFoundException("Not found blog with id = " + blogId);
        });


        Comment newComment = Comment.builder()
                .content(request.getTitle())
                .blog(blog)
                .user(user)
                .build();
        commentRepository.save(newComment);
        return newComment;
    }
}
