package OnlineStoreWemalpa.com.OnlineStore.controller;

import OnlineStoreWemalpa.com.OnlineStore.Configuration.MyUserDetails;
import OnlineStoreWemalpa.com.OnlineStore.model.Basket;
import OnlineStoreWemalpa.com.OnlineStore.model.User;

import OnlineStoreWemalpa.com.OnlineStore.service.BasketService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/api/v1/basket")
@AllArgsConstructor
public class BasketController {

    private final BasketService basketService;

    // Отображение корзины
    @GetMapping
    public String basketPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = ((MyUserDetails) userDetails).getUser();
        Basket basket = basketService.getBasketByUser(user);

        model.addAttribute("basket", basket);
        model.addAttribute("basketItems", basket.getBasketItems()); // Передача списка товаров
        model.addAttribute("basketTotal", basket.getTotalPrice());  // Общая сумма

        return "basket"; // Thymeleaf-шаблон "basket.html"
    }

    // Обновить количество товара в корзине
    @PostMapping("/update")
    public String updateBasket(@AuthenticationPrincipal UserDetails userDetails,
                               @RequestParam Long basketItemId,
                               @RequestParam Integer quantity) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        basketService.updateItemQuantity(basketItemId, quantity);
        return "redirect:/api/v1/basket";
    }

    // Удалить товар из корзины
    @PostMapping("/remove")
    public String removeItem(@AuthenticationPrincipal UserDetails userDetails,
                             @RequestParam Long basketItemId) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        basketService.removeItem(basketItemId);
        return "redirect:/api/v1/basket";
    }

    // Оформить заказ
    @PostMapping("/checkout")
    public String checkout(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return "redirect:/login";
        }
        User user = ((MyUserDetails) userDetails).getUser();
        basketService.checkout(user);
        return "redirect:/api/v1/order/thank";
    }

    @PostMapping("/add")
    public String addToCart(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam("productId") Long productId,
                            @RequestParam("sizeId") Long sizeId,
                            @RequestParam String primaryImage,
                            @RequestParam(value = "redirect", required = false) String redirectUrl,
                            RedirectAttributes redirectAttributes) {
        if (userDetails == null) {
            redirectAttributes.addFlashAttribute("error", "Вы не авторизованы!");
            return "redirect:/login" + (redirectUrl != null ? "?redirect=" + redirectUrl : "");
        }

        User user = ((MyUserDetails) userDetails).getUser(); // Преобразование UserDetails в User
        boolean added = basketService.addToBasket(user, productId, sizeId, primaryImage);

        if (!added) {
            redirectAttributes.addFlashAttribute("error", "Товар не найден или размер отсутствует!");
        } else {
            redirectAttributes.addFlashAttribute("success", "Товар добавлен в корзину!");
        }
        return "redirect:/api/v1/home";
    }

}
