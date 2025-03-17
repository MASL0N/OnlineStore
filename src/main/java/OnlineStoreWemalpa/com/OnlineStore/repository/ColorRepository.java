package OnlineStoreWemalpa.com.OnlineStore.repository;

import OnlineStoreWemalpa.com.OnlineStore.model.Color;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColorRepository extends JpaRepository<Color, Long> {
    void deleteById(Long id);
}
