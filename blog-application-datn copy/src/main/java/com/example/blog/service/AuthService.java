package com.example.blog.service;

import com.example.blog.dto.UserDto;
import com.example.blog.entity.Role;
import com.example.blog.entity.TokenConfirm;
import com.example.blog.entity.User;
import com.example.blog.exception.BadRequestException;
import com.example.blog.exception.NotFoundException;
import com.example.blog.repository.RoleRepository;
import com.example.blog.repository.TokenConfirmRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.ChangePasswordRequest;
import com.example.blog.request.ForgotPasswordRequest;
import com.example.blog.request.LoginRequest;
import com.example.blog.request.RegisterRequest;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthService {
    private final MailService mailService;
    private final UserReceiveNewsService userReceiveNewsService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final TokenConfirmRepository tokenConfirmRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserDto register(RegisterRequest request) {
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (!user.getEnabled() && user.getName().equals(request.getName()) && user.getEmail().equals(request.getEmail())) {
                // Generate ra token va send mail
                // return link kích hoạt ở đây
                generateTokenAndSendMail(user);
                return new UserDto(user);
            }
            throw new BadRequestException("Tài khoản đã được kích hoạt!");
        }
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("Mật khẩu xác nhận không khớp nhau. Vui lòng kiểm tra lại!");
        }
        // Tạo user mới
        Role userRole = roleRepository.findByName("USER").orElse(null);
        User user = new User(request.getName(), request.getEmail(), passwordEncoder.encode(request.getPassword()), userRole);
        userRepository.save(user);
        generateTokenAndSendMail(user);

        return new UserDto(user);
    }

    private void generateTokenAndSendMail(User user) {
        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);

        int serverPort = userReceiveNewsService.getServerPort();

        String linkConfirm = "http://localhost:" + serverPort + "/auth/register/confirm/" + tokenConfirm.getToken();
        Map<String, Object> data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("username", user.getName());
        data.put("linkConfirm", linkConfirm);
//        mailService.sendMail(user.getEmail(), "Xác thực email đăng ký", linkConfirm);
        mailService.sendEmailConfirmAccountRegister(user.getEmail(), data);
    }


    public String login(LoginRequest request, HttpSession session) {
        // Tạo đối tượng xác thực
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                request.getEmail(),
                request.getPassword()
        );

        try {
            // Tiến hành xác thực
            Authentication authentication = authenticationManager.authenticate(token);

            // Lưu dữ liệu vào context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Tạo ra session
            session.setAttribute("MY_SESSION", authentication.getName());

            return "Đăng nhập thành công";
        } catch (Exception e) {
            throw new BadRequestException("Email/Password không chính xác!");
        }
    }

    public void forgotPassword(ForgotPasswordRequest request) {
        // Kiểm tra email gửi lên có tồn tại trong db hay không
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> {
                    throw new NotFoundException("Tài khoản không tồn tại!");
                });

        // Tạo ra token -> lưu vào cơ sở dữ liệu
        // Token là chuỗi UUID
        TokenConfirm tokenConfirm = TokenConfirm.builder()
                .token(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .expiredAt(LocalDateTime.now().plusMinutes(30))
                .user(user)
                .build();
        tokenConfirmRepository.save(tokenConfirm);
        int serverPort = userReceiveNewsService.getServerPort();

        String linkConfirm = "http://localhost:" + serverPort + "/auth/change-password/" + tokenConfirm.getToken();
        Map<String, Object> data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("username", user.getName());
        data.put("linkConfirm", linkConfirm);
        mailService.sendEmailChangePassword(user.getName(), data);
    }

    public void changePassword(ChangePasswordRequest request) {
        // Kiểm tra token
        TokenConfirm tokenConfirm = tokenConfirmRepository.findByToken(request.getToken())
                .orElseThrow(() -> {
                    throw new NotFoundException("Token không hợp lệ!");
                });

        // Kiểm tra password và confirm password
        if(!request.getNewPassword().equals(request.getConfirmPassword())) {
            throw new BadRequestException("NewPassword và ConfirmPassword không giống nhau");
        }

        // Lấy thông tin của user
        User user = tokenConfirm.getUser();
        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }

    // Hủy token nếu user không gửi yêu cầu đặt lại mật khẩu
//    public void userConfirmNotSendChangePassword(String token) {
//        TokenConfirm tokenConfirm = tokenConfirmRepository.findByToken(token)
//                .orElseThrow(() -> {
//                    throw new NotFoundException("Token không hợp l!");
//                });
//        tokenConfirmRepository.delete(tokenConfirm);
//    }

}
