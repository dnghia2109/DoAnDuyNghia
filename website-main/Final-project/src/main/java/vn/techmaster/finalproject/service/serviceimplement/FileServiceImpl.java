package vn.techmaster.finalproject.service.serviceimplement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import org.springframework.web.multipart.MultipartFile;
import vn.techmaster.finalproject.exception.BadRequestException;
import vn.techmaster.finalproject.exception.NotFoundException;
import vn.techmaster.finalproject.model.entity.FileType;
import vn.techmaster.finalproject.model.entity.User;
import vn.techmaster.finalproject.repository.FileRepository;
import vn.techmaster.finalproject.repository.UserRepository;
import vn.techmaster.finalproject.service.serviceinterface.FileService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class FileServiceImpl implements FileService {

    @Autowired private UserRepository userRepository;

    @Autowired private FileRepository fileRepository;


    private final Path rootDir = Paths.get("src/main/resources/static/images/uploads");

    public FileServiceImpl() {
        createFolder(rootDir.toString());
    }

    @Override
    public void createFolder(String path) {
        File folder = new File(path);
        if(!folder.exists()){
            folder.mkdirs();
        }
    }

    @Override
    public String uploadFile(String user_id, MultipartFile file, FileType fileType) {
        Optional<User> userOptional = userRepository.findById(user_id);
        if (userOptional.isEmpty()) {
            throw new NotFoundException("user id " + user_id + " not found");
        }
        String fileName = file.getOriginalFilename();
        if (fileName == null || fileName.equals("")) {
            throw new BadRequestException("Tên file không hợp lệ");
        }
        String fileExtension = getFileExtension(fileName);
        Path UserDir = Paths.get("src/main/resources/static/images/uploads").resolve(String.valueOf(user_id));
        String fileID = UUID.randomUUID().toString();
        if (fileType.equals(FileType.PHOTO)) {
            if (!checkFileExtensionPhoto(fileExtension)) {
                throw new BadRequestException("Vui lòng chỉ upload file đúng định dạng ảnh png,jpg,jpeg");
            }
            if ((double) file.getSize() / 1_000_000L > 2) {
                throw new BadRequestException(("File không được vượt quá 2 MB"));
            }
            createFolder(UserDir.toString());
            String generateFilename = fileID + fileName;
            File serverFile = new File(UserDir.toString() + "/" + generateFilename);
            try {
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();
                String filePath = "/files/" + user_id + "/" + generateFilename;
                userOptional.get().setPhoto(filePath);
                userRepository.save(userOptional.get());
                fileRepository.save(vn.techmaster.finalproject.model.entity.File.builder().id(fileID).fileType(FileType.PHOTO).name(generateFilename).path(filePath).user(userOptional.get()).timeUpload(LocalDateTime.now()).build());
                return filePath;
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Lỗi Khi upload");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }else if (fileType.equals(FileType.PDF)){
            if (!checkFileExtensionPDF(fileExtension)) {
                throw new BadRequestException("Vui lòng chỉ upload file đúng định dạng file PDF");
            }
            if ((double) file.getSize() / 1_000_000L > 5) {
                throw new BadRequestException(("File không được vượt quá 5 MB"));
            }
            createFolder(UserDir.toString());
            String generateFilename = fileID + fileName;
            File serverFile = new File(UserDir.toString() + "/" + generateFilename);
            try {
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                stream.write(file.getBytes());
                stream.close();
                String filePath = "/files/" + user_id + "/" + generateFilename;
                fileRepository.save(vn.techmaster.finalproject.model.entity.File.builder().id(fileID).fileType(FileType.PDF).name(generateFilename).path(filePath).timeUpload(LocalDateTime.now()).user(userOptional.get()).build());
                return filePath;
            } catch (FileNotFoundException e) {
                throw new RuntimeException("Lỗi Khi upload");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return "";
    }

        //Tạo Path tương ứng với file Upload lên
    @Override
    public String getFileExtension(String fileName) {
        int lastIndexOf = fileName.lastIndexOf(".");
        if (lastIndexOf == -1) {
            return "";
        }
        return fileName.substring(lastIndexOf+1);
    }

    @Override
    public boolean checkFileExtensionPDF(String fileExtension) {
        List<String> fileExtensions = Arrays.asList("pdf");
        return fileExtensions.contains(fileExtension);
    }

    @Override
    public boolean checkFileExtensionPhoto(String fileExtension) {
        List<String> fileExtensions = Arrays.asList("png","jpg","jpeg");
        return fileExtensions.contains(fileExtension);
    }

    @Override
    public byte[] readFile(String id, String fileName) {

        Path userPath = rootDir.resolve(String.valueOf(id));

        // Kiểm tra đường dẫn file có tồn tại hay không
        if (!Files.exists(userPath)) {
            throw new RuntimeException("Không thể đọc file : " + fileName);
        }
        try {
            // Lấy đường dẫn file tương ứng với user_id và file_name
            Path file = userPath.resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return StreamUtils.copyToByteArray(resource.getInputStream());
            } else {
                throw new RuntimeException("Không thể đọc file: " + fileName);
            }
        } catch (Exception e) {
            throw new RuntimeException("Không thể đọc file : " + fileName);
        }
    }

    @Override
    public List<String> getFiles(String id) {
        return null;
    }


}
