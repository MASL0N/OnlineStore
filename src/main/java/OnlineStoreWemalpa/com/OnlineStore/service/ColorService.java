package OnlineStoreWemalpa.com.OnlineStore.service;

import OnlineStoreWemalpa.com.OnlineStore.model.Color;
import OnlineStoreWemalpa.com.OnlineStore.model.Order;

import java.util.List;

public interface ColorService {
    Color findById(Long id);
    List<Color> findAllColor();
    Color saveColor(Color color);
    Color updateColor(Color color);
    void deleteColor(Long color);
}
