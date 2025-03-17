package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.Material;
import OnlineStoreWemalpa.com.OnlineStore.repository.MaterialRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.MaterialService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Primary
public class MaterialServiceImpl implements MaterialService {
    private final MaterialRepository repository;
    @Override
    public List<Material> findAllMaterial() {
        return repository.findAll();
    }

    @Override
    public Material saveMaterial(Material material) {
        return repository.save(material);
    }

    @Override
    public Material updateMaterial(Material material) {
        return repository.save(material);
    }

    @Override
    public void deleteMaterial(Long id) { repository.deleteById(id); }

    @Override
    public Material findById(Long id) { return repository.findById(id).orElse(null);}

    @Override
    public boolean existsByName(String name) { return repository.existsByName(name); }

}
