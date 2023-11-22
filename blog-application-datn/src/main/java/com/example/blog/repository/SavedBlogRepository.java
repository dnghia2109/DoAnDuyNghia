package com.example.blog.repository;

import com.example.blog.entity.Blog;
import com.example.blog.entity.SavedBlog;
import com.example.blog.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SavedBlogRepository extends JpaRepository<SavedBlog, Integer> {
    @Query("select sb from SavedBlog sb where sb.user = ?1 and sb.blog.status = true and sb.blog.approvalStatus = 'APPROVE'")
    Page<SavedBlog> getAllSavedBlogs(Pageable pageable, User user);

    @Query("select sb from SavedBlog sb where sb.blog.id = ?1 and sb.user = ?2")
    Optional<SavedBlog> findByIdBlog(Integer savedBlogId, User user);

    Optional<SavedBlog> findByBlogAndUser(Blog blog, User user);

}
