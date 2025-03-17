package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.OrderItem;
import OnlineStoreWemalpa.com.OnlineStore.repository.OrderItemRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository repository;
    @Override
    public List<OrderItem> findAllOrderItem() {
        return repository.findAll();
    }

    @Override
    public OrderItem saveOrderItem(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public OrderItem updateOrderItem(OrderItem orderItem) {
        return repository.save(orderItem);
    }

    @Override
    public void deleteOrderItem(Long id) {
        repository.deleteById(id);
    }
}
