package OnlineStoreWemalpa.com.OnlineStore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Column(name = "gender")
    private String gender;

    @Column(name = "clothing_type", nullable = false)
    private String clothingType;

    @Column(name = "custom")
    private Boolean custom;

    @Column(name = "actual")
    private Boolean actual;

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
    private List<ProductSize> sizes = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<ProductImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<PrintCustom> prints = new ArrayList<>();

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

