package com.foodie.app.service;

import com.foodie.app.model.MenuData;
import com.foodie.app.model.MenuData.Category;
import com.foodie.app.model.MenuData.MenuItem;

import java.math.BigDecimal;
import java.util.List;

public final class MockMenuDataProvider implements MenuDataProvider {
    @Override
    public MenuData loadMenu() {
        return new MenuData(List.of(
                item(1, "Zinger Burger", "Crispy chicken with special sauce", Category.BURGERS, "600", "Available"),
                item(2, "Chicken Fajita Pizza", "Loaded with chicken, cheese and peppers", Category.PIZZAS, "1200", "Available"),
                item(3, "French Fries", "Crispy and golden", Category.SIDES, "250", "Available"),
                item(4, "Cold Coffee", "Chilled coffee with ice cream", Category.DRINKS, "350", "Available"),
                item(5, "Chicken Wings", "Spicy and crunchy wings", Category.SIDES, "550", "Available"),
                item(6, "Margherita Pizza", "Classic cheese and tomato", Category.PIZZAS, "950", "Inactive"),
                item(7, "Chocolate Brownie", "Rich and fudgy", Category.DESSERTS, "400", "Available"),
                item(8, "Soft Drink", "Chilled and refreshing", Category.DRINKS, "200", "Available")
        ));
    }

    private MenuItem item(long id, String name, String description, Category category,
                          String price, String status) {
        return new MenuItem(id, name, description, category, new BigDecimal(price), status);
    }
}


