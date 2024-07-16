package com.picpay.challenge_backend.services;

import com.picpay.challenge_backend.domain.transaction.Transaction;
import com.picpay.challenge_backend.domain.user.User;
import com.picpay.challenge_backend.dtos.TransactionDTO;
import com.picpay.challenge_backend.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

@Service
public class TransactionService {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private TransactionRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api.authorization.url}")
    private String authorizationUrl;

    public Transaction createTransaction(TransactionDTO transaction) {
        User payee = this.userService.findUserById(transaction.payee());
        User payer = this.userService.findUserById(transaction.payer());

        // Requirement: Validate Usertype and Balance
        this.userService.validateTransaction(payer, transaction.value());

        // Requirement: Consumes external auth service
        this.authorizeTransaction();

        // Requirement: Transfer contract
        Transaction newTransaction = new Transaction();
        newTransaction.setPayer(payer);
        newTransaction.setPayee(payee);
        newTransaction.setValue(transaction.value());
        newTransaction.setTimestamp(LocalDateTime.now());

        // Update balance
        payer.setBalance(payer.getBalance().subtract(transaction.value()));
        payee.setBalance(payee.getBalance().add(transaction.value()));

        this.repository.save(newTransaction);
        this.userService.saveUser(payer);
        this.userService.saveUser(payee);

        // Requirement: Consume external notification service
        this.notificationService.sendNotification(payer, "Transaction successfully sent");
        this.notificationService.sendNotification(payee, "Transaction successfully received");

        return newTransaction;
    }

    public void authorizeTransaction() {
        try {
            ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity(authorizationUrl, Map.class);
            Map<String, Object> responseBody = (Map<String, Object>) Objects.requireNonNull(authorizationResponse.getBody()).get("data");
            boolean isAuthorized = (boolean) responseBody.get("authorization");
            if (!isAuthorized) {
                throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
            }
        } catch (HttpClientErrorException e) {
            throw new HttpClientErrorException(e.getStatusCode(), "Transaction not authorized");
        } catch (Exception e) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error");
        }
    }
}
