package OnlineStoreWemalpa.com.OnlineStore.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "city")
    private String city;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "post_index")
    private String postIndex;

    @Column(name = "role", nullable = false)
    private String role;

    @JsonIgnore  // Исключаем из JSON, чтобы избежать циклической зависимости
    @ToString.Exclude  // Lombok не будет включать это поле в toString()
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    @Override
    public String toString() {
        return "User{id=" + userId + ", email='" + email + "', firstName='" + firstName + "'}";
    }
}
