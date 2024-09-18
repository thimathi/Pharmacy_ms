package the.boys.pharmacy_management_system.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import the.boys.pharmacy_management_system.Data.UserDetails;
import the.boys.pharmacy_management_system.Services.UserService;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path = "/user")
    public List<UserDetails> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping(path = "/user/{userId}")
    public UserDetails getUserDetails(@PathVariable int userId) {
        return userService.getUserDetails(userId);
    }

    @PostMapping(path = "/user")
    public UserDetails createUser(@RequestBody UserDetails userDetails) {
        return userService.createUser(userDetails);
    }

    @PutMapping(path = "/user")
    public UserDetails updateUser(@RequestBody UserDetails userDetails) {
        return userService.updateUser(userDetails);
    }

    @DeleteMapping(path = "/user/{userId}")
    public void deleteUser(@PathVariable Integer userId) {
        if(userId != null){
            userService.deleteUser(userId);
        }
        else{
            System.out.println("User id is null");
        }
    }

}
