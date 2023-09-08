package vn.techmaster.finalproject.service.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.exception.NotFoundException;
import vn.techmaster.finalproject.model.entity.Employer;
import vn.techmaster.finalproject.model.entity.Job;
import vn.techmaster.finalproject.model.entity.State;
import vn.techmaster.finalproject.repository.EmployerRepo;
import vn.techmaster.finalproject.repository.JobRepository;
import vn.techmaster.finalproject.request.JobRequest;
import vn.techmaster.finalproject.service.serviceinterface.JobService;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;


@Component
public class JobServiceImpl implements JobService {
    @Autowired private JobRepository jobRepository;
    @Autowired private EmployerRepo employerRepo;

    @Override
    public Collection<Job> findByEmpId(String empId) {
        Optional<Employer> employer = employerRepo.findById(empId);
        employer.orElseThrow(() -> new NotFoundException("Không tìm thấy người dùng này"));
        return employer.get().getJobs();
    }

    @Override
    public void changeStatusDisable(String job_id) {
        Optional<Job> job = jobRepository.findById(job_id);
        job.get().setTimeUpdate(LocalDateTime.now());
        job.get().setState(State.DISABLED);
        jobRepository.save(job.get());
    }

    @Override
    public void changeStatusActive(String job_id) {
        Optional<Job> job = jobRepository.findById(job_id);
        job.get().setTimeUpdate(LocalDateTime.now());
        job.get().setState(State.ACTIVE);
        jobRepository.save(job.get());

    }

    @Override
    public void addJob(JobRequest jobRequest) {
        Optional<Employer> employer = employerRepo.findById(jobRequest.emp_id());
        jobRepository.save(Job.builder()
                .id(jobRequest.id())
                .employer(employer.get())
                .timeCreate(LocalDateTime.now())
                .title(jobRequest.title())
                .salary(jobRequest.salary())
                .timeUpdate(LocalDateTime.now())
                .jobDescription(jobRequest.jobDescription())
                .jobSkill(jobRequest.jobSkill())
                .benefits(jobRequest.benefits())
                .skills(jobRequest.skill())
                .type(jobRequest.type())
                .state(State.PENDING)
                .deadline(jobRequest.deadline())
                .vacancy(jobRequest.vacancy())
                .experience(jobRequest.experience())
                .education(jobRequest.education())
                .city(jobRequest.city()).build());
    }

    @Override
    public Job findByID(String job_id) {
        Optional<Job> job = jobRepository.findById(job_id);
        job.orElseThrow(() -> new NotFoundException("Không tìm thấy công việc này"));
        return job.get();
    }

}
