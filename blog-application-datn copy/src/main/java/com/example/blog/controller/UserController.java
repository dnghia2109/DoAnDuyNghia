package com.example.blog.controller;

import com.example.blog.constant.EReceiveNewsState;
import com.example.blog.dto.UserDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.projection.RolePublic;
import com.example.blog.dto.projection.UserPublic;
import com.example.blog.entity.Image;
import com.example.blog.entity.Role;
import com.example.blog.entity.User;
import com.example.blog.entity.UserReceiveNews;
import com.example.blog.exception.BadRequestException;
import com.example.blog.repository.UserReceiveNewsRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.CreateUserRequest;
import com.example.blog.request.ReceiveNewsRequest;
import com.example.blog.request.UpdateUserRequest;
import com.example.blog.security.ICurrentUser;
import com.example.blog.service.RoleService;
import com.example.blog.service.UserReceiveNewsService;
import com.example.blog.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;
    private final UserReceiveNewsService userReceiveNewsService;
    private final ICurrentUser iCurrentUser;
    private final UserRepository userRepository;
    private final UserReceiveNewsRepository userReceiveNewsRepository;

    // Danh sách View
    @GetMapping("/dashboard/admin/users")
    public String getListUserPage(
            @RequestParam(required = false, defaultValue = "1") int page,
            @RequestParam(required = false, defaultValue = "10") int pageSize,
            Model model) {
        Page<UserDto> pageInfo = userService.getAllUser(page, pageSize);
        List<Role> roleList = roleService.getAllRole();

        model.addAttribute("page", pageInfo);
        model.addAttribute("currentPage", page);
        model.addAttribute("roleList", roleList);
        return "admin/user/user-index";
    }

    @GetMapping("/dashboard/admin/users/create")
    public String getCreateUserPage(Model model) {
        List<Role> roleList = roleService.getAllRole();
        model.addAttribute("roleList", roleList);

        return "admin/user/user-create";
    }

    @GetMapping("/dashboard/admin/users/{id}")
    public String getDetailUserPage(@PathVariable Integer id, Model model) {
        List<Role> roleList = roleService.getAllRole();
        UserDto user = userService.getUserDetail(id);

        model.addAttribute("roleList", roleList);
        model.addAttribute("user", user);

        return "admin/user/user-detail";
    }

    // Danh sách API
    // Tạo user mới
    @PostMapping("/api/v1/admin/users")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
//        UserPublic user = userService.createUser(request);
        User user = userService.createNewUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Cập nhật thông tin user
    @PutMapping("/api/v1/admin/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        User user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }

    // Upload avatar
    @PostMapping("/api/v1/admin/users/{id}/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@ModelAttribute("file") MultipartFile file, @PathVariable Integer id) {
        Image image = userService.uploadAvatar(id, file);
        return ResponseEntity.ok(image);
    }

    @DeleteMapping("/api/v1/admin/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userRepository.delete(userRepository.findById(id).get());
        return ResponseEntity.ok("Đã xóa");
    }
    
    // Người dùng cập nhật thông tin tài khoản
    @PutMapping("/api/v1/user")
    public ResponseEntity<?> updateUserProfile(@RequestBody UpdateUserRequest request) {
        UserDto user = userService.updateProfile(request);
        return ResponseEntity.ok(user);
    }

    // TODO: Người dùng đăng ký nhận tin tức qua email
    @PostMapping("/api/v1/user/subcribe-news")
    public ResponseEntity<?> subcribeReceiveNews(@RequestBody ReceiveNewsRequest request) {
        return ResponseEntity.ok(userReceiveNewsService.subcribe(request));
    }

    // TODO: Người dùng cập nhật thời gian tạm ngưng nhận tin tức qua mail
    @PutMapping("/api/v1/user/subcribe-news/30-days")
    public ResponseEntity<?> delayReceiveNewsFor30Days(@RequestBody ReceiveNewsRequest request) {
        return ResponseEntity.ok(userReceiveNewsService.updateStatusReceiveNews(request.getEmail(), EReceiveNewsState.DELAY_30_DAYS));
    }
    @PutMapping("/api/v1/user/subcribe-news/unknownDay")
    public ResponseEntity<?> delayReceiveNewsForUnknownDay(@RequestBody ReceiveNewsRequest request) {
        return ResponseEntity.ok(userReceiveNewsService.updateStatusReceiveNews(request.getEmail(), EReceiveNewsState.DELAY_UNTIL_TURN_ON));
    }


}
