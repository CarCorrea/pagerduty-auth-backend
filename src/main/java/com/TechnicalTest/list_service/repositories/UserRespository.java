package com.TechnicalTest.list_service.repositories;

import com.TechnicalTest.list_service.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRespository  extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
