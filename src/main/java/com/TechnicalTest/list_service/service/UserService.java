package com.TechnicalTest.list_service.service;

import com.TechnicalTest.list_service.entities.EscalationPolicy;
import com.TechnicalTest.list_service.entities.User;
import com.TechnicalTest.list_service.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    public User createUser(User user){

        if(userRepository.existsByUsername(user.getUsername())){
            throw new IllegalArgumentException("Username already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(Long id, String newPassword, String newRole){
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (newPassword != null && !newPassword.isBlank()) {
            user.setPassword(passwordEncoder.encode(newPassword));
        }

        if (newRole != null && !newRole.isBlank()) {
            user.setRole(newRole);
        }

        return userRepository.save(user);
    }

    public User updateUserEscalationPolicies(Long userId, List<EscalationPolicy> newPolicies){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("One or more users not found"));

        user.setEscalationPolicies(newPolicies);

        return userRepository.save(user);
    }

    public void deleteUser(Long id){
        if (!userRepository.existsById(id)){
           throw new IllegalArgumentException("User not found");
        }
        userRepository.deleteById(id);
    }
}
