package vn.techmaster.finalproject.service.serviceinterface;

import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.model.entity.ApplicantJob;
import vn.techmaster.finalproject.model.entity.File;

import java.util.Collection;

@Service
public interface ApplicantService {
    public abstract void loveJob(String userID, String jobId);
    public abstract Collection<File> findAllCv(String userID);
    public abstract void applyJob(String userID, String jobId, String filePath);
    public abstract String findApplicantJob(String userID, String jobID);

    public abstract Collection<ApplicantJob> findAllApplyByEmpId (String emp_id);
    public abstract Collection<ApplicantJob> findAllApplicantInJob (String emp_id,String job_id);
    public abstract Collection<ApplicantJob> findAllApplyAcceptByEmpId (String emp_id);
    public abstract Collection<ApplicantJob> findAllApplyRejectByEmpId (String emp_id);
    public abstract Collection<ApplicantJob> findAllApplyByUserId (String user_id);
    public abstract Collection<ApplicantJob> findAllLikeByUserId (String user_id);
}
