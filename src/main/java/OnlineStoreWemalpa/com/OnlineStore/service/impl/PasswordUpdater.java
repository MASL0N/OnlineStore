package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import OnlineStoreWemalpa.com.OnlineStore.model.User;
import OnlineStoreWemalpa.com.OnlineStore.repository.UserRepository;

import java.util.List;
// Этот код проходит по всем пользователям и шифрует пароли, если они еще не в хешированном виде.
@Component
public class PasswordUpdater implements CommandLineRunner {
    private final UserRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordUpdater(UserRepository userRepository) {
        this.repository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public void run(String... args) {
        List<User> users = repository.findAll();
        for (User user : users) {
            if (!user.getPassword().startsWith("$2a$")) { // Проверяем, зашифрован ли пароль
                user.setPassword(passwordEncoder.encode(user.getPassword())); // Хешируем старый пароль
                repository.save(user);
            }
        }
    }
}
