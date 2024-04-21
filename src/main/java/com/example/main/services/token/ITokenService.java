package com.example.main.services.token;

import com.example.main.entities.Token;
import com.example.main.entities.User;
import com.example.main.exceptions.DataNotFoundException;
import com.example.main.exceptions.ExpiredException;

import java.util.List;

public interface ITokenService {
    Token addToken(User user, String token, boolean isMobile);
    Token refreshToken(User user, String refreshToken) throws DataNotFoundException, ExpiredException;

}
