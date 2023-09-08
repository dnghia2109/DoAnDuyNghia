package vn.techmaster.finalproject.service.serviceinterface;

import org.springframework.stereotype.Service;

@Service
public interface EmployerService {
    public abstract void removeEmployer(String emp_id);

    public abstract void acceptApplicant(String applicant_id, String job_id);

    public abstract void rejectApplicant(String applicant_id, String job_id);
}
