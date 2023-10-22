package com.example.blog.controller;

import com.example.blog.dto.UserDto;
import com.example.blog.dto.projection.BlogPublic;
import com.example.blog.dto.projection.RolePublic;
import com.example.blog.dto.projection.UserPublic;
import com.example.blog.entity.Image;
import com.example.blog.entity.Role;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.CreateUserRequest;
import com.example.blog.request.UpdateUserRequest;
import com.example.blog.service.RoleService;
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

@Controller
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    private UserRepository userRepository;

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
        UserPublic user = userService.getUserById(id);

        model.addAttribute("roleList", roleList);
        model.addAttribute("user", user);

        return "admin/user/user-detail";
    }

    // Danh sách API
    // Tạo user mới
    @PostMapping("api/v1/admin/users")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest request) {
        UserPublic user = userService.createUser(request);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    // Cập nhật thông tin user
    @PutMapping("api/v1/admin/users/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UpdateUserRequest request) {
        UserPublic user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }

    // Upload avatar
    @PostMapping("api/v1/admin/users/{id}/upload-avatar")
    public ResponseEntity<?> uploadAvatar(@ModelAttribute("file") MultipartFile file, @PathVariable Integer id) {
        Image image = userService.uploadAvatar(id, file);
        return ResponseEntity.ok(image);
    }

    @DeleteMapping("api/v1/admin/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        userRepository.delete(userRepository.findById(id).get());
        return ResponseEntity.ok("Đã xóa");
    }
}
