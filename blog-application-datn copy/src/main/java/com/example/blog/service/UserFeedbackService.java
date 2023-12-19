package com.example.blog.service;

import com.example.blog.constant.EFeedbackStatus;
import com.example.blog.entity.UserFeedback;
import com.example.blog.exception.BadRequestException;
import com.example.blog.repository.UserFeedbackRepository;
import com.example.blog.repository.UserRepository;
import com.example.blog.request.UserFeedbackRequest;
import com.example.blog.security.ICurrentUser;
import com.example.blog.security.ICurrentUserImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class UserFeedbackService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ICurrentUser iCurrentUser;

    @Autowired
    private UserFeedbackRepository userFeedbackRepository;

    @Autowired
    private MailService mailService;

    public UserFeedback create(UserFeedbackRequest userFeedbackRequest) {
        UserFeedback userFeedback = new UserFeedback();
        if (userFeedbackRequest.getUsername().isEmpty()) {
            throw new BadRequestException("Bạn cần nhập vào tên");
        }

        if (userFeedbackRequest.getEmail().isEmpty()) {
            throw new BadRequestException("Bạn cần nhập vào email");
        }

        if (userFeedbackRequest.getContent().isEmpty()) {
            throw new BadRequestException("Bạn cần để lại ý kiến góp ý, nhận xét");
        }
        userFeedback.setUsername(userFeedbackRequest.getUsername());
        userFeedback.setEmail(userFeedbackRequest.getEmail());
        userFeedback.setContent(userFeedbackRequest.getContent());
        userFeedback.setStatus(EFeedbackStatus.NOT_REPLY);
        userFeedbackRepository.save(userFeedback);
        return userFeedback;
    }

    public UserFeedback replyFeedback(Integer id, UserFeedbackRequest userFeedbackRequest) {
        Optional<UserFeedback> optionalUserFeedback = userFeedbackRepository.findById(id);
        if (!optionalUserFeedback.isPresent()) {
            throw new BadRequestException("Không tìm thấy feedback có id - " + id);
        }
        UserFeedback userFeedback = optionalUserFeedback.get();

        if (userFeedbackRequest.getContentReply().isEmpty()) {
            throw new BadRequestException("Bạn cần nhập nội dung trả lời");
        }
        userFeedback.setContentReply(userFeedbackRequest.getContentReply());
        userFeedback.setUserReply(iCurrentUser.getUser().getEmail());
        userFeedback.setStatus(EFeedbackStatus.REPLY);
        userFeedbackRepository.save(userFeedback);

        Map<String, Object> data = new HashMap<>();
        data.put("username", userFeedback.getUsername());
        data.put("contentReply", userFeedbackRequest.getContentReply());
        mailService.sendEmailReplyUserFeedback(userFeedback.getEmail(), data);

        return userFeedback;
    }

    public Page<UserFeedback> getAllUserFeedbackByStatus(EFeedbackStatus status, Integer page, Integer pageSize) {
        Page<UserFeedback> result = userFeedbackRepository.
                findAllByStatus(status, PageRequest.of(page - 1, pageSize, Sort.by("id").descending()));
        return result;
    }

    public void delete(Integer id) {
        Optional<UserFeedback> optionalUserFeedback = userFeedbackRepository.findById(id);
        if (optionalUserFeedback.isPresent()) {
            throw new BadRequestException("Không tìm thấy feedback có id - " + id);
        }
        UserFeedback userFeedback = optionalUserFeedback.get();
        userFeedbackRepository.delete(userFeedback);
    }

}
