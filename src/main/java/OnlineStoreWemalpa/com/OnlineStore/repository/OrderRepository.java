package OnlineStoreWemalpa.com.OnlineStore.repository;

import OnlineStoreWemalpa.com.OnlineStore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    void deleteById(Long id);
    List<Order> findByUserUserIdAndStatusNot(Long userId, String status);
    List<Order> findByUserUserIdAndStatus(Long userId, String status);
}
