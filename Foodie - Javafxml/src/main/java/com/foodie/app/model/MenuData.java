package com.foodie.app.model;

import java.math.BigDecimal;
import java.util.List;

public record MenuData(List<MenuItem> items) {
    public record MenuItem(
            long id,
            String name,
            String description,
            Category category,
            BigDecimal price,
            String status
    ) {
        public boolean isActive() {
            return "Available".equalsIgnoreCase(status);
        }
    }

    public enum Category {
        BURGERS("Burgers", "B"),
        PIZZAS("Pizzas", "P"),
        DRINKS("Drinks", "D"),
        SIDES("Sides", "S"),
        DESSERTS("Desserts", "D");

        private final String displayName;
        private final String badge;

        Category(String displayName, String badge) {
            this.displayName = displayName;
            this.badge = badge;
        }

        public String displayName() { return displayName; }
        public String badge() { return badge; }
    }
}
