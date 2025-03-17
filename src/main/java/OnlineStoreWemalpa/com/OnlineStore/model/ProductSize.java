package OnlineStoreWemalpa.com.OnlineStore.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_size")
public class    ProductSize {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "size", nullable = false)
    private String size;
}
