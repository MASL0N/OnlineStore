package OnlineStoreWemalpa.com.OnlineStore.repository;

import OnlineStoreWemalpa.com.OnlineStore.model.Material;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface MaterialRepository extends JpaRepository<Material, Long> {
    void deleteById(Long id);
    boolean existsByName(String name);
    List<Material> findByClothingType(String clothingType);
    List<Material> findByClothingTypeIn(List<String> clothingTypes);
    Optional<Material> findByNameAndClothingType(String name, String clothingType);
    Material findByName(String name);
}
