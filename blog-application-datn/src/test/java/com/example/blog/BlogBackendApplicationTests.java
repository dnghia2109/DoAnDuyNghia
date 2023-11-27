package com.example.blog;

import com.example.blog.constant.EApprovalStatus;
import com.example.blog.entity.*;
import com.example.blog.repository.*;
import com.github.javafaker.Faker;
import com.github.slugify.Slugify;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@SpringBootTest
class BlogBackendApplicationTests {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BlogRepository blogRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Faker faker;

    @Test
    //@Rollback
    void test1() {
        User user = userRepository.findById(1).get();
        user.setId(11);
        user.setEmail("nghia1@gmail.com");
        userRepository.save(user);
        System.out.println("========");
        //System.out.println(user.getEmail());
        System.out.println(user.getId());
    }

    @Test
    void save_roles() {
        List<Role> roles = List.of(
                new Role(null, "ADMIN"),
                new Role(null, "USER"),
                new Role(null, "AUTHOR")
        );

        roleRepository.saveAll(roles);
    }

    @Test
    void save_users() {
        Role userRole = roleRepository.findByName("USER").orElse(null);
        Role adminRole = roleRepository.findByName("ADMIN").orElse(null);
        Role authorRole = roleRepository.findByName("AUTHOR").orElse(null);

//        List<User> users = List.of(
//                new User(null, "Bùi Hiên", "hien@gmail.com", passwordEncoder.encode("111"), null, List.of(adminRole, userRole)),
//                new User(null, "Minh Duy", "duy@gmail.com", passwordEncoder.encode("111"), null, List.of(userRole)),
//                new User(null, "Thu Hằng", "hang@gmail.com",passwordEncoder.encode("111"), null, List.of(authorRole, userRole))
//        );

        User user1 = User.builder()
                .name("Duy Nghĩa")
                .email("nghia@gmail.com")
                .password(passwordEncoder.encode("111"))
                .roles(List.of(adminRole, authorRole))
                .build();

        User user2 = User.builder()
                .name("Bùi Hiên")
                .email("hien@gmail.com")
                .password(passwordEncoder.encode("111"))
                .roles(List.of(adminRole, authorRole))
                .build();

        User user3 = User.builder()
                .name("Minh Duy")
                .email("duy@gmail.com")
                .password(passwordEncoder.encode("111"))
                .roles(List.of(userRole))
                .build();

        User user4 = User.builder()
                .name("Thu Hằng")
                .email("hang@gmail.com")
                .password(passwordEncoder.encode("111"))
                .roles(List.of(authorRole, userRole))
                .build();

//        userRepository.saveAll(users);
        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);
        userRepository.save(user4);
    }

    @Test
    void save_categories() {
        List<Category> categories = List.of(
                new Category(null, "Thời sự"),
                new Category(null, "Xã hội"),
                new Category(null, "Thế giới"),
                new Category(null, "Kinh tế"),
                new Category(null, "Thể thao"),
                new Category(null, "Văn hóa"),
                new Category(null, "Sức khỏe"),
                new Category(null, "Pháp luật"),
                new Category(null, "Giáo dục"),
                new Category(null, "Xe"),
                new Category(null, "Du lịch"),
                new Category(null, "Công nghệ")
        );
        categoryRepository.saveAll(categories);

    }

    @Test
    void save_blogs() {
        Slugify slugify = Slugify.builder().build();
        Random rd = new Random();

        List<User> userList = userRepository.findByRoles_NameIn(List.of("ADMIN", "AUTHOR"));
        List<Category> categoryList = categoryRepository.findAll();

        for (int i = 21; i <= 40; i++) {
            // Random 1 user
            User rdUser = userList.get(rd.nextInt(userList.size()));

            // Tao blog
            Blog blog = Blog.builder()
                    .title("Blog " + (i + 1))
                    .slug(slugify.slugify("Blog " + (i + 1)))
                    .description("description " + (i + 1))
                    .content(faker.lorem().sentence(1000))
                    .status(rd.nextInt(2) == 0)
                    .user(rdUser)
                    .approvalStatus(EApprovalStatus.APPROVE)
                    .category(categoryList.get(rd.nextInt(12)))
                    .build();
            blogRepository.save(blog);
        }
    }

    @Test
    void save_comments() {
        Random rd = new Random();
        List<User> userList = userRepository.findAll();
        List<Blog> blogList = blogRepository.findAll();

        for (int i = 0; i < 100; i++) {
            // Random 1 user
            User rdUser = userList.get(rd.nextInt(userList.size()));

            // Random 1 blog
            Blog rdBlog = blogList.get(rd.nextInt(blogList.size()));

            // Tao comment
            Comment comment = Comment.builder()
                    .content("comment " + (i + 1))
                    .user(rdUser)
                    .blog(rdBlog)
                    .build();

            commentRepository.save(comment);
        }
    }

}
