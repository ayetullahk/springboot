package com.example.springboot.repository;

import com.example.springboot.domain.User;
import com.example.springboot.exception.ResourceNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    //user ları UserName ile Db den bulanmasını sağlayan method
    Optional<User> findByUserName(String userName)throws ResourceNotFoundException;
}
