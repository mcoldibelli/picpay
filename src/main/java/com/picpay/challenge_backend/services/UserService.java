package com.picpay.challenge_backend.services;

import com.picpay.challenge_backend.domain.user.User;
import com.picpay.challenge_backend.domain.user.UserType;
import com.picpay.challenge_backend.dtos.UserDTO;
import com.picpay.challenge_backend.exceptions.InsufficientFundsException;
import com.picpay.challenge_backend.exceptions.NotAllowedException;
import com.picpay.challenge_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public User findUserById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new NoSuchElementException("User not found"));
    }

    public Optional<User> findUserByDocument(String document) {
        return this.repository.findUserByDocument(document);
    }

    public List<User> findAllUsers() {
        return this.repository.findAll();
    }

    public User createUser(UserDTO data) {
        if(data == null || data.document() == null || data.document().isEmpty()) {
            throw new IllegalArgumentException("UserDTO cannot be null or document cannot be empty");
        }

        findUserByDocument(data.document())
                .ifPresent(existingUser -> {
                    throw new NotAllowedException("User already exists");
                });

        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }

    public void validateTransaction(User payer, BigDecimal value) {
        if(payer.getUserType() == UserType.MERCHANT) {
            throw new NotAllowedException("User of type MERCHANT is not allowed to transfer money");
        }

        if(payer.getBalance().compareTo(value) < 0) {
            throw new InsufficientFundsException("Insufficient funds.");
        }
    }
}
