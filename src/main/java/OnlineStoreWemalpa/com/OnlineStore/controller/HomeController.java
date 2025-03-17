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
@RequestMapping("/api/v1/home")
@AllArgsConstructor
public class HomeController {
    private final ProductService service;

    // Метод для отображения страницы с продуктами
    @GetMapping
    public String showProducts(Model model) {
        List<Product> products = service.findAllCustomProduct(); // Получаем список продуктов
        model.addAttribute("products", products); // Передаем данные в модель
        return "home"; // Возвращаем имя шаблона
    }
}

