package OnlineStoreWemalpa.com.OnlineStore.repository;

import OnlineStoreWemalpa.com.OnlineStore.model.PrintType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrintTypeRepository extends JpaRepository<PrintType, Long> {
    void deleteById(Long id);
    boolean existsByName(String name);
}
