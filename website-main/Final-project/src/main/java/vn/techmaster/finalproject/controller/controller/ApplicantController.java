package vn.techmaster.finalproject.controller.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import vn.techmaster.finalproject.model.entity.Applicant;
import vn.techmaster.finalproject.repository.ApplicantRepo;
import vn.techmaster.finalproject.request.ChangeInfoEmployerRequest;
import vn.techmaster.finalproject.request.ApplicantChangeInfoRequest;
import vn.techmaster.finalproject.security.UserDetailCustom;
import vn.techmaster.finalproject.service.serviceinterface.ApplicantService;
import vn.techmaster.finalproject.service.serviceinterface.CityService;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class ApplicantController {
    private ApplicantService applicantService;

    private ApplicantRepo applicantRepo;

    private CityService cityService;

    @GetMapping("/apply-list-for-applicant")
    public String appliedListDisplay(Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var applicants= applicantService.findAllApplyByUserId(user.getUser().getId());
        model.addAttribute("applicants",applicants);
        return "applicant/danh-sach-cong-viec-ung-tuyen";
    }

    @GetMapping("/save-list-for-applicant")
    public String saveListDisplay(Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var applicants= applicantService.findAllLikeByUserId(user.getUser().getId());
        String user_id =user.getUser().getId();
        model.addAttribute("applicants",applicants);
        model.addAttribute("user_id",user_id);
        return "applicant/danh-sach-cong-viec-da-luu";
    }
    @GetMapping("/applicant-info")
    public String applicantInfo(Model model){
        model.addAttribute("applicant","");
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Applicant> applicant = applicantRepo.findById(user.getUser().getId());
        model.addAttribute("applicant",applicant.get());
        model.addAttribute("user",applicant.get().getUser());
        return "applicant/thong-tin-nguoi-ung-tuyen";
    }

    @GetMapping("/change-info-applicant")
    public String changeInfoApplicant(Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var userId = user.getUser().getId();
        var applicant = applicantRepo.findById(userId).get();
        model.addAttribute("changeInfo",new ApplicantChangeInfoRequest(userId,applicant.getUser().getName(), applicant.getUser().getCity(), applicant.getPhone(),applicant.getSkills()));
        model.addAttribute("citycodes", cityService.getCity());
        return "applicant/thay-doi-thong-tin-nguoi-dung";
    }
    @PostMapping("/saving-change-applicant")
    public String saveChange(@ModelAttribute("changeInfo") ApplicantChangeInfoRequest applicantChangeInfoRequest, Model model , BindingResult bindingResult) {
        var applicant = applicantRepo.findById(applicantChangeInfoRequest.user_id()).get();
        if (bindingResult.hasErrors()) {
            model.addAttribute("citycodes", cityService.getCity());
            return "company/thay-doi-thong-tin-cong-ty";
        }
        applicant.getUser().setName(applicantChangeInfoRequest.name());
        applicant.getUser().setCity(applicantChangeInfoRequest.city());
        applicant.setPhone(applicantChangeInfoRequest.phone());
        applicant.setSkills(applicantChangeInfoRequest.skills());
        applicantRepo.save(applicant);
        return "redirect:/change-info-applicant";
    }
}
