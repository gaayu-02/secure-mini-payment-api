package com.secure.payment.dto;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
@Data
@Builder
public class TransactionResponse {
    private Double amount;
    private String currency;
    private String merchantId;
    private String status;
    private String referenceId;
    private LocalDateTime createdAt;
}