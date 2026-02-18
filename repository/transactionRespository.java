package com.secure.payment.repository;
import com.secure.payment.entity.Transaction;
import com.secure.payment.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    Optional<Transaction> findByUserAndReferenceId(User user, String referenceId);
    List<Transaction> findByUser(User user);
}