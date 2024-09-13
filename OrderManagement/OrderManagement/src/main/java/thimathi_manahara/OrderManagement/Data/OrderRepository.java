package thimathi_manahara.OrderManagement.Data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<OrderDetails, Integer> {

    List<OrderDetails> findByUserid(int customerid);

}
