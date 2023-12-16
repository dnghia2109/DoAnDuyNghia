package com.example.blog.service;

import com.example.blog.dto.UserDto;
import com.example.blog.dto.projection.UserPublic;
import com.example.blog.entity.*;
import com.example.blog.exception.BadRequestException;
import com.example.blog.exception.NotFoundException;
import com.example.blog.mapper.UserMapper;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.CreateUserRequest;
import com.example.blog.request.UpdateUserRequest;
import com.example.blog.security.ICurrentUser;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ICurrentUser iCurrentUser;
    private final RoleRepository roleRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;


    // Thay đổi avatar
    public String updateAvatar(MultipartFile file) {
        User user = iCurrentUser.getUser();

        // Upload file
        Image image = imageService.uploadFile(file);

        user.setAvatar("/api/v1/files/" + image.getId());
        userRepository.save(user);

        return user.getAvatar();
    }

    /*
    * @author: Lai Duy Nghia
    * @since: 26/10/2023 20:25
    * @description:
    * @update:
    *
    * */

    // TODO: Lấy ra danh sách user (admin)
    public Page<UserDto> getAllUser(Integer page, Integer pageSize) {
        Page<UserDto> pageInfo = userRepository.findAll(PageRequest.of(page - 1, pageSize, Sort.by("id").ascending())).map(UserDto::new);
        return pageInfo;
    }

    public UserDto getUserDetail(Integer id) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Không tìm thấy user có id: " + id);
        }
        User currentUser = userOptional.get();
        return new UserDto(currentUser);
    }

    // TODO: Tạo mới user
    public User createNewUser(CreateUserRequest request) {
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(roleRepository.findByIdIn(request.getRoleIds()));
        user.setEnabled(true);
        return userRepository.save(user);
    }


    // TODO: Cập nhật thông tin user (admin)
    public User updateUser(Integer id, UpdateUserRequest request) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("Không tìm thấy user có id: " + id);
        }
        User currentUser = userOptional.get();

        currentUser.setName(request.getName());
        currentUser.setPassword(passwordEncoder.encode(request.getPassword()));
        currentUser.setRoles(roleRepository.findByIdIn(request.getRoleIds()));
        //currentUser.setEnabled(true);
        return userRepository.save(currentUser);
    }

    // TODO: Xóa user
    public void deleteUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });
        userRepository.delete(user);
    }

    /*
    * @author: Lai Duy Nghia
    * @since: 21/11/2023 20:56
    * @description:  Người dùng cập nhật thông tin tài khoản
    * @update:
    *
    * */
    public UserDto updateProfile(UpdateUserRequest request) {
        User user = iCurrentUser.getUser();
        user.setName(request.getName());
        userRepository.save(user);
        return new UserDto(user);
    }

    public void sendComplexEmail() {
        Map<String, Object> model = new HashMap<>();
        model.put("name", "Nguyễn Minh Duy");
        model.put("content", "<p>This is a <strong>complex</strong> email with HTML content and CSS styling.</p>");

        mailService.sendEmailWithTemplate("duy@gmail.com", "Important Notification", model, "mail/feedback");
    }

}
