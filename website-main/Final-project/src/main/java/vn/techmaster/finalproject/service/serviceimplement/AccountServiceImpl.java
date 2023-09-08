package vn.techmaster.finalproject.service.serviceimplement;

import lombok.AllArgsConstructor;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.exception.NotFoundException;
import vn.techmaster.finalproject.model.entity.*;

import vn.techmaster.finalproject.repository.ApplicantRepo;
import vn.techmaster.finalproject.repository.AuthorizeRepository;
import vn.techmaster.finalproject.repository.EmployerRepo;
import vn.techmaster.finalproject.repository.UserRepository;
import vn.techmaster.finalproject.service.serviceinterface.MailService;
import vn.techmaster.finalproject.service.serviceinterface.AccountService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {

    private ApplicantRepo applicantRepository;
    private EmployerRepo employerRepo;
    private MailService mailService;
    private AuthorizeRepository authorizeRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public void registerApplicant(String firstName, String lastName, String phoneNumber, String email, String password, String city , List<Skill> skills) {
        String user_id = UUID.randomUUID().toString();
        var user = User.builder().id(user_id).name(firstName+" "+lastName).email(email).password(passwordEncoder.encode(password)).timeCreate(LocalDateTime.now()).state(State.PENDING).city(city).role(RoleEnum.APPLICANT).build();
        var applicant = Applicant.builder().user(user).phone(phoneNumber).ranking(Ranking.FREE).skills(skills).build();
        applicantRepository.save(applicant);
        String token_code= renderToken(user_id);
        String url = "http://localhost:8089/validate?token="+token_code;
        mailService.sendEmail(email,"Xác nhận tài khoản của bạn tại Job hunt","Vui lòng nhấp vào link này để xác thực tài khoản của bạn : "+url);
    }
    @Override
    public void registerCompany(String firstName, String lastName, String companyName, String website, String city, String phoneNumber, String email, String password) {
        String user_id = UUID.randomUUID().toString();
        var user = User.builder().id(user_id).name(firstName+" "+lastName).city(city).email(email).password(passwordEncoder.encode(password)).state(State.PENDING).timeCreate(LocalDateTime.now()).role(RoleEnum.EMPLOEYER).build();
        var company = Employer.builder().companyName(companyName).hotline(phoneNumber).website(website).user(user).build();
        employerRepo.save(company);
        String token_code= renderToken(user_id);
        String url = "http://localhost:8089/validate?token="+token_code;
        mailService.sendEmail(email,"Xác nhận tài khoản của bạn tại Job hunt","Vui lòng nhấp vào link này để xác thực tài khoản của bạn : "+url);
    }

    @Override
    public String validateUser(String token){
        String userID = validateToken(token);
        activeUser(userID);
        return userID;
    }

    @Override
    public void forgotPass(String email) {
        User user = userRepository.findByEmail(email);
        String userId = user.getId();
        String token_code= renderToken(userId);
        mailService.sendEmail(email,"Đổi mật khẩu","Chào bạn ! \n" +
                                                              "Token của bạn là : "+token_code);
    }

    @Override
    public void changePass(String token, String password) {
       String userId = validateToken(token);
       Optional<User> user = userRepository.findById(userId);
        user.orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này"));
        user.get().setPassword(passwordEncoder.encode(password));
        userRepository.save(user.get());
    }

    @Override
    public void changePassManual(String oldPass, String NewPass, String userId) {
        var user = userRepository.findById(userId).get();
        if(passwordEncoder.matches(oldPass,user.getPassword())){
            user.setPassword(passwordEncoder.encode(NewPass));
            userRepository.save(user);
        }else {
            throw new NotFoundException("Mật khẩu cũ không đúng");
        }
    }

    public String renderToken (String userId){
        String token = UUID.randomUUID().toString();
        authorizeRepository.save(Authorize.builder()
                .id(token)
                .userId(userId)
                .timeCreate(LocalDateTime.now())
                .timeExp(LocalDateTime.now().plusMinutes(15))
                .active(false)
                .build());

        return token;
    }
    public void activeUser(String user_id){
        Optional<User> user = userRepository.findById(user_id);
        user.orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này"));
        user.get().setState(State.ACTIVE);
        user.get().setTimeUpdate(LocalDateTime.now());
        userRepository.save(user.get());
    }


    public String validateToken(String token){
        Optional<Authorize> user_in_token = authorizeRepository.findById(token);
        user_in_token.orElseThrow(() -> new NotFoundException("Không tồn tại token này"));
        String userId = user_in_token.get().getUserId();
        if(user_in_token.get().isActive()){
            throw new IllegalStateException("Bạn đã xác nhận");
        }
        if(user_in_token.get().getTimeExp().isBefore(LocalDateTime.now())){
            throw new IllegalStateException("Bạn đã quá hạn để xác nhận");
        }
        user_in_token.get().setActive(true);
        authorizeRepository.save(user_in_token.get());
        return userId;
    }

}
