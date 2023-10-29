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
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ImageService imageService;
    private final PasswordEncoder passwordEncoder;



    // Lấy chi tiết user theo id
    public UserPublic getUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        return UserPublic.of(user);
    }

    // Tạo user
    public UserPublic createUser(CreateUserRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email = " + request.getEmail() + " is existed");
        }

        // List Role
        List<Role> roleList = roleRepository.findByIdIn(request.getRoleIds());

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(roleList)
                .build();

        userRepository.save(user);
        return UserPublic.of(user);
    }

    // Cập nhật thông tin user
    public UserPublic updateUser(Integer id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        // List Role
        List<Role> roleList = roleRepository.findByIdIn(request.getRoleIds());

        user.setName(request.getName());
        user.setRoles(roleList);

        userRepository.save(user);
        return UserPublic.of(user);
    }

    // Thay đổi avatar
    public Image uploadAvatar(Integer id, MultipartFile file) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            throw new NotFoundException("Not found user with id = " + id);
        });

        // Upload file
        Image image = imageService.uploadFile(file);

        user.setAvatar("/api/v1/files/" + image.getId());
        userRepository.save(user);

        return image;
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
    public User updateInfoUser(Integer id, UpdateUserRequest request) {
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

}
