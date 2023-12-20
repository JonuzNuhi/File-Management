package core.fileupload.service;

import core.fileupload.model.Attachment;
import core.fileupload.response.ResponseData;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;

    List<ResponseData> getAllResponseData();
}
