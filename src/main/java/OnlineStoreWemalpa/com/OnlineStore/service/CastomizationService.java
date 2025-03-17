package OnlineStoreWemalpa.com.OnlineStore.service;

import OnlineStoreWemalpa.com.OnlineStore.model.Product;

public interface CastomizationService {
    Product customizeProduct(Long productId, Long materialId, Long colorId, Long printTypeId);
}
