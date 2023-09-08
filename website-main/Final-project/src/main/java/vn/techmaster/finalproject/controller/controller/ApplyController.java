package vn.techmaster.finalproject.controller.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.techmaster.finalproject.model.entity.FileType;
import vn.techmaster.finalproject.request.UploadCVRequest;
import vn.techmaster.finalproject.security.UserDetailCustom;
import vn.techmaster.finalproject.service.serviceinterface.ApplicantService;
import vn.techmaster.finalproject.service.serviceinterface.EmployerService;
import vn.techmaster.finalproject.service.serviceinterface.FileService;

@Controller
@AllArgsConstructor
public class ApplyController {
    private ApplicantService applicantService;
    private FileService fileService;

    private EmployerService employerService;

    @GetMapping  ("/upload-cv")
    public String uploadCv(@RequestParam String job_id, Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getUser().getId();
        String hide;
        var files = applicantService.findAllCv(userId);
        if(files.size()==0){
            hide = "0";
        }else{
            hide = "1";
        }
        model.addAttribute("cv",new UploadCVRequest(job_id,null));
        model.addAttribute("files",files);
        model.addAttribute("user_id",userId);
        model.addAttribute("hide",hide);
        return "applicant-apply-job/upload-cv";
    }
    @GetMapping("/apply-by-cur-file")
    public String applyByFile(@RequestParam String file_path, @RequestParam String job_id){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getUser().getId();
            applicantService.applyJob(userId,job_id,file_path);
        return "redirect:/job-detail?job_id="+job_id;
    }
    @PostMapping("/applied-job")
    public String savingFileCV(@ModelAttribute("cv")UploadCVRequest uploadCVRequest, BindingResult bindingResult,Model model){
        if(bindingResult.hasErrors()){
            return "applicant-apply-job/upload-cv";
        }
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getUser().getId();
        String filePath = fileService.uploadFile(userId, uploadCVRequest.cv(), FileType.PDF);
        applicantService.applyJob(userId,uploadCVRequest.jobID(),filePath);
        return "redirect:/job-detail?job_id="+uploadCVRequest.jobID();
    }
    @GetMapping("/like-job")
    public String likeJob(@RequestParam String job_id){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getUser().getId();
        applicantService.loveJob(userId,job_id);
        return "redirect:/job-detail?job_id="+job_id;
    }

    @GetMapping("/accept-applicant")
    public String acceptApplicant(@RequestParam String applicant_id,@RequestParam String job_id){
        employerService.acceptApplicant(applicant_id,job_id);
        return "redirect:/apply-list";
    }

    @GetMapping("/reject-applicant")
    public String rejectApplicant(@RequestParam String applicant_id,@RequestParam String job_id){
        employerService.rejectApplicant(applicant_id,job_id);
        return "redirect:/apply-list";
    }
}
