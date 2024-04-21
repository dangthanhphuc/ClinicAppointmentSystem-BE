package com.example.main.services.token;

import com.example.main.entities.Token;
import com.example.main.entities.User;
import com.example.main.exceptions.DataNotFoundException;
import com.example.main.exceptions.ExpiredException;
import com.example.main.repositories.TokenRepository;
import com.example.main.utils.JwtTokenUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TokenService implements ITokenService{

    private final TokenRepository tokenRepository;
    private final JwtTokenUtils jwtTokenUtils;

    private static final int MAX_TOKENS = 3;


    @Value("${jwt.expiration}")
    private int expiration;
    @Value("${jwt.expiration-refresh}")
    private int expirationRefresh;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Token addToken(User user, String token, boolean isMobile) {
        List<Token> userTokens = tokenRepository.findByUser(user);

        if(userTokens.size() >= MAX_TOKENS){
            boolean hasNonMobilToken = !userTokens.stream().allMatch(Token::isMobile);
            Token tokenToDelete;
            if(hasNonMobilToken) {
                tokenToDelete = userTokens.stream()
                        .filter( t -> !t.isMobile())
                        .toList().getFirst();
            }
            else
            {
                // Nếu tất cả các token đều là mobile token
                tokenToDelete = userTokens.getFirst();
            }
            tokenRepository.delete(tokenToDelete);
        }

        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(expiration);
        LocalDateTime expirationRefreshDate = LocalDateTime.now().plusSeconds(expirationRefresh);

        Token newToken = Token.builder()
                .user(user)
                .token(token)
                .tokenType("Bearer")
                .refreshToken(UUID.randomUUID().toString())
                .expirationDate(expirationDate)
                .expirationRefreshDate(expirationRefreshDate)
                .revoked(false)
                .expired(false)
                .isMobile(isMobile)
                .build();

        return tokenRepository.save(newToken);
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public Token refreshToken(User user, String refreshToken) throws DataNotFoundException, ExpiredException {
        Token existingToken = tokenRepository.findByRefreshToken(refreshToken);

        if(existingToken == null){
            throw new DataNotFoundException("Cannot found token by refresh token " + refreshToken );
        }

        if(existingToken.getExpirationRefreshDate().isBefore(LocalDateTime.now())) {
            throw new ExpiredException("Refresh token is expired !");
        }

        LocalDateTime expirationDate = LocalDateTime.now().plusSeconds(expiration);
        LocalDateTime expirationRefreshDate = LocalDateTime.now().plusSeconds(expirationRefresh);

        // Tạo token mới
        String token = jwtTokenUtils.generateToken(user);

        // Đặt lại giá trị cho token cà refresh token
        existingToken.setToken(token);
        existingToken.setRefreshToken(UUID.randomUUID().toString());
        existingToken.setExpirationDate(expirationDate);
        existingToken.setExpirationRefreshDate(expirationRefreshDate);

        return existingToken;
    }
}
