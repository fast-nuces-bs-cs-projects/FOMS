package com.foms.foodieapi.controller;

import com.foms.foodieapi.model.FoodItem;
import com.foms.foodieapi.repository.FoodItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"/api/menu", "/api/foods"})
@CrossOrigin(origins = "*") // Allows the frontend to make requests without CORS errors
public class MenuController {

    @Autowired
    private FoodItemRepository menuRepository;

    @GetMapping
    public List<FoodItem> getAllMenuItems() {
        return menuRepository.findAll();
    }

    @PostMapping
    public FoodItem addMenuItem(@RequestBody FoodItem item) {
        return menuRepository.save(item);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FoodItem> updateMenuItem(@PathVariable Long id, @RequestBody FoodItem updatedItem) {
        return menuRepository.findById(id)
                .map(item -> {
                    item.setName(updatedItem.getName());
                    item.setDescription(updatedItem.getDescription());
                    item.setCategory(updatedItem.getCategory());
                    item.setPrice(updatedItem.getPrice());
                    item.setStatus(updatedItem.getStatus());
                    item.setImageUrl(updatedItem.getImageUrl());
                    return ResponseEntity.ok(menuRepository.save(item));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable Long id) {
        if (menuRepository.existsById(id)) {
            menuRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
