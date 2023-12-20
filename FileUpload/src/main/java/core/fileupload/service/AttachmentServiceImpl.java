package core.fileupload.service;

import core.fileupload.model.Attachment;
import core.fileupload.repository.AttachmentRepository;
import core.fileupload.response.ResponseData;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttachmentServiceImpl implements AttachmentService{

    private AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public Attachment saveAttachment(MultipartFile file) throws Exception {
       String fileName = StringUtils.cleanPath(file.getOriginalFilename());

       try{
             if (fileName.contains("..")){
                 throw new Exception("Pathname contains invalid path sequence: " + fileName);
             }
            Attachment attachment = new Attachment(
                    fileName,
                    file.getContentType(),
                    file.getBytes()
            );

             return attachmentRepository.save(attachment);

       } catch (Exception e){
            throw new Exception("Could not save file: " + fileName);
       }
    }

    @Override
    public Attachment getAttachment(String fileId) throws Exception {
        return attachmentRepository.findById(fileId)
                .orElseThrow(() -> new Exception("File not found with id" + fileId));
    }


    @Override
    public List<ResponseData> getAllResponseData() {
        List<Attachment> attachments = attachmentRepository.findAll();
        return attachments.stream()
                .map(attachment -> new ResponseData(
                        attachment.getFileName(),
                        ServletUriComponentsBuilder.fromCurrentContextPath()
                                .path("/api/download/")
                                .path(attachment.getId())
                                .toUriString(),
                        attachment.getFileType(),
                        attachment.getData().length))
                .collect(Collectors.toList());
    }

}

