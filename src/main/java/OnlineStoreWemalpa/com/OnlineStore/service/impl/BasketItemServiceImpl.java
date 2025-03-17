package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.BasketItem;
import OnlineStoreWemalpa.com.OnlineStore.repository.BasketItemRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.BasketItemService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class BasketItemServiceImpl implements BasketItemService {
    private final BasketItemRepository repository;
    @Override
    public List<BasketItem> findAllBasketItem() {
        return repository.findAll();
    }

    @Override
    public BasketItem saveBasketItem(BasketItem basketItem) {
        return repository.save(basketItem);
    }

    @Override
    public BasketItem updateBasketItem(BasketItem basketItem) {
        return repository.save(basketItem);
    }

    @Override
    public void deleteBasketItem(Long id) {
        repository.deleteById(id);
    }
}
