package com.example.main.repositories;

import com.example.main.entities.Token;
import com.example.main.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends JpaRepository<Token, Long> {
    List<Token> findByUser(User user);

    Token findByRefreshToken(String refreshToken);
}
