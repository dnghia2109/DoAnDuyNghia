package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.finalproject.model.entity.Applicant;

@Repository
public interface ApplicantRepo extends JpaRepository<Applicant,String> {
}
