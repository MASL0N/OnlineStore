package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.Order;
import OnlineStoreWemalpa.com.OnlineStore.model.Product;
import OnlineStoreWemalpa.com.OnlineStore.repository.OrderRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class OrderServiceImpl implements OrderService {
    private final OrderRepository repository;

    // Получить историю заказов пользователя (только завершенные)
    @Override
    public List<Order> getOrderHistory(Long userId) {
        return repository.findByUserUserIdAndStatus(userId, "Completed");
    }

    // Получить все активные заказы пользователя (например, со статусом "В обработке" или "Доставляется")
    @Override
    public List<Order> getActiveOrders(Long userId) {
        return repository.findByUserUserIdAndStatusNot(userId, "Completed");
    }

    @Override
    public Order findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public List<Order> findAllOrder() {
        return repository.findAll();
    }

    @Override
    public Order saveOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public Order updateOrder(Order order) {
        return repository.save(order);
    }

    @Override
    public void deleteOrder(Long id) {
        repository.deleteById(id);

    }
}
