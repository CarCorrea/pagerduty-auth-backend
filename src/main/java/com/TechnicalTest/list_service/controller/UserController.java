package com.TechnicalTest.list_service.controller;

import com.TechnicalTest.list_service.entities.EscalationPolicy;
import com.TechnicalTest.list_service.entities.User;
import com.TechnicalTest.list_service.repositories.EscalationPolicyRepository;
import com.TechnicalTest.list_service.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final EscalationPolicyRepository escalationPolicyRepository;

    public UserController(UserService userService, EscalationPolicyRepository escalationPolicyRepository) {
        this.userService = userService;
        this.escalationPolicyRepository = escalationPolicyRepository;
    }

    @GetMapping("/{id}")

    public ResponseEntity<User> getUserById(@PathVariable Long id){
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return ResponseEntity.ok(userService.createUser(user));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or #id == principal.id")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails){
        User updatedUser = userService.updateUser(id, userDetails.getPassword(), userDetails.getRole());
        return ResponseEntity.ok(updatedUser);
    }

    @PutMapping("/{id}/policies")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUserPolicies(@PathVariable Long id, @RequestBody List<Long> policyIds){
        List<EscalationPolicy> policies = escalationPolicyRepository.findAllById(policyIds);
        User updatedUser = userService.updateUserEscalationPolicies(id, policies);

        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
