package vn.techmaster.finalproject.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record CompanyRegisterRequest(@NotBlank String firstName , @NotBlank String lastName , @NotBlank String companyName, @NotBlank String website , String city, String phoneNumber , @Email String email , @NotBlank @Size(message = "Độ dài từ 8-20 kí tư" , min = 8, max = 20) String password , String cfPassword) {
}
