package minibankapi.demo.account;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    private final AccountService service;

    public AccountController(AccountService service) {
        this.service = service;
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse open(@Valid @RequestBody OpenAccountRequest req) {
        return service.open(req);
    }

    @GetMapping("/{id}")
    public AccountResponse get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @PostMapping("/{id}/deposit")
    public AccountResponse deposit(@PathVariable("id") Long id,
                                   @Valid @RequestBody AmountRequest req) {
        return service.deposit(id, req.amount());
    }

    @PostMapping("/{id}/withdraw")
    public AccountResponse withdraw(@PathVariable("id") Long id,
                                    @Valid @RequestBody AmountRequest req) {
        return service.withdraw(id, req.amount());
    }


}
