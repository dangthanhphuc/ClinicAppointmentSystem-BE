package com.example.main.controllers;

import com.example.main.dtos.LoginDTO;
import com.example.main.entities.Token;
import com.example.main.entities.User;
import com.example.main.exceptions.DataNotFoundException;
import com.example.main.exceptions.ExpiredException;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.NotMatchException;
import com.example.main.responses.LoginResponse;
import com.example.main.responses.ResponseObject;
import com.example.main.responses.UserResponse;
import com.example.main.services.token.ITokenService;
import com.example.main.services.user.IUserService;
import com.example.main.utils.JwtTokenUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RequiredArgsConstructor
@RequestMapping("${api.prefix}/users") // Admin or Manager
@RestController
public class UserController {
    private final IUserService userService;
    private final ITokenService tokenService;

    private final JwtTokenUtils jwtTokenUtils;

    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER') or hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("{userId}") // getBykeywork
    public ResponseEntity<ResponseObject> getUser(
            @PathVariable Long userId
    ) throws IdNotFoundException {
        User user = userService.getUser(userId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("User got successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(UserResponse.fromUser(user))
                        .build()
        );
    }


    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER') or hasRole('ROLE_ADMINISTRATOR')")
    @GetMapping("")
    public ResponseEntity<ResponseObject> getUsers(){
        List<User> users = userService.getUsers();
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Users got successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(users.stream().map(UserResponse::fromUser))
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_CLINIC_MANAGER') or hasRole('ROLE_ADMINISTRATOR')")
    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<ResponseObject> blockOrEnable(
            @PathVariable Long userId
    ) throws IdNotFoundException {
        userService.deleteUser(userId);
        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("Deleted user successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data("Deleted user successfully !")
                        .build()
        );
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDTO loginDTO,
            HttpServletRequest request
    ) throws NotMatchException, DataNotFoundException, ExpiredException {
        String token = userService.login(loginDTO);

        String userAgent = request.getHeader("User-Agent");
        User userDetail = userService.getUserByToken(token);
        Token newToken = tokenService.addToken(
                userDetail,  token,
                userAgent.equalsIgnoreCase("mobile")
        );

        LoginResponse loginResponse = LoginResponse.builder()
                .token(newToken.getToken())
                .refreshToken(newToken.getRefreshToken())
                .tokenType(newToken.getTokenType())
                .username(userDetail.getUsername())
                .roles(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("User login successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(loginResponse)
                        .build()
        );
    }

    @PostMapping("/refreshToken/{refresh_token}")
    public ResponseEntity<?> refreshToken(
            @PathVariable(name = "refresh_token") String refreshToken
    ) throws DataNotFoundException, ExpiredException {

        User userDetail = userService.getUserByRefreshToken(refreshToken);
        Token token = tokenService.refreshToken(userDetail, refreshToken);

        LoginResponse loginResponse = LoginResponse.builder()
                .token(token.getToken())
                .refreshToken(token.getRefreshToken())
                .tokenType(token.getTokenType())
                .username(userDetail.getUsername())
                .roles(userDetail.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();

        return ResponseEntity.ok().body(
                ResponseObject.builder()
                        .timeStamp(LocalDateTime.now())
                        .message("User refresh token successfully !")
                        .status(OK)
                        .statusCode(OK.value())
                        .data(loginResponse)
                        .build()
        );

    }


    // resetPassword
}
