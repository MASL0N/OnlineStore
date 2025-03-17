package OnlineStoreWemalpa.com.OnlineStore.Configuration;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // Получаем список ролей пользователя
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        // Проверяем, есть ли у пользователя роль администратора
        boolean isAdmin = authorities.stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));

        if (isAdmin) {
            response.sendRedirect("/api/v1/admin/product-admin"); // Перенаправление админа
        } else {
            response.sendRedirect("/api/v1/home"); // Перенаправление обычного пользователя
        }
    }
}
