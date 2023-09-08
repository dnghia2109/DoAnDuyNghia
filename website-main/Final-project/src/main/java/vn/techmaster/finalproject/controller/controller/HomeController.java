package vn.techmaster.finalproject.controller.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import vn.techmaster.finalproject.model.entity.Job;
import vn.techmaster.finalproject.request.SearchRequest;
import vn.techmaster.finalproject.service.serviceinterface.CityService;
import vn.techmaster.finalproject.service.serviceinterface.HTMLService;
import vn.techmaster.finalproject.service.serviceinterface.HomeService;
import vn.techmaster.finalproject.service.serviceinterface.JobService;

import java.util.List;


@Controller
@AllArgsConstructor
public class HomeController {
    private HomeService homeService;
    private CityService cityService;
    private JobService jobService;
    private HTMLService htmlService;

    @GetMapping("/")
    public String Home(Model model){
        HomePage(model,1,"","");
        return "index";
    }
    @GetMapping("/home-page")
    public String HomePage(Model model,@RequestParam int pageNumber,String keyword ,String city){
        Pageable pageable = PageRequest.of(pageNumber - 1, 5);
        List<Job> listJob = homeService.findAllByTitleContains(keyword,city,pageable).getContent();
        long totalItems = homeService.findAllByTitleContains(keyword,city,pageable).getTotalElements();
        int totalPages = homeService.findAllByTitleContains(keyword,city,pageable).getTotalPages();
        model.addAttribute("currentPage", pageNumber);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("jobs",listJob);
        model.addAttribute("citycodes", cityService.getCity());
        model.addAttribute("search",new SearchRequest("",""));
        return "index";
    }

    @PostMapping("/search")
    public String Search(@ModelAttribute("search")SearchRequest searchRequest, Model model) {
        HomePage(model,1,searchRequest.keyword(),searchRequest.city());
        return "index";
    }

    @GetMapping("/job-detail")
    public String singleJob(Model model, @RequestParam String job_id){
        boolean checkHide = homeService.checkHide(job_id);
        String hide ;
        if(checkHide){
            hide = "0";
        }else{
            hide = "1";
        }
        var job = jobService.findByID(job_id);
        model.addAttribute("hide",hide);
        model.addAttribute("job",job);
        model.addAttribute("emp_id",job.getEmployer().getId());
        model.addAttribute("companyName",job.getEmployer().getCompanyName());
        model.addAttribute("jobDescription",htmlService.markdownToHtml(job.getJobDescription()));
        model.addAttribute("education",htmlService.markdownToHtml(job.getEducation()));
        model.addAttribute("jobSkill",htmlService.markdownToHtml(job.getJobSkill()));
        model.addAttribute("benefits",htmlService.markdownToHtml(job.getBenefits()));
        return "job-single";
    }
}
