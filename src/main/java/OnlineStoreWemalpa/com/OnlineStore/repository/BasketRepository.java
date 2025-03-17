package OnlineStoreWemalpa.com.OnlineStore.repository;

import OnlineStoreWemalpa.com.OnlineStore.model.Basket;
import OnlineStoreWemalpa.com.OnlineStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    void deleteById(Long id);
    Optional<Basket> findByUser(User user);
}
