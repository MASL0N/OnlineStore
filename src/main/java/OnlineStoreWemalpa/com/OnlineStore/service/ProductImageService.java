package OnlineStoreWemalpa.com.OnlineStore.service;

import OnlineStoreWemalpa.com.OnlineStore.model.BasketItem;
import OnlineStoreWemalpa.com.OnlineStore.model.ProductImage;

import java.util.List;

public interface ProductImageService {
    List<ProductImage> findAllProductImage();
    ProductImage saveProductImage(ProductImage productImage);
    ProductImage updateProductImage(ProductImage productImage);
    void deleteProductImage(Long productImage);
}
