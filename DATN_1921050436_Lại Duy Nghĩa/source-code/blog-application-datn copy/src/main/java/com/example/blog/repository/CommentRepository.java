package com.example.blog.repository;

import com.example.blog.dto.CommentDto;
import com.example.blog.dto.projection.CommentPublic;
import com.example.blog.entity.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Query(
            value = "select c from Comment c"
    )
    Page<CommentPublic> findComments(Pageable pageable);

    @Query(value = "select c from Comment c")
    Page<Comment> getAllCommentsAdminAPI(Pageable pageable);
}