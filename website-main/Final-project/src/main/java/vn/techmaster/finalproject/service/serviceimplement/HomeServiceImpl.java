package vn.techmaster.finalproject.service.serviceimplement;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.model.entity.ApplyState;
import vn.techmaster.finalproject.model.entity.Job;
import vn.techmaster.finalproject.model.entity.RoleEnum;
import vn.techmaster.finalproject.model.entity.State;
import vn.techmaster.finalproject.repository.ApplicantJobRepo;
import vn.techmaster.finalproject.repository.JobRepository;
import vn.techmaster.finalproject.security.UserDetailCustom;
import vn.techmaster.finalproject.service.serviceinterface.ApplicantService;
import vn.techmaster.finalproject.service.serviceinterface.HomeService;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class HomeServiceImpl implements HomeService {

    private JobRepository jobRepository;

    private ApplicantService applicantService;

    private ApplicantJobRepo applicantJobRepo;
    @Override
    public Page<Job> findAllByTitleContains(String keyword ,String city,Pageable pageable) {
        if(keyword.equals("")&&city.equals("")){
            return  jobRepository.findAllPaging(pageable);
        } else if(keyword.equals("")){
            return jobRepository.findAllByCity(city,pageable);
        }else if(city.equals("")){
            return jobRepository.findAllByKey(keyword.toLowerCase(),pageable);
        }
        return jobRepository.findAllByAll(keyword.toLowerCase(),city,pageable);
    }

    @Override
    public Collection<Job> ListAll() {
        return jobRepository.findAll().stream().filter(job -> job.getState().equals(State.ACTIVE)).collect(Collectors.toList());
    }

    @Override
    public Collection<Job> ListAllPage() {
        return jobRepository.findAll();
    }

    @Override
    public boolean checkHide(String job_id) {
        UserDetailCustom user = (UserDetailCustom) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(user.getUser().getRole().equals(RoleEnum.EMPLOEYER)){
            return false;
        }
        String id = applicantService.findApplicantJob(user.getUser().getId(),job_id);
        if(id==null){
            return true;
        }
        var applicantJob = applicantJobRepo.findById(id);
        if(applicantJob.isEmpty()){
            return true;
        }
        else if(applicantJob.get().getApplyState().equals(ApplyState.NONE)){
            return true;
        }
        return false;
    }

    public Collection<Job> SearchByKeyword(String search, String city) {
        if(search.equals("")&&city.equals("")){
            return  ListAll();
        }
        else if(search.equals("")){
            return ListAll().stream().filter(job -> job.SearchByCity(city)).collect(Collectors.toList());
        }else if(city.equals("")){
            return ListAll().stream().filter(job -> job.SearchByOnlyKeyword(search)).collect(Collectors.toList());
        }
        return ListAll().stream().filter(job -> job.SearchByKeywordAndCity(search,city)).collect(Collectors.toList());
    }

}
