package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.User;
import OnlineStoreWemalpa.com.OnlineStore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import OnlineStoreWemalpa.com.OnlineStore.Configuration.MyUserDetails;

import java.util.Optional;
// Этот код — реализация сервиса загрузки пользователей
// (UserDetailsService) в Spring Security. Он отвечает за
// поиск пользователя в базе данных по имени (username) и
// преобразование его в объект UserDetails, который Spring
// Security использует для аутентификации.

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByFirstName(username);
        return user.map(MyUserDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
    }
}
