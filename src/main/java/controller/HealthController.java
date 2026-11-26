package controller;

import DTO.CustomerRequest;
import DTO.CustomerResponse;
import jakarta.validation.Valid;
import java.util.List;

import bussinesslogic.CustomerService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class HealthController {


    private final CustomerService service;

    public HealthController(CustomerService service) {
        this.service = service;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CustomerResponse create(@Valid @RequestBody CustomerRequest req) {
        return service.create(req);
    }

    @GetMapping("/{id}")
    public CustomerResponse get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @GetMapping
    public List<CustomerResponse> list() {
        return service.list();
    }

    @PutMapping("/{id}")
    public CustomerResponse update(@PathVariable("id") Long id,
                                   @Valid @RequestBody CustomerRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }
}
