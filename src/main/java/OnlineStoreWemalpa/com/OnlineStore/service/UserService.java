package OnlineStoreWemalpa.com.OnlineStore.service;

import OnlineStoreWemalpa.com.OnlineStore.model.Order;
import OnlineStoreWemalpa.com.OnlineStore.model.User;

import java.util.List;

public interface UserService {
    List<User> findAllUser();
    User saveUser(User user);
    User updateUser(User user);
    void deleteUser(String email);
    User findById(Long id);
    void deleteUserById(Long id);
    void createUser(User user);
    boolean existsByEmail(String email);
}
