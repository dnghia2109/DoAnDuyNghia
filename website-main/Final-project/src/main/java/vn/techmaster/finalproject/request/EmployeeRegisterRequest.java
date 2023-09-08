package vn.techmaster.finalproject.request;

import vn.techmaster.finalproject.model.entity.Skill;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

public record EmployeeRegisterRequest(@NotBlank String firstName , @NotBlank String lastName ,@NotBlank String phoneNumber , String city, List<Skill> skill,  @Email String email , @NotBlank @Size(min = 8 , max = 20) String password , String cfPassword) {
}
