package OnlineStoreWemalpa.com.OnlineStore.repository;

import OnlineStoreWemalpa.com.OnlineStore.model.ProductSize;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductSizeRepository extends JpaRepository<ProductSize, Long> {
    void deleteById(Long id);
}
