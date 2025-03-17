package OnlineStoreWemalpa.com.OnlineStore.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileUploadService {
    String uploadFile(MultipartFile file);
}
