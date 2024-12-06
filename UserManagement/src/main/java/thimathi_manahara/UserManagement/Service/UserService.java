package thimathi_manahara.UserManagement.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thimathi_manahara.UserManagement.Data.UserDetails;
import thimathi_manahara.UserManagement.Data.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDetails> getAllUsers() {
        return userRepository.findAll();
    }

    public UserDetails getUserDetailsById(int id) {
        Optional<UserDetails> userDetails = userRepository.findById(id);
        if(userDetails.isPresent()){
            return userDetails.get();
        }
        return null;
    }

    public UserDetails addUser(UserDetails userDetails) {
        return userRepository.save(userDetails);
    }

    public UserDetails updateUser(UserDetails userDetails) {
        Optional<UserDetails> existingUserOpt = userRepository.findById(userDetails.getId());
        if(existingUserOpt.isPresent()){
            UserDetails existingUser = existingUserOpt.get();
            existingUser.setUserType(userDetails.getUserType());
            existingUser.setAddress(userDetails.getAddress());
            existingUser.setMobile(userDetails.getMobile());
            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }

    public void deleteUser(Integer id) {
        if(id != null){
            userRepository.deleteById(id);
        }
        else{
            System.out.println("Id is null");
        }
    }

    public Map<String, String> login (String username, String password) {
        Optional<UserDetails> userOpt = userRepository.findByUsernameAndPassword(username, password);
        if (userOpt.isPresent()) {
            UserDetails user = userOpt.get();
            Map<String, String> response = new HashMap<>();
            response.put("id", String.valueOf(user.getId()));
            response.put("type", String.valueOf(user.getUserType()));
            return response;
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("id", null);
            response.put("type", null);
            return response;
        }
    }

    public UserDetails updateMyAccount(UserDetails userDetails) {
        Optional<UserDetails> existingUserOpt = userRepository.findById(userDetails.getId());
        if(existingUserOpt.isPresent()){
            UserDetails existingUser = existingUserOpt.get();
            existingUser.setAddress(userDetails.getAddress());
            existingUser.setMobile(userDetails.getMobile());
            existingUser.setUsername(userDetails.getUsername());
            existingUser.setPassword(userDetails.getPassword());

            return userRepository.save(existingUser);
        } else {
            return null;
        }
    }
}
