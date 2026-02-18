package com.secure.payment.service;
import com.secure.payment.dto.PaymentRequest;
import com.secure.payment.entity.Transaction;
import com.secure.payment.entity.User;
import com.secure.payment.repository.TransactionRepository;
import com.secure.payment.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    public String processPayment(PaymentRequest request, String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (request.getAmount() > 100000) {
            log.warn("Suspicious high-value transaction attempt by {}", userEmail);
            throw new RuntimeException("Transaction exceeds allowed limit");
        }
        boolean alreadyExists = transactionRepository
                .findByUserAndReferenceId(user, request.getReferenceId())
                .isPresent();
        if (alreadyExists) {
            log.warn("Duplicate payment attempt detected for referenceId: {} by user: {}",
                    request.getReferenceId(), userEmail);
            throw new RuntimeException("Duplicate payment detected");
        }
        Transaction transaction = Transaction.builder()
                .amount(request.getAmount())
                .currency(request.getCurrency().toUpperCase())
                .merchantId(request.getMerchantId())
                .status("SUCCESS") // In real world, this comes from payment gateway
                .referenceId(request.getReferenceId())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
        transactionRepository.save(transaction);
        log.info("Payment processed successfully for user: {}", userEmail);
        return "Payment processed successfully";
    }
    public List<TransactionResponse> getUserTransactions(String userEmail) {

    User user = userRepository.findByEmail(userEmail)
            .orElseThrow(() -> new RuntimeException("User not found"));

    List<Transaction> transactions = transactionRepository.findByUser(user);

    log.info("Transaction history accessed by user: {}", userEmail);

    return transactions.stream()
            .map(tx -> TransactionResponse.builder()
                    .amount(tx.getAmount())
                    .currency(tx.getCurrency())
                    .merchantId(tx.getMerchantId())
                    .status(tx.getStatus())
                    .referenceId(tx.getReferenceId())
                    .createdAt(tx.getCreatedAt())
                    .build())
            .toList();
}
}