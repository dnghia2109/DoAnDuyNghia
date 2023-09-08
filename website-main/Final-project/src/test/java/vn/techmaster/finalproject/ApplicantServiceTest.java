package vn.techmaster.finalproject;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;
import vn.techmaster.finalproject.model.entity.Applicant;
import vn.techmaster.finalproject.repository.ApplicantRepo;
import vn.techmaster.finalproject.service.serviceinterface.ApplicantService;

import java.util.Optional;

@SpringBootTest
@AllArgsConstructor
public class ApplicantServiceTest {
    private ApplicantService applicantService;

    private ApplicantRepo  applicantRepo;
    @Test
    public void deleteApplicantTest(){
//        applicantService.removeApplicant("124566");
//        Optional<Applicant> applicant =  applicantRepo.findById("124566");
//        Assert.isTrue(applicant.isPresent());
//        System.out.println("1234");
    }
}
