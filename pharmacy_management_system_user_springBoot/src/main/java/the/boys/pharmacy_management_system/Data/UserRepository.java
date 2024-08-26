package the.boys.pharmacy_management_system.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails, Integer> {
    // JpaRepository provides basic CRUD operations
    // Integer specifies the type of primary key for User entity
}