package OnlineStoreWemalpa.com.OnlineStore.controller;

import OnlineStoreWemalpa.com.OnlineStore.model.User;
import OnlineStoreWemalpa.com.OnlineStore.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/v1/registration")
@AllArgsConstructor
public class RegistrationController {
    final private UserService userService;

    @GetMapping
    public String showCreateUserForm(Model model,
                                     @RequestParam(value = "error", required = false) String error) {
        model.addAttribute("user", new User());
        if ("emailExists".equals(error)) {
            model.addAttribute("errorMessage", "Пользователь с таким email уже существует.");
        }
        return "user-registration";
    }

    @PostMapping("/user-create")
    public String createUser(@ModelAttribute("user") User user) {

        if (userService.existsByEmail(user.getEmail())) {
            // Если пользователь с таким email уже существует, возвращаем ошибку
            return "redirect:/api/v1/registration?error=emailExists";
        }

        user.setRole("USER");
        userService.createUser(user);  // Вызываем сервис для сохранения пользователя
        return "redirect:/api/v1/login";  // Перенаправляем на страницу с пользователями
    }
}
