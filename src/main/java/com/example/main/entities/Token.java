package com.example.main.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;

    @Column(name = "token", length = 512, nullable = false)
    private String token;

    @Column(name = "refresh_token" ,nullable = false)
    private String refreshToken;

    @Column(name = "token_type", length = 50, nullable = false)
    private String tokenType;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "expiration_refresh_token", nullable = false)
    private LocalDateTime expirationRefreshDate;

    @Column(name = "is_mobile", columnDefinition = "TINYINT(1)", nullable = false)
    private boolean isMobile;

    private boolean revoked;
    private boolean expired;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
