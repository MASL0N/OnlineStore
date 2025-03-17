package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.*;
import OnlineStoreWemalpa.com.OnlineStore.repository.*;
import OnlineStoreWemalpa.com.OnlineStore.service.CastomizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
public class CustomizationServiceImpl implements CastomizationService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MaterialRepository materialRepository;

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private PrintTypeRepository printTypeRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Override
    @Transactional
    public Product customizeProduct(Long productId, Long materialId, Long colorId, Long printTypeId) {
        // Получаем оригинальный продукт
        Product originalProduct = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        // Получаем новые параметры
        Material material = materialRepository.findById(materialId)
                .orElseThrow(() -> new IllegalArgumentException("Material not found"));

        Color color = colorRepository.findById(colorId)
                .orElseThrow(() -> new IllegalArgumentException("Color not found"));

        PrintType printType = printTypeRepository.findById(printTypeId)
                .orElseThrow(() -> new IllegalArgumentException("PrintType not found"));

        // Создаём новый объект `Product`, копируя данные
        Product newProduct = new Product();
        newProduct.setName(originalProduct.getName() + " (Custom)");
        newProduct.setDecsription(originalProduct.getDecsription());
        newProduct.setPrice(originalProduct.getPrice()); // Можно обновлять цену, если кастомизация меняет стоимость
        newProduct.setGender(originalProduct.getGender());
        newProduct.setClothingType(originalProduct.getClothingType());

        // Применяем кастомные параметры
        newProduct.setMaterial(material);
        newProduct.setColor(color);
        newProduct.setPrintType(printType);
        newProduct.setCustom(true);

        // Сохраняем новый продукт в БД
        newProduct = productRepository.save(newProduct);  // Сохраняем новый продукт в базу данных

        // Получаем primaryImage из оригинального товара
        ProductImage originalPrimaryImage = originalProduct.getPrimaryImage();

        if (originalPrimaryImage != null) {
            // Создаем новое изображение для кастомизированного продукта
            ProductImage newProductImage = new ProductImage();
            newProductImage.setProduct(newProduct);  // Привязываем изображение к новому продукту
            newProductImage.setImageUrl(originalPrimaryImage.getImageUrl());  // Копируем URL изображения
            newProductImage.setIsPrimary(true);  // Устанавливаем это изображение как основное

            // Сохраняем новое изображение в базе данных
            productImageRepository.save(newProductImage);
        }

        // Сохраняем новый продукт в БД
        return productRepository.save(newProduct);
    }
}
