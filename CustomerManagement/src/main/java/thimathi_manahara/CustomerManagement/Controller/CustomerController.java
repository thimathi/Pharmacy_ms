package thimathi_manahara.CustomerManagement.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import thimathi_manahara.CustomerManagement.Data.CustomerDetails;
import thimathi_manahara.CustomerManagement.Service.CustomerService;

import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping(path = "/customers")
    public List<CustomerDetails> getCustomerDetails() {
        return customerService.getAllCustomers();
    }

    @GetMapping(path = "/customers/{customerId}")
    public CustomerDetails getCustomerDetailsById(@PathVariable int customerId) {
        return customerService.getCustomerDetailsById(customerId);
    }

    @PostMapping(path = "/customers")
    public CustomerDetails addCustomer(@RequestBody CustomerDetails customerDetails) {
        return customerService.saveCustomer(customerDetails);
    }

    @PutMapping(path = "/customers")
    public CustomerDetails updateCustomer(@RequestBody CustomerDetails customerDetails) {
        return customerService.updateCustomer(customerDetails);
    }

    @DeleteMapping(path = "/customers/{customerId}")
    public void deleteCustomer(@PathVariable Integer customerId) {
        if(customerId != null){
            customerService.deleteCustomer(customerId);
        }
        else{
            System.out.println("Customer id is null");
        }
    }
}