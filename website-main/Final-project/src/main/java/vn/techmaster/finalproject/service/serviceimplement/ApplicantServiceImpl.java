package vn.techmaster.finalproject.service.serviceimplement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.model.entity.*;
import vn.techmaster.finalproject.repository.ApplicantJobRepo;
import vn.techmaster.finalproject.repository.ApplicantRepo;
import vn.techmaster.finalproject.repository.JobRepository;
import vn.techmaster.finalproject.repository.UserRepository;
import vn.techmaster.finalproject.service.serviceinterface.ApplicantService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ApplicantServiceImpl implements ApplicantService {

    private ApplicantRepo applicantRepo;

    private JobRepository jobRepository;

    private ApplicantJobRepo applicantJobRepo;

    private UserRepository userRepository;


    @Override
    public void loveJob(String userID, String jobId) {
        String applicantJobID = findApplicantJob(userID,jobId);
        var job= jobRepository.findById(jobId).orElseThrow(()-> new RuntimeException("Không tìm thấy công việc này"));
        var applicant=applicantRepo.findById(userID).orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng này"));
        if(applicantJobID == null){
            applicantJobRepo.save(ApplicantJob.builder().id(UUID.randomUUID().toString()).applicant(applicant).job(job).love(true).applyState(ApplyState.NONE).timeCreate(LocalDateTime.now()).timeUpdate(LocalDateTime.now()).build());
        }else{
            var applicantJob = applicantJobRepo.findById(applicantJobID).get();
            applicantJob.setLove(true);
            applicantJob.setApplyState(ApplyState.NONE);
            applicantJob.setTimeUpdate(LocalDateTime.now());
            applicantJobRepo.save(applicantJob);
        }
    }

    @Override
    public Collection<File> findAllCv(String userID) {
        Optional<User> user = userRepository.findById(userID);
        user.orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng"));
        return user.get().getFiles().stream().filter(file -> file.getFileType().equals(FileType.PDF)).collect(Collectors.toList());
    }

    @Override
    public void applyJob(String userID, String jobId, String filePath) {
        String applicantJobID = findApplicantJob(userID,jobId);
        var job= jobRepository.findById(jobId).orElseThrow(()-> new RuntimeException("Không tìm thấy công việc này"));
        var applicant=applicantRepo.findById(userID).orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng này"));
        if(applicantJobID == null){
            applicantJobRepo.save(ApplicantJob.builder().id(UUID.randomUUID().toString()).applicant(applicant).job(job).cv_path(filePath).applyState(ApplyState.APPLIED).timeCreate(LocalDateTime.now()).timeUpdate(LocalDateTime.now()).build());
        }else{
            var applicantJob = applicantJobRepo.findById(applicantJobID).get();
            applicantJob.setCv_path(filePath);
            applicantJob.setApplyState(ApplyState.APPLIED);
            applicantJob.setTimeUpdate(LocalDateTime.now());
            applicantJobRepo.save(applicantJob);
        }
    }


    @Override
    public String findApplicantJob(String userID, String jobID) {
       var job= jobRepository.findById(jobID).orElseThrow(()-> new RuntimeException("Không tìm thấy công việc này"));
       var applicant=applicantRepo.findById(userID).orElseThrow(()-> new RuntimeException("Không tìm thấy người dùng này"));
       var applicantJob = applicantJobRepo.findByApplicantAndJob(applicant,job);
        return applicantJob.map(ApplicantJob::getId).orElse(null);
    }

    @Override
    public Collection<ApplicantJob> findAllApplyByEmpId(String emp_id) {
        return applicantJobRepo.findAll().stream().filter(apply -> apply.getJob().getEmployer().getId().equals(emp_id)).filter(apply -> apply.getApplyState().equals(ApplyState.APPLIED)).collect(Collectors.toList());
    }

    @Override
    public Collection<ApplicantJob> findAllApplicantInJob(String emp_id, String job_id) {
        return applicantJobRepo.findAll().stream().filter(apply -> apply.getJob().getEmployer().getId().equals(emp_id)).filter(apply ->apply.getJob().getId().equals(job_id)).filter(apply -> !apply.getApplyState().equals(ApplyState.NONE)).collect(Collectors.toList());
    }

    @Override
    public Collection<ApplicantJob> findAllApplyAcceptByEmpId(String emp_id) {
        return applicantJobRepo.findAll().stream().filter(apply -> apply.getJob().getEmployer().getId().equals(emp_id)).filter(apply -> apply.getApplyState().equals(ApplyState.ACCEPT)).collect(Collectors.toList());
    }

    @Override
    public Collection<ApplicantJob> findAllApplyRejectByEmpId(String emp_id) {
        return applicantJobRepo.findAll().stream().filter(apply -> apply.getJob().getEmployer().getId().equals(emp_id)).filter(apply -> apply.getApplyState().equals(ApplyState.REJECT)).collect(Collectors.toList());
    }

    @Override
    public Collection<ApplicantJob> findAllApplyByUserId(String user_id) {
        return applicantJobRepo.findAll().stream().filter(apply -> apply.getApplicant().getId().equals(user_id)).filter(apply -> !apply.getApplyState().equals(ApplyState.NONE)).collect(Collectors.toList());
    }

    @Override
    public Collection<ApplicantJob> findAllLikeByUserId(String user_id) {
        return applicantJobRepo.findAll().stream().filter(apply -> apply.getApplicant().getId().equals(user_id)).filter(apply -> apply.getApplyState().equals(ApplyState.NONE)).collect(Collectors.toList());
    }
}
