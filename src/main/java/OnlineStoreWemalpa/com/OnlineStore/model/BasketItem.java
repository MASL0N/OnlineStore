package OnlineStoreWemalpa.com.OnlineStore.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "basket_item")
@Data
public class BasketItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "basket_item_id")
    private Long basketItemId;

    @ManyToOne
    @JoinColumn(name = "basket_id", nullable = false)
    private Basket basket;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @ManyToOne
    @JoinColumn(name = "size_id", nullable = false)
    private ProductSize size;
}

