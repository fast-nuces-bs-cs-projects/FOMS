package com.foodie.app.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.foodie.app.models.CartItem;
import com.foodie.app.models.FoodItem;
import com.foodie.app.models.Order;
import com.foodie.app.repositories.ApiRepository;

import java.util.List;

public class MainViewModel extends AndroidViewModel {
    private final ApiRepository repository;

    public MainViewModel(@NonNull Application application) {
        super(application);
        repository = ApiRepository.getInstance(application);
    }

    public LiveData<List<FoodItem>> getFoodItems() {
        return repository.getFoodItems();
    }

    public LiveData<List<CartItem>> getCartItems() {
        return repository.getCartItems();
    }

    public LiveData<List<Order>> getOrders() {
        return repository.getOrders();
    }

    public LiveData<com.foodie.app.models.UserProfile> getUserProfile() {
        return repository.getUserProfile();
    }

    public void fetchFoods() {
        repository.fetchFoods();
    }

    public void fetchOrders() {
        repository.fetchOrders();
    }

    public void fetchProfile() {
        repository.fetchProfile();
    }

    public void addToCart(FoodItem food) {
        repository.addToCart(food);
    }

    public void increaseQuantity(String itemId) {
        repository.updateCartQuantity(itemId, 1);
    }

    public void decreaseQuantity(String itemId) {
        repository.updateCartQuantity(itemId, -1);
    }
    
    public void removeCartItem(String itemId) {
        repository.removeCartItem(itemId);
    }
    
    public void placeOrder(android.content.Context context) {
        List<CartItem> currentCart = repository.getCartItems().getValue();
        if (currentCart == null || currentCart.isEmpty()) return;

        java.util.List<com.foodie.app.models.requests.CartItemRequest> items = new java.util.ArrayList<>();
        for (CartItem item : currentCart) {
            items.add(new com.foodie.app.models.requests.CartItemRequest(Integer.parseInt(item.getId()), item.getQuantity()));
        }

        com.foodie.app.models.requests.PlaceOrderRequest request = new com.foodie.app.models.requests.PlaceOrderRequest(
            "Default Address",
            "Cash on Delivery",
            items
        );

        repository.placeOrder(request, new retrofit2.Callback<com.foodie.app.models.responses.OrderResponse>() {
            @Override
            public void onResponse(retrofit2.Call<com.foodie.app.models.responses.OrderResponse> call, retrofit2.Response<com.foodie.app.models.responses.OrderResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    android.widget.Toast.makeText(context, response.body().getMessage(), android.widget.Toast.LENGTH_SHORT).show();
                    repository.clearCart();
                    // Optionally fetch orders again
                    repository.fetchOrders();
                } else {
                    android.widget.Toast.makeText(context, "Order failed", android.widget.Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<com.foodie.app.models.responses.OrderResponse> call, Throwable t) {
                android.widget.Toast.makeText(context, "Error: " + t.getMessage(), android.widget.Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void changePassword(String currentPassword, String newPassword, retrofit2.Callback<okhttp3.ResponseBody> callback) {
        com.foodie.app.models.requests.ChangePasswordRequest req = new com.foodie.app.models.requests.ChangePasswordRequest(currentPassword, newPassword);
        repository.changePassword(req, callback);
    }
}
