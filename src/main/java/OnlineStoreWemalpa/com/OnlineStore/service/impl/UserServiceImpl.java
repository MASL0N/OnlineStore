package OnlineStoreWemalpa.com.OnlineStore.service.impl;

import OnlineStoreWemalpa.com.OnlineStore.model.Order;
import OnlineStoreWemalpa.com.OnlineStore.service.UserService;
import OnlineStoreWemalpa.com.OnlineStore.model.User;
import OnlineStoreWemalpa.com.OnlineStore.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@AllArgsConstructor
@Primary
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<User> findAllUser() {
        return repository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return repository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return repository.save(user);
    }

    @Override
    public void deleteUser(String email) {
        repository.deleteByEmail(email);
    }

    @Override
    public User findById(Long id) { return repository.findById(id).orElse(null); }

    @Override
    public void deleteUserById(Long id) { repository.deleteById(id); }

    @Override
    public void createUser(User user) {
        repository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    public void addUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        repository.save(user);
    }
}
