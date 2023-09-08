package vn.techmaster.finalproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.techmaster.finalproject.model.entity.Employer;
import vn.techmaster.finalproject.model.entity.Job;

import java.util.List;

@Repository
public interface EmployerRepo extends JpaRepository<Employer,String> {

}
