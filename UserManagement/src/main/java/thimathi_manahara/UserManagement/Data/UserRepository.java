package thimathi_manahara.UserManagement.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {
    Optional<UserDetails> findByUsernameAndPassword(String username, String password);
}
