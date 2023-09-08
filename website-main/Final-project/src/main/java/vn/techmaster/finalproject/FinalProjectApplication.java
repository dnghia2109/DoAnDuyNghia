package vn.techmaster.finalproject;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.scheduling.annotation.EnableAsync;
import vn.techmaster.finalproject.model.entity.*;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootApplication
@AllArgsConstructor
@EnableAsync
public class FinalProjectApplication  {
//    private EntityManager entityManager;
    public static void main(String[] args) {
        SpringApplication.run(FinalProjectApplication.class, args);
    }

//    @Override
//    @Transactional
//    public void run(String... args) throws Exception {
//        var user = User.builder().id(UUID.randomUUID().toString()).name("main").email("admin@gmail.com").password("47775855555").build();
//        var file = File.builder().id(UUID.randomUUID().toString()).path("Javax.txt").user(user).build();
//
//        var employer1 = Employer.builder().user(User.builder().id("1234").name("cmc").email("cmc@cmc.com").password("123456789").state(State.ACTIVE).build()).build();
//        var applicant1 = Applicant.builder().user(User.builder().id("124566").name("d").email("abc@gmail.com").password("password").build()).skill(Skill.CPLUS).build();
//        var job = Job.builder().id(UUID.randomUUID().toString()).title("Java").state(State.ACTIVE).city("Thành phố Đà Nẵng").build();
//        var jobApplicant = ApplicantJob.builder().id(UUID.randomUUID().toString()).love(true).timeCreate(LocalDateTime.now()).applicant(applicant1).job(job).build();
//
//        entityManager.persist(user);
//        entityManager.persist(file);
//        entityManager.persist(employer1);
//        entityManager.persist(applicant1);
//        entityManager.persist(job);
//        entityManager.persist(jobApplicant);
//
//        entityManager.flush();
//    }
}
