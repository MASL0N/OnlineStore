package OnlineStoreWemalpa.com.OnlineStore.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "product_image")
public class ProductImage {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "is_primary")
    private Boolean isPrimary; // Флаг для главного изображения
}



