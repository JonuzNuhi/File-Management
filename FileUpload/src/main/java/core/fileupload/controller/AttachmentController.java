package core.fileupload.controller;

import com.google.api.client.http.InputStreamContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import core.fileupload.model.Attachment;
import core.fileupload.response.ResponseData;
import core.fileupload.service.AttachmentService;
import core.fileupload.service.DriveService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import static java.util.Currency.getInstance;

@RestController
@RequestMapping("/api")
public class AttachmentController {

    private AttachmentService attachmentService;

    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping("/upload")
    public ResponseData uploadFile(@RequestParam("file")MultipartFile file) throws Exception {
        if (file.getSize() <= 2 * 1024 * 1024) {
            Attachment attachment = null;
            String downloadUrl = "";
            attachment = attachmentService.saveAttachment(file);
            downloadUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/download/")
                    .path(attachment.getId())
                    .toUriString();

            return new ResponseData(attachment.getFileName(),
                    downloadUrl,
                    file.getContentType(),
                    file.getSize());
        } else{
            String fileId = service.uploadFile(file);
            return new ResponseData(file.getOriginalFilename(),
                    "Drive download link: " + fileId,
                    file.getContentType(),
                    file.getSize());
        }

    }

    @GetMapping("download/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable("fileId") String fileId) throws Exception {
        Attachment attachment = null;
        attachment = attachmentService.getAttachment(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(attachment.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + attachment.getFileName() + "\"")
                .body(new ByteArrayResource(attachment.getData()));
    }

    @GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ResponseData>> getAllMultifiles() {
        List<ResponseData> responseDataList = attachmentService.getAllResponseData();
        return new ResponseEntity<>(responseDataList, HttpStatus.OK);
    }

    @Autowired
    private DriveService service;

    @GetMapping("/list/drive")
    public String getAllAudio() throws IOException, GeneralSecurityException{
        return service.getfiles();
    }
    @PostMapping("upload/drive")
    public String uploadAudio(MultipartFile file) throws IOException, GeneralSecurityException{
        System.out.println(file.getOriginalFilename());

        return service.uploadFile(file);
    }

    @GetMapping("/download/drive/{id}")
    public void downloadFromDrive(@PathVariable String id, HttpServletResponse response) throws IOException, GeneralSecurityException {
        // Get the file metadata from Google Drive
        File fileMetadata = DriveService.getInstance().files().get(id).execute();

        // Set the content type based on the file's MIME type
        String contentType = fileMetadata.getMimeType();

        // Set the content type and headers for file download
        response.setContentType(contentType);
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileMetadata.getName() + "\"");

        // Download the file to the response output stream
        service.downloadFile(id, response.getOutputStream());
    }

}
