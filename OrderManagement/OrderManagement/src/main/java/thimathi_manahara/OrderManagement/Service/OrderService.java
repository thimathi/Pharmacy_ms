package thimathi_manahara.OrderManagement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thimathi_manahara.OrderManagement.Data.OrderDetails;
import thimathi_manahara.OrderManagement.Data.OrderRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    OrderRepository orderRepository;

    public List<OrderDetails> getAllOrders() {
        return orderRepository.findAll();
    }

    public OrderDetails getOrderDetailsById(int orderId) {
        Optional<OrderDetails> orderDetails = orderRepository.findById(orderId);
        if(orderDetails.isPresent()){
            return orderDetails.get();
        }
        return null;
    }

    public OrderDetails addOrderDetails(OrderDetails orderDetails) {
        return orderRepository.save(orderDetails);
    }

    public OrderDetails updateOrder(OrderDetails orderDetails) {
        Optional<OrderDetails> existingOrderDetails = orderRepository.findById(orderDetails.getOrder_id());
        if(existingOrderDetails.isPresent()){
            OrderDetails existingOrderDetail = existingOrderDetails.get();
            existingOrderDetail.setOrder_id(orderDetails.getOrder_id());
            existingOrderDetail.setOrder_date(orderDetails.getOrder_date());
            return orderRepository.save(existingOrderDetail);
        }
        else {
            return null;
        }}

    public void deleteOrderDetails(Integer orderId) {
        if(orderId != null){
            orderRepository.deleteById(orderId);
        }
        else {
            System.out.println("Order id is null");
        }
    }

    public List<OrderDetails> getOrdersByCustomerId(int customerid) {

        return orderRepository.findByUserid(customerid);
    }

}
