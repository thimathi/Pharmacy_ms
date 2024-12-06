package thimathi_manahara.OrderManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import thimathi_manahara.OrderManagement.Data.OrderDetails;
import thimathi_manahara.OrderManagement.Service.OrderService;

import java.util.List;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/orders/{customerid}")
    public List<OrderDetails> getOrdersByCustomerId(@PathVariable int customerid) {
        return orderService.getOrdersByCustomerId(customerid);
    }

    @GetMapping(path = "/orders")
    public List<OrderDetails> getOrderDetails() {
        return orderService.getAllOrders();
    }

    @GetMapping(path = "/orders/{orderId}")
    public OrderDetails getOrderDetailsById(@PathVariable int orderId) {
        return orderService.getOrderDetailsById(orderId);
    }

    @PostMapping(path = "/orders")
    public void addOrder(@RequestBody OrderDetails orderDetails) {

        orderService.addOrderDetails(orderDetails);
    }

    @PutMapping(path = "/orders/my-order")
    public OrderDetails orderDetails (@RequestBody OrderDetails orderDetails) {
        return orderService.updateOrder(orderDetails);
    }

    @DeleteMapping(path = "/orders/{orderId}")
    public void deleteOrder(@PathVariable Integer orderId) {
        if(orderId != null){
            orderService.deleteOrderDetails(orderId);
        }
        else {
            System.out.println("Order id is null");
        }
    }

}
