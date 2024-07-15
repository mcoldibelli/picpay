package com.picpay.challenge_backend.services;

import com.picpay.challenge_backend.domain.user.User;
import com.picpay.challenge_backend.domain.user.UserType;
import com.picpay.challenge_backend.dtos.UserDTO;
import com.picpay.challenge_backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository repository;

    public void validateTransaction(User payer, BigDecimal value) throws Exception {
        if(payer.getUserType() == UserType.MERCHANT) {
            throw new Exception("User of type MERCHANT not allowed to transfer.");
        }

        if(payer.getBalance().compareTo(value) < 0) {
            throw new Exception("Insufficient funds.");
        }
    }

    public User findUserById(Long id) throws Exception {
        return repository.findById(id).orElseThrow(()-> new Exception("User not found."));
    }

    public List<User> findAllUsers() {
        return this.repository.findAll();
    }

    public User createUser(UserDTO data) {
        User newUser = new User(data);
        this.saveUser(newUser);
        return newUser;
    }

    public void saveUser(User user) {
        this.repository.save(user);
    }
}
