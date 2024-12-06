package thimathi_manahara.CustomerManagement.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import thimathi_manahara.CustomerManagement.Data.CustomerDetails;
import thimathi_manahara.CustomerManagement.Data.CustomerRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDetails> getAllCustomers() {
        return customerRepository.findAll();
    }

    public CustomerDetails getCustomerDetailsById(int id) {
        Optional<CustomerDetails> customerDetails = customerRepository.findById(id);
        if (customerDetails.isPresent()) {
            return customerDetails.get();
        }
        return null;
    }

    public CustomerDetails saveCustomer(CustomerDetails customerDetails) {
        return customerRepository.save(customerDetails);
    }

    public CustomerDetails updateCustomer(CustomerDetails customerDetails) {
        Optional<CustomerDetails> existingUserOpt = customerRepository.findById(customerDetails.getId());
        if (existingUserOpt.isPresent()) {
            CustomerDetails existingUser = existingUserOpt.get();
            existingUser.setAddress(customerDetails.getAddress());
            existingUser.setPhoneNumber(customerDetails.getPhoneNumber());
            existingUser.setEmail(customerDetails.getEmail());
            return customerRepository.save(existingUser);
        } else {
            return null;
        }
    }


    public void deleteCustomer(Integer id) {
        if(id != null){
            customerRepository.deleteById(id);
        }
        else{
            System.out.println("id is null");
        }
    }
}
