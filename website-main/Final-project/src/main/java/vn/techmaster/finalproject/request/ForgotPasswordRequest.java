package vn.techmaster.finalproject.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record ForgotPasswordRequest(@NotBlank @Size(min = 8 , max = 20) String password , String cfPassword ,String token) {
}
