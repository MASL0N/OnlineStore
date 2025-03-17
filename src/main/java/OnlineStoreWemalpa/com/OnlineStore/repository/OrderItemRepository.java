package OnlineStoreWemalpa.com.OnlineStore.repository;

import OnlineStoreWemalpa.com.OnlineStore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    void deleteById(Long id);
    boolean existsByProductId(Long productId);
}
