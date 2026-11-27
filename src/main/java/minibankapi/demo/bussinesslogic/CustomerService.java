package minibankapi.demo.bussinesslogic;

import minibankapi.demo.DTO.CustomerRequest;
import minibankapi.demo.DTO.CustomerResponse;
import minibankapi.demo.Exception.ResourceNotFoundException;
import java.util.List;

import minibankapi.demo.Customer.CustomerRepository;
import minibankapi.demo.entity.Customer;

import org.springframework.transaction.annotation.Transactional;

@org.springframework.stereotype.Service
public class CustomerService {

    private final CustomerRepository repo;

    public CustomerService(CustomerRepository repo) {
        this.repo = repo;
    }

    public CustomerResponse create(CustomerRequest req) {
        Customer saved = repo.save(new Customer(req.name(), req.email()));
        return CustomerResponse.from(saved);
    }

    public CustomerResponse get(Long id) {
        return CustomerResponse.from(find(id));
    }

    public List<CustomerResponse> list() {
        return repo.findAll().stream().map(CustomerResponse::from).toList();
    }
    @Transactional
    public CustomerResponse update(Long id, CustomerRequest req) {
        Customer c = find(id);
        c.setName(req.name());
        c.setEmail(req.email());
        return CustomerResponse.from(c);   // no save() needed: JPA saves changes at commit
    }

    public void delete(Long id) {
        repo.delete(find(id));
    }

    public Customer find(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer " + id + " not found"));
    }


}
