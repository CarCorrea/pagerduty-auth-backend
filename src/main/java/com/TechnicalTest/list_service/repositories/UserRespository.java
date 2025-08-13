package com.TechnicalTest.list_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.User;

import java.util.Optional;

public interface UserRespository  extends JpaRepository<org.springframework.security.core.userdetails.User, Long> {

    Optional<User> findByUsername(String username);
}
