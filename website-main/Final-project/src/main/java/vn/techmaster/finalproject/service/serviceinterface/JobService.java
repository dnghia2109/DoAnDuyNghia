package vn.techmaster.finalproject.service.serviceinterface;

import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.model.entity.Employer;
import vn.techmaster.finalproject.model.entity.Job;
import vn.techmaster.finalproject.request.JobRequest;

import java.util.Collection;
import java.util.List;

@Service
public interface JobService {
    public abstract Collection<Job> findByEmpId(String empId);

    public abstract void changeStatusDisable(String job_id);

    public abstract void changeStatusActive(String job_id);

    public abstract void addJob(JobRequest jobRequest);

    public abstract Job findByID(String job_id);

}
