package com.picpay.challenge_backend.repositories;

import com.picpay.challenge_backend.domain.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
