package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import vn.techmaster.finalproject.model.entity.Applicant;
import vn.techmaster.finalproject.model.entity.ApplicantJob;
import vn.techmaster.finalproject.model.entity.Job;

import java.util.Optional;

@Repository
public interface ApplicantJobRepo extends JpaRepository<ApplicantJob,String> {
    Optional<ApplicantJob> findByApplicantAndJob(Applicant applicant, Job job) ;
}
