package vn.techmaster.finalproject.service.serviceinterface;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.finalproject.model.entity.FileType;

import java.util.List;

@Service
public interface FileService {
    public abstract void createFolder(String path);
    public abstract String uploadFile(String id, MultipartFile file, FileType fileType);
    public abstract String getFileExtension(String fileName);
    public abstract boolean checkFileExtensionPDF(String fileExtension);
    public abstract boolean checkFileExtensionPhoto(String fileExtension);
    public abstract byte[] readFile(String id, String fileName);
    public abstract List<String> getFiles(String id);


}
