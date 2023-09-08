package vn.techmaster.finalproject.controller.RestController;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vn.techmaster.finalproject.service.serviceinterface.FileService;

@RestController
@AllArgsConstructor
public class FileReaderController {
    private FileService fileService;

    @GetMapping(value = "/photo/files/{id}/{fileName}",produces = {MediaType.IMAGE_PNG_VALUE})
    public ResponseEntity<?> readFilePhoto (@PathVariable String id , @PathVariable String fileName) {
        byte[] bytes = fileService.readFile(id , fileName);
        return ResponseEntity.ok().body(bytes);
    }

    @GetMapping(value = "/pdf/files/{id}/{fileName}",produces = {MediaType.APPLICATION_PDF_VALUE})
    public ResponseEntity<?> readFilePDF (@PathVariable String id , @PathVariable String fileName) {
        byte[] bytes = fileService.readFile(id , fileName);
        return ResponseEntity.ok().body(bytes);
    }
}
