package vn.techmaster.finalproject.request;

import org.springframework.web.multipart.MultipartFile;

public record UploadCVRequest(String jobID, MultipartFile cv) {
}
