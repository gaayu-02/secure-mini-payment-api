package com.secure.payment.controller;
import com.secure.payment.dto.PaymentRequest;
import com.secure.payment.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/payment")
    public ResponseEntity<?> makePayment(
            @Valid @RequestBody PaymentRequest request,
            HttpServletRequest httpRequest) {
        String userEmail = (String) httpRequest.getAttribute("email");

        if (userEmail == null) {
            return ResponseEntity.status(401).body("Unauthorized");
        }

        String response = paymentService.processPayment(request, userEmail);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/transactions")
public ResponseEntity<?> getTransactions(HttpServletRequest request) {
    String userEmail = (String) request.getAttribute("email");
    if (userEmail == null) {
        return ResponseEntity.status(401).body("Unauthorized");
    }
    return ResponseEntity.ok(
            paymentService.getUserTransactions(userEmail)
    );
}
}