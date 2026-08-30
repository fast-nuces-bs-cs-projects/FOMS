package com.foms.foodieapi.config;

import com.foms.foodieapi.model.FoodItem;
import com.foms.foodieapi.model.User;
import com.foms.foodieapi.repository.FoodItemRepository;
import com.foms.foodieapi.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataSeeder {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, FoodItemRepository foodItemRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Seed Users
            if (userRepository.count() == 0) {
                User admin = new User();
                admin.setName("Admin User");
                admin.setEmail("admin@foodie.com");
                admin.setPassword(passwordEncoder.encode("admin123")); // Hashed!
                admin.setRole("ADMIN");
                admin.setStatus("Active");
                userRepository.save(admin);

                User customer = new User();
                customer.setName("John Doe");
                customer.setEmail("john@example.com");
                customer.setPassword(passwordEncoder.encode("password123"));
                customer.setRole("CUSTOMER");
                customer.setStatus("Active");
                customer.setPhone("123-456-7890");
                userRepository.save(customer);
                
                System.out.println("Seeded Users.");
            }

            // Seed Food Items
            if (foodItemRepository.count() == 0) {
                FoodItem burger = new FoodItem();
                burger.setName("Classic Burger");
                burger.setDescription("Juicy beef patty with lettuce, tomato, and cheese.");
                burger.setCategory("Main Course");
                burger.setPrice(850.00);
                burger.setStatus("Available");
                burger.setImageUrl("/images/burger.jpg");
                foodItemRepository.save(burger);

                FoodItem pizza = new FoodItem();
                pizza.setName("Margherita Pizza");
                pizza.setDescription("Classic cheese and tomato pizza.");
                pizza.setCategory("Main Course");
                pizza.setPrice(1200.00);
                pizza.setStatus("Available");
                pizza.setImageUrl("/images/pizza.jpg");
                foodItemRepository.save(pizza);
                
                FoodItem coke = new FoodItem();
                coke.setName("Coca Cola");
                coke.setDescription("Chilled 500ml beverage.");
                coke.setCategory("Beverage");
                coke.setPrice(200.00);
                coke.setStatus("Available");
                coke.setImageUrl("/images/coke.jpg");
                foodItemRepository.save(coke);

                System.out.println("Seeded Food Items.");
            }
        };
    }
}
