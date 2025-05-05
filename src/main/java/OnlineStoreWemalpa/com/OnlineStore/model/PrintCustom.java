package OnlineStoreWemalpa.com.OnlineStore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "print_custom")
@Data
public class PrintCustom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "print_custom_id")
    private Long printCustomId;

    @Column(name = "x")
    private String x;

    @Column(name = "y")
    private String y;

    @Column(name = "scale")
    private String scale;

    @Column(name = "orientation")
    private String orientation;

    @Column(name = "image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
