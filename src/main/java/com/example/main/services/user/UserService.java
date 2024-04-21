package com.example.main.services.user;

import com.example.main.dtos.LoginDTO;
import com.example.main.entities.Token;
import com.example.main.entities.User;
import com.example.main.exceptions.DataNotFoundException;
import com.example.main.exceptions.ExpiredException;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.NotMatchException;
import com.example.main.filters.JwtTokenFilter;
import com.example.main.repositories.TokenRepository;
import com.example.main.repositories.UserRepository;
import com.example.main.utils.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtils jwtTokenUtils;
    @Override
    public User getUser(Long userId) throws IdNotFoundException {
        return userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new IdNotFoundException("User id : " + userId +" is not found")
                );
    }

    @Override
    public User getUserByToken(String token) throws ExpiredException, DataNotFoundException {
        if(jwtTokenUtils.isTokenExpired(token)){ // Kiểm tra điều kiện chạy đúng không
            throw new ExpiredException("Token is expired");
        }
        String username = jwtTokenUtils.getUsernameFromToken(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(
                        () -> new DataNotFoundException("Username not found !")
                );
        return user;
    }

    @Override
    public User getUserByRefreshToken(String refreshToken) throws DataNotFoundException, ExpiredException {
        Token token = tokenRepository.findByRefreshToken(refreshToken);
        return getUserByToken(token.getToken());
    }

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long userId) throws IdNotFoundException {
        User existingUser = userRepository
                .findById(userId)
                .orElseThrow(
                        () -> new IdNotFoundException("User id : " + userId +" is not found")
                );
        existingUser.setDeleted(true);
        userRepository.save(existingUser);
    }

    @Override
    public String login(LoginDTO loginDTO) throws NotMatchException {
        User existingUser = userRepository.findByUsername(loginDTO.getUsername())
                .orElseThrow(
                        () -> new NotMatchException("Username not found !")
                );

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            loginDTO.getUsername(), loginDTO.getPassword(),
            existingUser.getAuthorities()
        ); // Tạo authenticationToken đáng tin cậy

        authenticationManager.authenticate(authenticationToken); // Xác thực thông tin của authenticationToken

        return jwtTokenUtils.generateToken(existingUser);
    }
}
