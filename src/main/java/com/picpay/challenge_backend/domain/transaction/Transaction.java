package com.picpay.challenge_backend.domain.transaction;

import com.picpay.challenge_backend.domain.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Table(name = "transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal value;
    @ManyToOne
    @JoinColumn(name="payer_id")
    private User payer;
    @ManyToOne
    @JoinColumn(name="payee_id")
    private User payee;
    private LocalDateTime timestamp;
}
