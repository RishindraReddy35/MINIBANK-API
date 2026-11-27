package minibankapi.demo.account;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record AmountRequest(  @NotNull
                              @DecimalMin(value = "0.01", message = "amount must be at least 0.01")
                              @Digits(integer = 13, fraction = 2, message = "amount can have at most 2 decimals")
                              BigDecimal amount )


{ }
