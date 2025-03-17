package OnlineStoreWemalpa.com.OnlineStore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @Column(name = "order_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @JsonIgnore  // Исключаем из JSON, чтобы избежать циклической зависимости
    @ToString.Exclude  // Lombok не будет включать это поле в toString()
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "order_price", nullable = false)
    private BigDecimal orderPrice;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Override
    public String toString() {
        return "Order{id=" + orderId + ", status='" + status + "', price=" + orderPrice + "}";
    }
}
