package OnlineStoreWemalpa.com.OnlineStore.controller;

import OnlineStoreWemalpa.com.OnlineStore.Configuration.MyUserDetails;
import OnlineStoreWemalpa.com.OnlineStore.model.Order;
import OnlineStoreWemalpa.com.OnlineStore.model.Payment;
import OnlineStoreWemalpa.com.OnlineStore.model.User;
import OnlineStoreWemalpa.com.OnlineStore.service.BasketService;
import OnlineStoreWemalpa.com.OnlineStore.service.PaymentService;
import OnlineStoreWemalpa.com.OnlineStore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/api/v1/payment")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final UserService userService;
    @GetMapping
    public String showCreateUserForm(@RequestParam("orderId") Long orderId,
                                     Model model,
                                     @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((MyUserDetails) userDetails).getUser();

        user = userService.findUserWithOrders(user.getUserId());

        Order order = user.getOrders().stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        model.addAttribute("user", user);
        model.addAttribute("order", order);
        model.addAttribute("payment", new Payment());
        return "payment";  // Thymeleaf-шаблон
    }

    @PostMapping
    public String createPayment(@ModelAttribute("payment") Payment payment,
                                @RequestParam("orderId") Long orderId,
                                @AuthenticationPrincipal UserDetails userDetails) {
        User user = ((MyUserDetails) userDetails).getUser();
        user = userService.findUserWithOrders(user.getUserId()); // Загружаем пользователя с заказами

        Order order = user.getOrders().stream()
                .filter(o -> o.getOrderId().equals(orderId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Заказ не найден"));

        payment.setPaymentMethod("Bank card");
        payment.setPaymentStatus("Completed");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setUser(user);
        payment.setAmount(order.getOrderPrice());
        payment.setCurrency("Ruble");
        payment.setOrder(order);

        paymentService.savePayment(payment);
        return "thank";
    }
}
