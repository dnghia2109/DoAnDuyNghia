package vn.techmaster.finalproject.service.serviceimplement;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import vn.techmaster.finalproject.model.entity.ApplicantJob;
import vn.techmaster.finalproject.model.entity.ApplyState;
import vn.techmaster.finalproject.repository.ApplicantJobRepo;
import vn.techmaster.finalproject.repository.EmployerRepo;
import vn.techmaster.finalproject.service.serviceinterface.ApplicantService;
import vn.techmaster.finalproject.service.serviceinterface.EmployerService;
import vn.techmaster.finalproject.service.serviceinterface.MailService;

import java.time.LocalDateTime;
import java.util.UUID;

@Component
@AllArgsConstructor
public class EmployerServiceImpl implements EmployerService {
    private EmployerRepo employerRepo;

    private ApplicantService applicantService;

    private ApplicantJobRepo applicantJobRepo;

    private MailService mailService;


    @Override
    public void removeEmployer(String emp_id) {
        employerRepo.deleteById(emp_id);
    }

    @Override
    public void acceptApplicant(String applicant_id, String job_id) {
       String id = applicantService.findApplicantJob(applicant_id,job_id);
       var applicantJob=applicantJobRepo.findById(id).get();
       applicantJob.setApplyState(ApplyState.ACCEPT);
       applicantJobRepo.save(applicantJob);
       mailService.sendEmail(applicantJob.getApplicant().getUser().getEmail(),"Thư báo kết quả ứng tuyển công việc "+applicantJob.getJob().getTitle()
               ,"Chào bạn "+applicantJob.getApplicant().getUser().getName()+" ! \n"
                       +"Thay mặt công ty "+ applicantJob.getJob().getEmployer().getCompanyName()+" cảm ơn bạn đã ứng tuyển công việc "+applicantJob.getJob().getTitle()+". \n"
                       +"Chúng tôi gửi email này để thông báo rằng bạn đã xuất sắc vượt qua vòng CV . \n"
                       +"Bộ phận nhân sự của công ty sẽ liên hệ lại với bạn để sắp xếp một cuộc trao đổi về kĩ năng và cơ hội nghề nghiệp tại công ty của họ . \n"
                       +"Cảm ơn bạn và chúc bạn có một ngày tốt lành !");
    }

    @Override
    public void rejectApplicant(String applicant_id, String job_id) {
        String id = applicantService.findApplicantJob(applicant_id,job_id);
        var applicantJob=applicantJobRepo.findById(id).get();
        applicantJob.setApplyState(ApplyState.REJECT);
        applicantJobRepo.save(applicantJob);
        mailService.sendEmail(applicantJob.getApplicant().getUser().getEmail(),"Thư báo kết quả ứng tuyển công việc "+applicantJob.getJob().getTitle()
                ,"Chào bạn "+applicantJob.getApplicant().getUser().getName()+" ! \n"
                        +"Thay mặt công ty "+ applicantJob.getJob().getEmployer().getCompanyName()+" cảm ơn bạn đã ứng tuyển công việc "+applicantJob.getJob().getTitle()+". \n"
                        +"Chúng tôi gửi email này để thông báo rằng bạn không phù hợp với vị trí mà chúng tôi đang tuyển dụng . \n"
                        +"Bộ phận nhân sự của công ty đã lưu hồ sơ của bạn vào cơ sở dũ liệu . Nếu trong thời gian tới công ty có vị trí phù hợp với bạn, chúng tôi sẽ liên hệ lại với bạn . \n"
                        +"Cảm ơn bạn và chúc bạn có một ngày tốt lành !");
    }


}
