package OnlineStoreWemalpa.com.OnlineStore.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/order")
public class OrderController {
    @GetMapping("/thank")
    public String thankYouPage() {
        return "thank"; // Thymeleaf-шаблон "thank-you.html"
    }
}
