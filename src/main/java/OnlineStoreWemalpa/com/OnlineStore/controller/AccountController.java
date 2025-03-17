package OnlineStoreWemalpa.com.OnlineStore.controller;


import OnlineStoreWemalpa.com.OnlineStore.Configuration.MyUserDetails;
import OnlineStoreWemalpa.com.OnlineStore.model.User;
import OnlineStoreWemalpa.com.OnlineStore.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1/account")
@AllArgsConstructor
public class AccountController {
    OrderServiceImpl service;
    @GetMapping
    public String accountPage(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        User user = ((MyUserDetails) userDetails).getUser();
        model.addAttribute("user", user);
        model.addAttribute("activeOrders", service.getActiveOrders(user.getUserId()));
        model.addAttribute("orderHistory", service.getOrderHistory(user.getUserId()));
        return "my-account"; // Thymeleaf-шаблон "account.html"
    }
}
