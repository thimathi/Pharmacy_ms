package the.boys.pharmacy_management_system.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import the.boys.pharmacy_management_system.Data.UserDetails;
import the.boys.pharmacy_management_system.Data.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public List<UserDetails> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDetails getUserDetails(int id) {
        Optional<UserDetails> userDetails = userRepository.findById(id);
        if(userDetails.isPresent()) {
            return userDetails.get();
        }
        return null;
    }

    public UserDetails createUser(UserDetails userDetailsObject) {
        return userRepository.save(userDetailsObject);
    }

    public UserDetails updateUser(UserDetails userDetailsObject) {
        return userRepository.save(userDetailsObject);
    }

    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }
}
