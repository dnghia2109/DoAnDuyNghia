package com.example.blog.repository;

import com.example.blog.constant.EFeedbackStatus;
import com.example.blog.entity.UserFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeedbackRepository extends JpaRepository<UserFeedback, Integer> {

    @Query("select uf from UserFeedback uf where uf.status = ?1")
    Page<UserFeedback> findAllByStatus(EFeedbackStatus status, Pageable pageable);
}
