package com.example.blog.service;

import com.example.blog.dto.BlogDto;
import com.example.blog.dto.BlogSendMailDto;
import com.example.blog.entity.UserReceiveNews;
import com.example.blog.exception.BadRequestException;
import com.example.blog.repository.UserReceiveNewsRepository;
import com.example.blog.request.UserReceiveNewsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UserReceiveNewsService {

    @Autowired
    private UserReceiveNewsRepository userReceiveNewsRepository;
    @Autowired
    private BlogService blogService;
    @Autowired
    private MailService mailService;
    @Value("${server.port}")
    private int serverPort;


    // TODO: Đăng ký nhân mail
    @Transactional
    public UserReceiveNews subcribe(UserReceiveNewsRequest request) {
        UserReceiveNews userReceiveNews = new UserReceiveNews();
        userReceiveNews.setEmail(request.getEmail());

        userReceiveNewsRepository.save(userReceiveNews);
        return userReceiveNews;
    }

    // TODO: Hủy nhận mail
    public String unsubcribe(String email) {
        Optional<UserReceiveNews> userReceiveNewsOptional = userReceiveNewsRepository.findByEmail(email);
        if (userReceiveNewsOptional.isPresent()) {
            UserReceiveNews userReceiveNews = userReceiveNewsOptional.get();
            userReceiveNewsRepository.delete(userReceiveNews);
        } else {
            throw new BadRequestException("Not found email: " + email);
        }
        return "Hủy đăng ký nhận tin thành công.";
    }

    // TODO: Tự động gửi mail
//    @Scheduled(fixedRate = 6100000)
//    @Async
//    public void autoSendMail() {
//        List<UserReceiveNews> userReceiveNewsList = userReceiveNewsRepository.findAll();
//        List<String> listURLs = new ArrayList<>();
//        List<BlogSendMailDto> blogDtoList1 = blogService.getTop5NewestBlogss();
//        for (BlogSendMailDto blog : blogDtoList1) {
////            "/api/blogs/{blogId}/{blogSlug}"
//            String url = "Blog title - " + blog.getTitle() + "\n localhost:" + serverPort +"/api/blogs/" + blog.getId() + "/" + blog.getSlug() +"\n";
//            listURLs.add(url);
//        }
//
//        for (UserReceiveNews userReceiveNews : userReceiveNewsList) {
//            mailService.sendMail(userReceiveNews.getEmail(), "Danh sách bài viết mới" ,listURLs.toString());
//            System.out.println("=============");
//            System.out.println(userReceiveNews.getEmail() + listURLs);
//            log.info("User - {}", userReceiveNews.getEmail());
//            System.out.println("=============");
//        }
////        System.out.println("=============");
////        System.out.println("This is scheduled");
////        System.out.println("=============");
//    }

}
