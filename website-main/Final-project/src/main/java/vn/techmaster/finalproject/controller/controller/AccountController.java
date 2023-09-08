package vn.techmaster.finalproject.controller.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.techmaster.finalproject.model.entity.Authorize;
import vn.techmaster.finalproject.model.entity.Employer;
import vn.techmaster.finalproject.model.entity.FileType;
import vn.techmaster.finalproject.model.entity.User;
import vn.techmaster.finalproject.repository.AuthorizeRepository;
import vn.techmaster.finalproject.repository.UserRepository;
import vn.techmaster.finalproject.request.*;
import vn.techmaster.finalproject.security.UserDetailCustom;
import vn.techmaster.finalproject.service.serviceimplement.EmailValidServiceImpl;
import vn.techmaster.finalproject.service.serviceinterface.AccountService;
import vn.techmaster.finalproject.service.serviceinterface.CityService;
import vn.techmaster.finalproject.service.serviceinterface.FileService;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class AccountController {
    private AccountService accountService;
    private EmailValidServiceImpl emailValidServiceImpl;
    private CityService cityService;
    private FileService fileService;
    private AuthorizeRepository authorizeRepository;

    //Chức năng đăng kí tài khoản
    @GetMapping("/register-employee")
    public String register(Model model ){
        model.addAttribute("register",new EmployeeRegisterRequest("","","","",null,"","",""));
        model.addAttribute("citycodes", cityService.getCity());
        return "account/register/employee-register";
    }

    @PostMapping("/saving-user-employee")
    public String saving(Model model,@Valid @ModelAttribute("register") EmployeeRegisterRequest employeeRequest, BindingResult result){
        if(!emailValidServiceImpl.test(employeeRequest.email())){
            result.addError(new FieldError("register", "email", "Email không tồn tại !"));
            model.addAttribute("citycodes", cityService.getCity());
            return "account/register/employee-register";
        }

        if(!employeeRequest.password().equals(employeeRequest.cfPassword())){
            result.addError(new FieldError("register", "cfPassword", "Mật khẩu không giống nhau !"));
            model.addAttribute("citycodes", cityService.getCity());
            return "account/register/employee-register";
        }
        accountService.registerApplicant(employeeRequest.firstName(), employeeRequest.lastName(), employeeRequest.phoneNumber(), employeeRequest.email(), employeeRequest.password(),employeeRequest.city(),employeeRequest.skill());
        return "account/register/check-email";
    }

    @GetMapping("/register-employer")
    public String registerForEmployer(Model model){
        model.addAttribute("register",new CompanyRegisterRequest("","","","","","","","",""));
        model.addAttribute("citycodes", cityService.getCity());
        return "account/register/company-register-form";
    }

    @PostMapping("/saving-user-employer")
    public String savingEmployer(Model model,@Valid @ModelAttribute("register") CompanyRegisterRequest companyRegisterRequest, BindingResult result){
        if(!emailValidServiceImpl.test(companyRegisterRequest.email())){
            result.addError(new FieldError("register", "email", "Email không tồn tại !"));
            model.addAttribute("citycodes", cityService.getCity());
            return "account/register/company-register-form";
        }

        if(!companyRegisterRequest.password().equals(companyRegisterRequest.cfPassword())){
            result.addError(new FieldError("register", "cfPassword", "Mật khẩu không giống nhau !"));
            model.addAttribute("citycodes", cityService.getCity());
            return "account/register/company-register-form";
        }
        accountService.registerCompany(companyRegisterRequest.firstName(), companyRegisterRequest.lastName(), companyRegisterRequest.companyName(), companyRegisterRequest.website(), companyRegisterRequest.city(),companyRegisterRequest.phoneNumber(),companyRegisterRequest.email(),companyRegisterRequest.password());
        return "account/register/check-email";
    }

    @GetMapping("/validate")
    public String validate(@RequestParam String token, Model model){
        String userId = accountService.validateUser(token);
        model.addAttribute("avatar",new UploadAvatarRequest(userId,null));
        return "account/register/upload-avatar";
    }

    @PostMapping("/upload-avatar")
    public String uploadAvatar(@ModelAttribute("avatar")UploadAvatarRequest uploadAvatarRequest){
        fileService.uploadFile(uploadAvatarRequest.userID(), uploadAvatarRequest.avatar(), FileType.PHOTO);
        return "redirect:/";
    }

// Chức năng đăng nhập
    @GetMapping("/login")
    public String getLoginPage (Model model){
        return "account/login-page";
    }

// Chức năng lấy lại mật khẩu khi đã quên
    @GetMapping("/forgot")
    public String getForgotForm(){
        return "account/forgot/forgot-email-request";
    }

    @PostMapping(value = "/forgot-request")
    public String forgot(@ModelAttribute("email") String email , Model model) {
        model.addAttribute("email",email);
        accountService.forgotPass(email);
        return "redirect:/new-password";
    }

    @GetMapping("/new-password")
    public String newPass(Model model) {
        model.addAttribute("request",new ForgotPasswordRequest("","",""));
        return "account/forgot/quen-mat-khau";
    }

    @PostMapping(value = "/validate-forgot-user")
    public String changePass(@Valid @ModelAttribute("request") ForgotPasswordRequest forgotPasswordRequest, BindingResult result,
                             Model model){
        Optional<Authorize> user_in_token = authorizeRepository.findById(forgotPasswordRequest.token());
        if(user_in_token.isEmpty()){
            result.addError(new FieldError("request", "token", "Token không tồn tại"));
            return "account/forgot/quen-mat-khau";
        }
        if(!forgotPasswordRequest.password().equals(forgotPasswordRequest.cfPassword())){
            result.addError(new FieldError("request", "cfPassword", "Mật khẩu không trùng nhau"));
            return "account/forgot/quen-mat-khau";
        }
        accountService.changePass(forgotPasswordRequest.token(),forgotPasswordRequest.password());
        return "redirect:/";
    }
    // Chức năng quên mật khẩu
    @GetMapping("/change-password")
    public String changePassword (Model model){
        model.addAttribute("password", new ChangePasswordRequest("","","",""));
        return "account/doi-mat-khau";
    }

    @PostMapping("/saving-password")
    public String changePassword(@Valid @ModelAttribute("password") ChangePasswordRequest changePasswordRequest,BindingResult bindingResult,Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getUser().getId();
        if(bindingResult.hasErrors()){
            return "account/doi-mat-khau";
        }
        if(!changePasswordRequest.newPass().equals(changePasswordRequest.retypePass())){

            bindingResult.addError(new FieldError("password", "retypePass", "Mật khẩu không giống nhau !"));
            return "account/doi-mat-khau";
        }
        accountService.changePassManual(changePasswordRequest.oldPass(),changePasswordRequest.newPass(),userId);
        return "redirect:/";
    }
}
