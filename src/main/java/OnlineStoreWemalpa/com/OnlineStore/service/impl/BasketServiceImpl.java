package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.*;
import OnlineStoreWemalpa.com.OnlineStore.repository.BasketItemRepository;
import OnlineStoreWemalpa.com.OnlineStore.repository.BasketRepository;
import OnlineStoreWemalpa.com.OnlineStore.repository.ProductRepository;
import OnlineStoreWemalpa.com.OnlineStore.repository.ProductSizeRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.BasketService;
import OnlineStoreWemalpa.com.OnlineStore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
public class BasketServiceImpl implements BasketService {
    private final BasketRepository repository;
    private final BasketItemRepository basketItemRepository;
    private final OrderService orderService;
    private final ProductRepository productRepository;
    private final ProductSizeRepository productSizeRepository;
    @Override
    public List<Basket> findAllBasket() {
        return repository.findAll();
    }

    @Override
    public Basket saveBasket(Basket basket) {
        return repository.save(basket);
    }

    @Override
    public Basket updateBasket(Basket basket) {
        return repository.save(basket);
    }

    @Override
    public void deleteBasket(Long id) {
        repository.deleteById(id);
    }

    // Получить корзину пользователя
    @Override
    public Basket getBasketByUser(User user) {
        return repository.findByUser(user).orElse(new Basket());
    }
    // Обновить количество товара в корзине
    @Override
    public void updateItemQuantity(Long basketItemId, Integer quantity) {
        BasketItem basketItem = basketItemRepository.findById(basketItemId).orElseThrow();
        basketItem.setQuantity(quantity);
        basketItemRepository.save(basketItem);
    }
    // Оформить заказ из корзин
    @Override
    public Long checkout(User user) {
        Basket basket = getBasketByUser(user);

        if (basket == null || basket.getBasketItems().isEmpty()) {
            throw new RuntimeException("Невозможно оформить заказ: корзина пуста!");
        }

        Order order = new Order();
        order.setUser(user);
        order.setStatus("Active");
        order.setOrderPrice(basket.getTotalPrice());

        // Переносим товары из корзины в заказ
        for (BasketItem basketItem : basket.getBasketItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(basketItem.getProduct());
            orderItem.setQuantity(basketItem.getQuantity());
            orderItem.setPrice(basketItem.getProduct().getPrice());
            orderItem.setOrder(order);
            order.getOrderItems().add(orderItem);
        }

        orderService.saveOrder(order);  // Сохранить заказ в базе
        repository.delete(basket);  // Очистить корзину после оформления заказа

        return order.getOrderId();
    }

    @Transactional
    public boolean addToBasket(User user, Long productId, Long sizeId, String primaryImage) {
        Optional<Product> productOpt = productRepository.findById(productId);
        Optional<ProductSize> sizeOpt = productSizeRepository.findById(sizeId);

        if (productOpt.isEmpty() || sizeOpt.isEmpty()) {
            return false; // Если товар или размер не найден, возвращаем false
        }

        Product product = productOpt.get();
        ProductSize size = sizeOpt.get();

        // Получаем корзину пользователя или создаем новую
        Basket basket = repository.findByUser(user).orElseGet(() -> {
            Basket newBasket = new Basket();
            newBasket.setUser(user);
            return repository.save(newBasket);
        });

        // Проверяем, есть ли уже этот товар с этим размером в корзине
        Optional<BasketItem> existingItem = basket.getBasketItems()
                .stream()
                .filter(item -> item.getProduct().equals(product) && item.getSize().equals(size))
                .findFirst();

        if (existingItem.isPresent()) {
            // Увеличиваем количество
            existingItem.get().setQuantity(existingItem.get().getQuantity() + 1);
        } else {
            // Добавляем новый товар в корзину с выбранным размером и primaryImage
            BasketItem newItem = new BasketItem();
            newItem.setBasket(basket);
            newItem.setProduct(product);
            newItem.setSize(size);
            newItem.setQuantity(1);
            basket.getBasketItems().add(newItem);
        }

        repository.save(basket);
        return true; // Успешно добавлено в корзину
    }

    @Override
    public void removeItem(Long basketItemId) {
        basketItemRepository.deleteById(basketItemId);
    }
}
