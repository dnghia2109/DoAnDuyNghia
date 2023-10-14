package com.example.blog.service;

import com.example.blog.dto.projection.CommentPublic;
import com.example.blog.entity.Blog;
import com.example.blog.entity.Comment;
import com.example.blog.entity.User;
import com.example.blog.exception.BadRequestException;
import com.example.blog.repository.BlogRepository;
import com.example.blog.repository.CommentRepository;
import com.example.blog.request.CommentRequest;
import com.example.blog.security.ICurrentUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
/*
* @author: Lai Duy Nghia
* @since: 30/09/2023 16:13
* @description:
* @update:
*
* */


@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final ICurrentUser iCurrentUser;
    private final BlogRepository blogRepository;

    public Page<CommentPublic> getAllCommentTestApi(int page, int pageSize) {
        return commentRepository.findComments(PageRequest.of(page, pageSize, Sort.by("updatedAt").descending()));
    }

    public CommentPublic createComment(int blogId, CommentRequest commentRequest) {
        Blog blog = blogRepository.findById(blogId).get();

        // User đang login
        User user = iCurrentUser.getUser();
        if (user == null) {
            throw new BadRequestException("Bạn cần đăng nhập để bình luận.");
        }

        Comment comment = Comment.builder()
                .content(commentRequest.getContent())
                .user(user)
                .blog(blog)
                .build();
        commentRepository.save(comment);
        return CommentPublic.of(comment);
    }

    public CommentPublic updateComment(int commentId, CommentRequest commentRequest) {
        // User đang login
        User user = iCurrentUser.getUser();
        if (user == null) {
            throw new BadRequestException("Bạn cần đăng nhập để thực hiện tác vụ.");
        }
        Comment comment = commentRepository.findById(commentId).orElse(null);

        if (!user.equals(comment.getUser())) {
            throw new BadRequestException("Bạn không phải tác giả của bình luận này");
        }

        comment.setContent(commentRequest.getContent());
        commentRepository.save(comment);
        return CommentPublic.of(comment);
    }

    public void deleteComment(int commentId) {
        // User đang login
        User user = iCurrentUser.getUser();
        Comment comment = commentRepository.findById(commentId).orElse(null);
        if (user.getId() == comment.getUser().getId()) {
            commentRepository.delete(comment);
        } else {
            throw new BadRequestException("Bạn không thể xóa bình luận này, bạn không phải tác giả của bình luận");
        }
    }
}
