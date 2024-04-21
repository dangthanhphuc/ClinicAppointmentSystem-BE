package com.example.main.repositories;

import com.example.main.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT u.* FROM users u where u.username LIKE :username ", nativeQuery = true)
    Optional<User> findByUsername(@Param("username") String username);
}
