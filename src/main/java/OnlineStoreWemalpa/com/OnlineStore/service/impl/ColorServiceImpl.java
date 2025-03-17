package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.Color;
import OnlineStoreWemalpa.com.OnlineStore.repository.ColorRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.ColorService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class ColorServiceImpl implements ColorService {
    private final ColorRepository repository;
    @Override
    public List<Color> findAllColor() {
        return repository.findAll();
    }

    @Override
    public Color saveColor(Color color) {
        return repository.save(color);
    }

    @Override
    public Color updateColor(Color color) {
        return repository.save(color);
    }

    @Override
    public void deleteColor(Long id) {
        repository.deleteById(id);

    }
}
