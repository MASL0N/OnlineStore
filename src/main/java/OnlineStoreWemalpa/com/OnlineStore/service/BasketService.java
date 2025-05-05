package OnlineStoreWemalpa.com.OnlineStore.service;

import OnlineStoreWemalpa.com.OnlineStore.model.Basket;
import OnlineStoreWemalpa.com.OnlineStore.model.User;

import java.util.List;

public interface BasketService {
    List<Basket> findAllBasket();
    Basket saveBasket(Basket basket);
    Basket updateBasket(Basket basket);
    void deleteBasket(Long basket);
    Basket getBasketByUser(User user);
    void updateItemQuantity(Long basketItemId, Integer quantity);
    Long checkout(User user);
    boolean addToBasket(User user, Long productId, Long sizeId, String primaryImage);
    void removeItem(Long basketItemId);
}
