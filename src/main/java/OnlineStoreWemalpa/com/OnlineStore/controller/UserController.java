package OnlineStoreWemalpa.com.OnlineStore.controller;

import org.springframework.http.HttpHeaders;
import OnlineStoreWemalpa.com.OnlineStore.service.UserService;
import OnlineStoreWemalpa.com.OnlineStore.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/api/v1/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @GetMapping()
    public List<User> findAllUser() {
        //todo
        return service.findAllUser();
    }

    @PostMapping("save_user")
    public User saveUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    @PutMapping("update_user")
    public User updateStudent(@RequestBody User user) {
        return service.updateUser(user);
    }

    @DeleteMapping("delete_user/{email}")
    public void deleteStudent(@PathVariable String email) {
        service.deleteUser(email);
    }
}
