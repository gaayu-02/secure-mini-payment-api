package com.secure.payment.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
@Data
public class PaymentRequest {
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private Double amount;
    @NotBlank(message = "Currency is required")
    private String currency;
    @NotBlank(message = "Merchant ID is required")
    private String merchantId;
    @NotBlank(message = "Reference ID required")
    private String referenceId;
}