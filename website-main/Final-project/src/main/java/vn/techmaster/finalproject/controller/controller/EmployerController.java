package vn.techmaster.finalproject.controller.controller;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.techmaster.finalproject.model.entity.CompanyType;
import vn.techmaster.finalproject.model.entity.Employer;
import vn.techmaster.finalproject.repository.EmployerRepo;
import vn.techmaster.finalproject.request.ChangeInfoEmployerRequest;
import vn.techmaster.finalproject.security.UserDetailCustom;
import vn.techmaster.finalproject.service.serviceinterface.CityService;

import java.util.Optional;
@Controller
@AllArgsConstructor
public class EmployerController {
    private EmployerRepo employerRepo;

    private CityService cityService;

    @GetMapping("/employer-infor")
    public String employer(Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Employer> employer = employerRepo.findById(user.getUser().getId());
        model.addAttribute("website",employer.get().getWebsite());
        model.addAttribute("employer",employer.get().getUser());
        model.addAttribute("employerMore",employer.get());
        return "company/thong-tin-nha-tuyen-dung";
    }


    @GetMapping("/change-info")
    public String changeInfo(Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var employer = employerRepo.findById(user.getUser().getId()).get();
        model.addAttribute("changeInfo", new ChangeInfoEmployerRequest(employer.getUser().getName(),employer.getCompanyName(),employer.getWebsite(),employer.getUser().getCity(),employer.getHotline(),employer.getEmployeeSize(),employer.getAddressDetail()));
        model.addAttribute("citycodes", cityService.getCity());
        return  "company/thay-doi-thong-tin-cong-ty";
    }
    @PostMapping("/saving-change")
    public String saveChange(@ModelAttribute("changeInfo") ChangeInfoEmployerRequest changeInfoEmployerRequest, Model model , BindingResult bindingResult){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var employer = employerRepo.findById(user.getUser().getId()).get();
        if(bindingResult.hasErrors()){
            model.addAttribute("citycodes", cityService.getCity());
            return "company/thay-doi-thong-tin-cong-ty";
        }
        employer.getUser().setName(changeInfoEmployerRequest.name());
        employer.setCompanyName(changeInfoEmployerRequest.companyName());
        employer.setWebsite(changeInfoEmployerRequest.website());
        employer.getUser().setCity(changeInfoEmployerRequest.city());
        employer.setHotline(changeInfoEmployerRequest.phoneNumber());
        employer.setEmployeeSize(changeInfoEmployerRequest.employeeSize());
        employer.setAddressDetail(changeInfoEmployerRequest.addressDetail());
        employerRepo.save(employer);
        return "redirect:/employer-infor";
    }

}
