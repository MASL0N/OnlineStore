package OnlineStoreWemalpa.com.OnlineStore.controller;


import OnlineStoreWemalpa.com.OnlineStore.Configuration.MyUserDetails;
import OnlineStoreWemalpa.com.OnlineStore.model.User;
import OnlineStoreWemalpa.com.OnlineStore.repository.UserRepository;
import OnlineStoreWemalpa.com.OnlineStore.service.OrderService;
import OnlineStoreWemalpa.com.OnlineStore.service.UserService;
import OnlineStoreWemalpa.com.OnlineStore.service.impl.OrderServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/account")
@AllArgsConstructor
public class AccountController {
    private  final OrderService service;
    private  final UserService userService;
    private final UserRepository userRepository;
    @GetMapping
    public String accountPage(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        User user = ((MyUserDetails) userDetails).getUser();
        model.addAttribute("user", user);
        model.addAttribute("activeOrders", service.getActiveOrders(user.getUserId()));
        model.addAttribute("orderHistory", service.getOrderHistory(user.getUserId()));
        return "my-account"; // Thymeleaf-шаблон "account.html"
    }

    @GetMapping("/edit")
    public String editOwnUser(@AuthenticationPrincipal MyUserDetails userDetails, Model model) {
        User user = userDetails.getUser();
        model.addAttribute("user", user);
        return "my-account-edit";
    }

    @PostMapping("/edit")
    public String updateOwnUser(@AuthenticationPrincipal MyUserDetails userDetails,
                                @ModelAttribute("user") User updatedUser) {
        User existingUser = userRepository.findById(userDetails.getUser().getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Обновляем только разрешённые поля
        existingUser.setFirstName(updatedUser.getFirstName());
        existingUser.setLastName(updatedUser.getLastName());
        existingUser.setCity(updatedUser.getCity());
        existingUser.setAddress(updatedUser.getAddress());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());
        existingUser.setPostIndex(updatedUser.getPostIndex());

        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        // Сохраняем обновлённого пользователя
        User updated = userRepository.save(existingUser);

        // Обновляем аутентификацию (обновляем данные в сессии)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails updatedDetails = new MyUserDetails(updated);
        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                updatedDetails,
                authentication.getCredentials(),
                updatedDetails.getAuthorities()
        );
        SecurityContextHolder.getContext().setAuthentication(newAuth);
        return "redirect:/api/v1/account";
    }

}
