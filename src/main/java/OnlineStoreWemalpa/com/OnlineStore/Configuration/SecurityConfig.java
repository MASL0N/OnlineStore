package OnlineStoreWemalpa.com.OnlineStore.Configuration;

import OnlineStoreWemalpa.com.OnlineStore.service.impl.MyUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

    // Внедрение зависимостей
    public SecurityConfig(CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler) {
        this.customAuthenticationSuccessHandler = customAuthenticationSuccessHandler;
    }

    // Сервис для загрузки пользователя
    @Bean
    public UserDetailsService userDetailsService() {
        return new MyUserDetailsService();
    }

    // Описание авторизации
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // Отключение CSRF (если не API)
                .authorizeRequests(auth -> auth
                        .requestMatchers("/api/v1/home", "/api/v1/product/**", "/img/**").permitAll()
                        .requestMatchers("/styles.css", "/js/**", "/img/**", "/static/**").permitAll()
                        .requestMatchers("api/v1/admin/**").hasRole("ADMIN")
                        .requestMatchers("api/v1/home/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(customAuthenticationSuccessHandler) // Настройка кастомного обработчика
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/api/v1/home")
                        .invalidateHttpSession(true) // Закрываем сессию при выходе
                        .deleteCookies("JSESSIONID") // Удаляем куки сессии (можно убрать для теста)
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Управление сессией
                        .maximumSessions(1)
                        .expiredUrl("/login?expired=true")
                )
                .build();
    }

    // Определяем AuthenticationProvider с использованием кастомного UserDetailsService
    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService()); // Берем пользователей из MyUserDetailsService
        provider.setPasswordEncoder(passwordEncoder()); // Указываем способ шифрования паролей
        return provider;
    }

    // Шифрование паролей с использованием BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}