package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.Product;
import OnlineStoreWemalpa.com.OnlineStore.model.ProductImage;
import OnlineStoreWemalpa.com.OnlineStore.model.ProductSize;
import OnlineStoreWemalpa.com.OnlineStore.repository.OrderItemRepository;
import OnlineStoreWemalpa.com.OnlineStore.repository.ProductImageRepository;
import OnlineStoreWemalpa.com.OnlineStore.repository.ProductRepository;
import OnlineStoreWemalpa.com.OnlineStore.repository.ProductSizeRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Primary
public class ProductServiceImpl implements ProductService {
    private final ProductRepository repository;
    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;
    private final ProductImageRepository productImageRepository;
    private final ProductSizeRepository productSizeRepository;

    @Override
    public List<Product> findAllProduct() {
        return repository.findAll();
    }

    @Override
    public List<Product> findAllCustomProduct() { return repository.findByCustomFalse(); }

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product updateProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void deleteProduct(Long id) {
        repository.deleteById(id);
    }

    @Override
    public boolean deleteProductAdmin(Long productId) {
        boolean isProductInOrder = orderItemRepository.existsByProductId(productId);

        if (isProductInOrder) {
            throw new IllegalStateException("Этот товар не может быть удален, так как он является частью существующего заказа.");
        }

        // Если товар не связан с заказами, удаляем его
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            productRepository.delete(product.get());
            return true;
        }
        return false;
    }

    @Override
    public Product saveProductWithImages(Product product, List<String> imageUrls) {
        List<ProductImage> productImages = new ArrayList<>();
        for (int i = 0; i < imageUrls.size(); i++) {
            ProductImage image = new ProductImage();
            image.setProduct(product);
            image.setImageUrl(imageUrls.get(i));
            image.setIsPrimary(i == 0); // Первое изображение будет главным
            productImages.add(image);
        }

        product.setImages(productImages);

        return productRepository.save(product);
    }

    @Override
    public void saveProductWithImagesAndSizes(Product product) {
        // Сохраняем продукт
        productRepository.save(product);

        // Сохраняем изображения
        productImageRepository.saveAll(product.getImages());

        // Сохраняем размеры
        productSizeRepository.saveAll(product.getSizes());
    }
}
