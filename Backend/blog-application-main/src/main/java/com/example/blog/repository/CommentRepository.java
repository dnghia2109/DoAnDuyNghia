package com.example.blog.repository;

import com.example.blog.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    @Override
    List<Comment> findAll();

    List<Comment> findByBlog_Id(Integer id);

}