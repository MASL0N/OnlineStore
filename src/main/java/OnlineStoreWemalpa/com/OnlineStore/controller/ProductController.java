package OnlineStoreWemalpa.com.OnlineStore.controller;

import OnlineStoreWemalpa.com.OnlineStore.model.Product;

import OnlineStoreWemalpa.com.OnlineStore.model.ProductImage;
import OnlineStoreWemalpa.com.OnlineStore.service.ProductImageService;
import OnlineStoreWemalpa.com.OnlineStore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/v1/product")
@AllArgsConstructor
public class ProductController {
    private ProductService service; // Сервис, который содержит метод getPrimaryImage

    @GetMapping("/{id}")
    public String getProductDetails(@PathVariable Long id, Model model) {
        Product product = service.findById(id);
        if (product == null) {
            return "redirect:/api/v1/home"; // Если продукт не найден, редирект на главную
        }

        model.addAttribute("product", product);

        ProductImage primaryImage = product.getPrimaryImage();
        model.addAttribute("primaryImage", primaryImage);

        return "product-details"; // Отображение страницы продукта
    }

    @PostMapping("save_product")
    public Product saveProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PutMapping("update_product")
    public Product updateProduct(@RequestBody Product product) {
        return service.updateProduct(product);
    }

    @DeleteMapping("delete_product/{id}")
    public void deleteProduct(@PathVariable Long id) {
        service.deleteProduct(id);
    }
}

