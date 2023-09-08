package com.example.newsportal;

import com.example.newsportal.entity.Role;
import com.example.newsportal.repository.RoleRepository;
import com.example.newsportal.repository.UserRepository;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class NewsPortalApplicationTests {
    @Autowired
    private Faker faker;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;


    @Test
    void contextLoads() {
    }

    @Test
    void save_roles() {
        Role roleAdmin = Role.builder()
                .name("ADMIN")
                .build();

        Role roleAuthor = Role.builder()
                .name("AUTHOR")
                .build();

        Role roleUser = Role.builder()
                .name("User")
                .build();

        roleRepository.saveAll(List.of(roleAdmin, roleAuthor, roleUser));
    }
}
