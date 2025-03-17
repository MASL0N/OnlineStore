package OnlineStoreWemalpa.com.OnlineStore.service;

import OnlineStoreWemalpa.com.OnlineStore.model.Color;

import java.util.List;

public interface ColorService {
    List<Color> findAllColor();
    Color saveColor(Color color);
    Color updateColor(Color color);
    void deleteColor(Long color);
}
