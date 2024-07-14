package com.picpay.challenge_backend.dtos;

import com.picpay.challenge_backend.domain.user.UserType;

import java.math.BigDecimal;

public record UserDTO(String firstName, String lastName, String document, BigDecimal balance, String email, String password, UserType userType) {
}
