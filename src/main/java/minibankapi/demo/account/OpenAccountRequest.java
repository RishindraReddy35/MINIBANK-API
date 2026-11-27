package minibankapi.demo.account;



import jakarta.validation.constraints.NotNull;


public record OpenAccountRequest(@NotNull Long customerId, @NotNull AccountType type) { }