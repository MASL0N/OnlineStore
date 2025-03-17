package OnlineStoreWemalpa.com.OnlineStore.repository;

import OnlineStoreWemalpa.com.OnlineStore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    void deleteByEmail(String email);
    Optional<User> findByFirstName(String firstName);
    boolean existsByEmail(String email);
}
