package OnlineStoreWemalpa.com.OnlineStore.controller;

import OnlineStoreWemalpa.com.OnlineStore.model.Product;
import OnlineStoreWemalpa.com.OnlineStore.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/catalog")
@AllArgsConstructor
public class CatalogController {
    private final ProductService productService;

    @GetMapping
    public String showCatalog(Model model) {
        List<Product> products = productService.findAllCustomProduct();
        model.addAttribute("products", products);
        return "catalog"; // Отображает catalog.html
    }
}
