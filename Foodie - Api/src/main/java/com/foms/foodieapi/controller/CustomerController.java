package com.foms.foodieapi.controller;

import com.foms.foodieapi.model.User;
import com.foms.foodieapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/customers")
@CrossOrigin(origins = "*")
public class CustomerController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public List<User> getAllCustomers() {
        // Return only users with role CUSTOMER
        return userRepository.findAll().stream()
                .filter(user -> "CUSTOMER".equals(user.getRole()))
                .collect(Collectors.toList());
    }

    @PostMapping
    public User addCustomer(@RequestBody User customer) {
        customer.setRole("CUSTOMER");
        // Ensure default status if not provided
        if (customer.getStatus() == null) {
            customer.setStatus("Active");
        }
        return userRepository.save(customer);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<User> updateCustomerStatus(@PathVariable Long id, @RequestBody Map<String, String> statusUpdate) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setStatus(statusUpdate.get("status"));
                    return ResponseEntity.ok(userRepository.save(user));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
