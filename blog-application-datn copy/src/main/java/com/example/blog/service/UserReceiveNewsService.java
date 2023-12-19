package com.example.blog.service;

import com.example.blog.constant.EReceiveNewsState;
import com.example.blog.dto.BlogDto;
import com.example.blog.dto.BlogSendMailDto;
import com.example.blog.entity.UserReceiveNews;
import com.example.blog.exception.BadRequestException;
import com.example.blog.exception.NotFoundException;
import com.example.blog.repository.UserReceiveNewsRepository;
import com.example.blog.request.ReceiveNewsRequest;
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

    public int getServerPort() {
        return serverPort;
    }

    // TODO: Đăng ký nhân mail
    @Transactional
    public UserReceiveNews subcribe(ReceiveNewsRequest request) {
        Optional<UserReceiveNews> userReceiveNewsOptional = userReceiveNewsRepository.findByEmail(request.getEmail());
        // check email đã đki hay chưa:
        // case 1 : email đã đki và đag có trạng thái là delay thì set lại active
        // case 2 : email đã đki và đag có trạng thái là avtive thì ném message lỗi đã tồn tại
        if (userReceiveNewsOptional.isPresent()) {
            UserReceiveNews userReceiveNews = userReceiveNewsOptional.get();
            if ((userReceiveNews.getStatus() == EReceiveNewsState.DELAY_30_DAYS) || (userReceiveNews.getStatus() == EReceiveNewsState.DELAY_UNTIL_TURN_ON)) {
                userReceiveNews.setStatus(EReceiveNewsState.ACTIVE);
                return userReceiveNewsRepository.save(userReceiveNews);
            } else {
                throw new BadRequestException("Email - " + request.getEmail() + " has already subcribe to received news. Please use another email address");
            }
        }
        UserReceiveNews userReceiveNews = new UserReceiveNews();
        userReceiveNews.setEmail(request.getEmail());
        userReceiveNewsRepository.save(userReceiveNews);
        return userReceiveNews;
    }

    // TODO: Hủy nhận mail
    public String unSubcribe(String email) {
        Optional<UserReceiveNews> userReceiveNewsOptional = userReceiveNewsRepository.findByEmail(email);
        if (userReceiveNewsOptional.isPresent()) {
            UserReceiveNews userReceiveNews = userReceiveNewsOptional.get();
            userReceiveNewsRepository.delete(userReceiveNews);
        } else {
            throw new BadRequestException("Not found email: " + email);
        }
        return "Hủy đăng ký nhận tin thành công.";
    }

    // TODO: Cập nhật trạng thái tần suất nhận mail
    public UserReceiveNews updateStatusReceiveNews(String email, EReceiveNewsState status) {
        Optional<UserReceiveNews> userReceiveNewsOptional = userReceiveNewsRepository.findByEmail(email);
        if (userReceiveNewsOptional.isPresent()) {
            UserReceiveNews userReceiveNews = userReceiveNewsOptional.get();
            userReceiveNews.setStatus(status);
            userReceiveNewsRepository.save(userReceiveNews);
        } else {
            throw new NotFoundException("Email chưa đăng ký dịch vụ nhận tin tức.");
        }
        return userReceiveNewsOptional.get();
    }

//    // TODO: Tự động gửi mail
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
//
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
