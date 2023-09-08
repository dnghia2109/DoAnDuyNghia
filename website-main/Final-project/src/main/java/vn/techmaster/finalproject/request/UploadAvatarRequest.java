package vn.techmaster.finalproject.request;

import org.springframework.web.multipart.MultipartFile;

public record UploadAvatarRequest(String userID, MultipartFile avatar) {
}
