package OnlineStoreWemalpa.com.OnlineStore.service;


import OnlineStoreWemalpa.com.OnlineStore.model.OrderItem;

import java.util.List;

public interface OrderItemService {
    List<OrderItem> findAllOrderItem();
    OrderItem saveOrderItem(OrderItem orderItem);
    OrderItem updateOrderItem(OrderItem orderItem);
    void deleteOrderItem(Long id);
}
