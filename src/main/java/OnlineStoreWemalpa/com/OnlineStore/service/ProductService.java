package OnlineStoreWemalpa.com.OnlineStore.service;

import OnlineStoreWemalpa.com.OnlineStore.model.Product;
import OnlineStoreWemalpa.com.OnlineStore.model.ProductImage;

import java.util.List;

public interface ProductService {
    List<Product> findAllProduct();
    List<Product> findAllCustomProduct();
    Product saveProduct(Product product);
    Product updateProduct(Product product);
    Product findById(Long id);
    void deleteProduct(Long id);
    boolean deleteProductAdmin(Long id);
    Product saveProductWithImages(Product product, List<String> imageUrls);
    void saveProductWithImagesAndSizes(Product product);
    List<Product> findAllActualProduct();
}

