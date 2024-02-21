package com.example.blog.repository;

import com.example.blog.entity.UserReceiveNews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserReceiveNewsRepository extends JpaRepository<UserReceiveNews, Integer> {
    Optional<UserReceiveNews> findByEmail(String email);
}
