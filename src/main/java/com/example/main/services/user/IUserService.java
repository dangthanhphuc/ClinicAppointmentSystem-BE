package com.example.main.services.user;

import com.example.main.dtos.LoginDTO;
import com.example.main.entities.User;
import com.example.main.exceptions.DataNotFoundException;
import com.example.main.exceptions.ExpiredException;
import com.example.main.exceptions.IdNotFoundException;
import com.example.main.exceptions.NotMatchException;

import java.util.List;

public interface IUserService {

    User getUser(Long userId) throws IdNotFoundException;
    User getUserByToken(String token) throws ExpiredException, DataNotFoundException;
    User getUserByRefreshToken(String refreshToken) throws DataNotFoundException, ExpiredException;
    List<User> getUsers() ;
    void deleteUser(Long userId) throws IdNotFoundException;
    String login(LoginDTO loginDTO) throws NotMatchException;
}
