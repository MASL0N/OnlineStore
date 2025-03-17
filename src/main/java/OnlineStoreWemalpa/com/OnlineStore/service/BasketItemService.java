package OnlineStoreWemalpa.com.OnlineStore.service;

import OnlineStoreWemalpa.com.OnlineStore.model.BasketItem;

import java.util.List;

public interface BasketItemService {
    List<BasketItem> findAllBasketItem();
    BasketItem saveBasketItem(BasketItem basketItem);
    BasketItem updateBasketItem(BasketItem basketItem);
    void deleteBasketItem(Long basketItem);
}
