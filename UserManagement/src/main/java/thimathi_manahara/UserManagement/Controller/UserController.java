package thimathi_manahara.UserManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import thimathi_manahara.UserManagement.Data.UserDetails;
import thimathi_manahara.UserManagement.Service.UserService;

import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(path = "/users")
    public List<UserDetails> getUserDetails() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/users/{userId}")
    public UserDetails getUserDetailsById(@PathVariable int userId) {
        return userService.getUserDetailsById(userId);
    }

    @PostMapping(path = "/users")
    public UserDetails createUser(@RequestBody UserDetails userDetails) {
        return userService.addUser(userDetails);
    }

    @PutMapping(path = "/users")
    public UserDetails updateUser(@RequestBody UserDetails userDetails) {
        return userService.updateUser(userDetails);
    }

    @PutMapping(path = "/users/my-account")
    public UserDetails updateMyAccount(@RequestBody UserDetails userDetails) {
        return userService.updateMyAccount(userDetails);
    }

    @DeleteMapping(path = "/users/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        if(userId != null){
            userService.deleteUser(userId);
        }
        else{
            System.out.println("user id is null");
        }
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("uname");
        String password = loginData.get("pword");
        return userService.login(username, password);
    }
    
}