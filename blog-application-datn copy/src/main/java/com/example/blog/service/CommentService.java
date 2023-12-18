package com.example.blog.service;

import com.example.blog.dto.CommentBlogDTO;
import com.example.blog.dto.CommentDto;
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
import java.util.Optional;
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

    // TODO: Thêm mới comment
    public CommentDto create(int blogId, CommentRequest commentRequest) {
        Optional<Blog> optionalBlog = blogRepository.findById(blogId);
        if (optionalBlog.isEmpty()) {
            throw new BadRequestException("Không tìm thấy bài viết có id - " + blogId);
        }
        User user = iCurrentUser.getUser();
        if (user == null) {
            throw new BadRequestException("Bạn cần đăng nhập để bình luận.");
        }
        Comment comment = new Comment();
        comment.setBlog(optionalBlog.get());
        comment.setContent(commentRequest.getContent());
        comment.setUser(user);

        commentRepository.save(comment);
        return new CommentDto(comment);
    }

    // TODO: Chỉnh sửa comment
    public CommentDto update(int commentId, CommentRequest commentRequest) {
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
        return new CommentDto(comment);
    }

    public Page<CommentDto> getAllCommentTestApi(int page, int pageSize) {
        return commentRepository.getAllCommentsAdminAPI(PageRequest.of(page, pageSize, Sort.by("updatedAt").descending()))
                .map(CommentDto::new);
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

    public void deleteCommentAdmin(int id) {
        Comment comment = commentRepository.findById(id).orElse(null);
        commentRepository.delete(comment);
    }
}
