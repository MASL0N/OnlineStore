package OnlineStoreWemalpa.com.OnlineStore.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductFormDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String clothingType;
    private String gender;
    private List<String> sizes;
    private List<MultipartFile> images;
}
