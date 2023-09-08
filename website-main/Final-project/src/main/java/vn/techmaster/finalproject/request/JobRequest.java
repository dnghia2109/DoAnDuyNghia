package vn.techmaster.finalproject.request;

import vn.techmaster.finalproject.model.entity.JobType;
import vn.techmaster.finalproject.model.entity.Skill;

import javax.validation.constraints.NotBlank;
import java.util.List;

public record JobRequest (
 String id,
 @NotBlank
 String title ,
 String jobDescription,
 String jobSkill,
 String benefits,
 @NotBlank
 String salary,
 String city,
 List<Skill> skill,
 JobType type,
 String emp_id,
 String deadline,
 String vacancy,
 String experience,
 String education
 ) {
    
}
