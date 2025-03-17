package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.ProductImage;
import OnlineStoreWemalpa.com.OnlineStore.repository.ProductImageRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.ProductImageService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class ProductImageServiceImpl implements ProductImageService {
    private final ProductImageRepository repository;
    @Override
    public List<ProductImage> findAllProductImage() {
        return repository.findAll();
    }

    @Override
    public ProductImage saveProductImage(ProductImage productImage) {
        return repository.save(productImage);
    }

    @Override
    public ProductImage updateProductImage(ProductImage productImage) {
        return repository.save(productImage);
    }

    @Override
    public void deleteProductImage(Long productImage) {
        repository.deleteById(productImage);
    }

}
