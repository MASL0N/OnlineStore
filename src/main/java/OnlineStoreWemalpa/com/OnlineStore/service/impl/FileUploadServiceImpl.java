package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.service.FileUploadService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadServiceImpl implements FileUploadService {

    private static final String UPLOAD_DIR = "src/main/resources/static/img/";

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("Файл не загружен!");
        }

        try {
            // Генерируем уникальное имя файла
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            Path filePath = Path.of(UPLOAD_DIR + fileName);

            // Копируем файл в нужную папку
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            // Возвращаем относительный путь к файлу (для хранения в БД)
            return "/img/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке файла: " + e.getMessage());
        }
    }
}
