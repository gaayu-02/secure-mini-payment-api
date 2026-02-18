package com.secure.payment.service;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;
@Service
public class LoginAttemptService {
    private final Map<String, Integer> attemptsCache = new HashMap<>();
    private static final int MAX_ATTEMPTS = 5;
    public void loginFailed(String email) {
        attemptsCache.put(email, attemptsCache.getOrDefault(email, 0) + 1);
    }
    public void loginSucceeded(String email) {
        attemptsCache.remove(email);
    }
    public boolean isBlocked(String email) {
        return attemptsCache.getOrDefault(email, 0) >= MAX_ATTEMPTS;
    }
}