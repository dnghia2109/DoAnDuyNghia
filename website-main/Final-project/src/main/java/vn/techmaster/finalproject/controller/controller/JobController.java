package vn.techmaster.finalproject.controller.controller;

import java.text.ParseException;
import java.util.UUID;
import javax.validation.Valid;

import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import vn.techmaster.finalproject.model.entity.*;
import vn.techmaster.finalproject.request.JobRequest;
import vn.techmaster.finalproject.security.UserDetailCustom;
import vn.techmaster.finalproject.service.serviceinterface.ApplicantService;
import vn.techmaster.finalproject.service.serviceinterface.CityService;
import vn.techmaster.finalproject.service.serviceinterface.HTMLService;
import vn.techmaster.finalproject.service.serviceinterface.JobService;

@Controller
@AllArgsConstructor
public class JobController {
     private JobService jobService;
     private CityService cityService;
     private ApplicantService applicantService;

    @GetMapping("/job-list-for-employer")
    public String listJobEmployer(Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var jobs = jobService.findByEmpId(user.getUser().getId());
        model.addAttribute("jobs",jobs);
        model.addAttribute("emp_id",user.getUser().getId());
        return "job/cong-viec-dang-tuyen-dung";
    }


    @GetMapping("/change-stasus-disable")
    public String changeStatusDisable(Model model,@RequestParam String emp_id,@RequestParam String job_id){
        jobService.changeStatusDisable(job_id);
        model.addAttribute("jobs",jobService.findByEmpId(emp_id));
        model.addAttribute("emp_id",emp_id);
        return "job/cong-viec-dang-tuyen-dung";
    }
    @GetMapping("/change-stasus-active")
    public String changeStatusActive(Model model,@RequestParam String emp_id,@RequestParam String job_id){
        jobService.changeStatusActive(job_id);
        model.addAttribute("jobs",jobService.findByEmpId(emp_id));
        model.addAttribute("emp_id",emp_id);
        return "job/cong-viec-dang-tuyen-dung";
    }

    @GetMapping(value = "/add-job")
    public String addEmployerForm(Model model) {
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        jobService.findByEmpId(user.getUser().getId());
        model.addAttribute("job", new JobRequest(UUID.randomUUID().toString(), "", "", "","","","",null ,null, user.getUser().getId(),"","","",""));
        model.addAttribute("citycodes", cityService.getCity());
        model.addAttribute("jobType", JobType.values());
        model.addAttribute("skillEnum", Skill.values());
        return "job/post-job";
    }

    @GetMapping("/edit-job")
    public String editJob (Model model , @RequestParam String job_id, @RequestParam String emp_id ){
        var job = jobService.findByID(job_id);
        model.addAttribute("job", new JobRequest(job_id , job.getTitle(),job.getJobDescription(), job.getJobSkill(),job.getBenefits(),job.getSalary(),job.getCity(),job.getSkills(), job.getType(), emp_id, job.getDeadline(),job.getVacancy(),job.getExperience(),job.getEducation()));
        model.addAttribute("citycodes", cityService.getCity());
        model.addAttribute("jobType", JobType.values());
        model.addAttribute("skillEnum", Skill.values());
        return "job/post-job";
    }

    @PostMapping("/save")
    public String addEmployer(@Valid @ModelAttribute("job") JobRequest jobRequest,
                              BindingResult result,
                              Model model) throws ParseException {
        if (result.hasErrors()) {
            model.addAttribute("citycodes", cityService.getCity());
            model.addAttribute("jobType", JobType.values());
            model.addAttribute("skillEnum", Skill.values());
            return "job/post-job";
        }
        jobService.addJob(jobRequest);
        return "redirect:/job-list-for-employer?emp_id="+jobRequest.emp_id();
    }
    @GetMapping("/apply-list")
    public String applyListDisplay(Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var applicants= applicantService.findAllApplyByEmpId(user.getUser().getId());
        model.addAttribute("applicants",applicants);
        return "job/danh-sach-ung-vien";
    }
    @GetMapping("/apply-list-accept")
    public String applyListAcceptDisplay(Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var applicants= applicantService.findAllApplyAcceptByEmpId(user.getUser().getId());
        model.addAttribute("applicants",applicants);
        return "job/danh-sach-ung-vien-da-dong-y";
    }
    @GetMapping("/apply-list-reject")
    public String applyListRejectDisplay(Model model){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var applicants= applicantService.findAllApplyRejectByEmpId(user.getUser().getId());
        model.addAttribute("applicants",applicants);
        return "job/danh-sach-ung-vien-da-tu-choi";
    }

    @GetMapping("/apply-list-in-job")
    public String applyListInJob(Model model,@RequestParam String job_id){
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var applicants= applicantService.findAllApplicantInJob(user.getUser().getId(),job_id);
        model.addAttribute("applicants",applicants);
        return "job/danh-sach-ung-vien-trong-viec";
    }

}
