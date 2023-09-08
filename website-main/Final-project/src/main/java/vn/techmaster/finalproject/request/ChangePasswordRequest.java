package vn.techmaster.finalproject.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public record ChangePasswordRequest(String oldPass, @NotBlank @Size(message = "Độ dài từ 8-20 kí tự" , min = 8, max = 20) String newPass, String retypePass, String userId) {
}
