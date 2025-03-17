package OnlineStoreWemalpa.com.OnlineStore.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "decsription")
    private String decsription;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "gender")
    private String gender;

    @Column(name = "clothing_type", nullable = false)
    private String clothingType;

    @Column(name = "custom")
    private Boolean custom;

    @ManyToOne
    @JoinColumn(name = "print_type_id")
    private PrintType printType;

    @ManyToOne
    @JoinColumn(name = "material_id")
    private Material material;

    @ManyToOne
    @JoinColumn(name = "color_id")
    private Color color;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<ProductSize> sizes = new ArrayList<>(); // Связь с размерами продукта

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductImage> images = new ArrayList<>();  // Связь с изображениями продукта

    public ProductImage getPrimaryImage() {
        if (images == null || images.isEmpty()) {
            return null;
        }

        ProductImage primaryImage = images.stream()
                .filter(ProductImage::getIsPrimary)
                .findFirst()
                .orElseGet(() -> {
                    return images.iterator().next();
                });

        return primaryImage;
    }
}

