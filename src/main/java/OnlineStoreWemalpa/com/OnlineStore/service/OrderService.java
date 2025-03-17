package OnlineStoreWemalpa.com.OnlineStore.service;

import OnlineStoreWemalpa.com.OnlineStore.model.Order;

import java.util.List;

public interface OrderService {
    List<Order> getOrderHistory(Long userId);
    List<Order> getActiveOrders(Long userId);
    List<Order> findAllOrder();
    Order saveOrder(Order order);
    Order updateOrder(Order order);
    Order findById(Long id);
    void deleteOrder(Long id);
}
