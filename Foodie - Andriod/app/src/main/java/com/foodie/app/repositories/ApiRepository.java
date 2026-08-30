package com.foodie.app.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.foodie.app.models.CartItem;
import com.foodie.app.models.FoodItem;
import com.foodie.app.models.Order;
import com.foodie.app.models.UserProfile;
import com.foodie.app.models.requests.LoginRequest;
import com.foodie.app.models.requests.PlaceOrderRequest;
import com.foodie.app.models.requests.RegisterRequest;
import com.foodie.app.models.responses.AuthResponse;
import com.foodie.app.models.responses.OrderResponse;
import com.foodie.app.network.ApiService;
import com.foodie.app.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {

    private ApiService apiService;
    private static ApiRepository instance;
    private static Context appContext;

    private final MutableLiveData<List<FoodItem>> foodItems = new MutableLiveData<>();
    private final MutableLiveData<List<CartItem>> cartItems = new MutableLiveData<>(new ArrayList<>());
    private final MutableLiveData<List<Order>> orders = new MutableLiveData<>();
    private final MutableLiveData<UserProfile> userProfile = new MutableLiveData<>();

    private ApiRepository(Context context) {
        apiService = RetrofitClient.getClient(context).create(ApiService.class);
    }

    public static synchronized ApiRepository getInstance(Context context) {
        if (instance == null) {
            appContext = context.getApplicationContext();
            instance = new ApiRepository(appContext);
        }
        return instance;
    }

    // LiveData getters
    public LiveData<List<FoodItem>> getFoodItems() { return foodItems; }
    public LiveData<List<CartItem>> getCartItems() { return cartItems; }
    public LiveData<List<Order>> getOrders() { return orders; }
    public LiveData<UserProfile> getUserProfile() { return userProfile; }

    public void login(LoginRequest request, Callback<AuthResponse> callback) {
        apiService.login(request).enqueue(callback);
    }

    public void register(RegisterRequest request, Callback<AuthResponse> callback) {
        apiService.register(request).enqueue(callback);
    }

    public void fetchFoods() {
        apiService.getFoods().enqueue(new Callback<List<FoodItem>>() {
            @Override
            public void onResponse(Call<List<FoodItem>> call, Response<List<FoodItem>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    foodItems.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<FoodItem>> call, Throwable t) {
                Log.e("ApiRepository", "fetchFoods error", t);
            }
        });
    }

    public void fetchOrders() {
        apiService.getOrderHistory().enqueue(new Callback<List<Order>>() {
            @Override
            public void onResponse(Call<List<Order>> call, Response<List<Order>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    orders.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<Order>> call, Throwable t) {
                Log.e("ApiRepository", "fetchOrders error", t);
            }
        });
    }

    public void fetchProfile() {
        apiService.getProfile().enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                if (response.isSuccessful() && response.body() != null) {
                    userProfile.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.e("ApiRepository", "fetchProfile error", t);
            }
        });
    }

    // Local cart logic
    public void addToCart(FoodItem food) {
        List<CartItem> current = cartItems.getValue();
        if (current == null) current = new ArrayList<>();
        boolean exists = false;
        for (CartItem item : current) {
            if (item.getId().equals(food.getId())) {
                item.setQuantity(item.getQuantity() + 1);
                exists = true;
                break;
            }
        }
        if (!exists) {
            current.add(new CartItem(food.getId(), food.getTitle(), food.getDesc(), food.getPrice(), food.getImageResId(), food.getImageUrl()));
        }
        cartItems.postValue(current);
    }

    public void updateCartQuantity(String itemId, int change) {
        List<CartItem> current = cartItems.getValue();
        if (current == null) return;
        for (int i = 0; i < current.size(); i++) {
            CartItem item = current.get(i);
            if (item.getId().equals(itemId)) {
                int newQty = item.getQuantity() + change;
                if (newQty <= 0) {
                    current.remove(i);
                } else {
                    item.setQuantity(newQty);
                }
                cartItems.postValue(current);
                return;
            }
        }
    }

    public void removeCartItem(String itemId) {
        List<CartItem> current = cartItems.getValue();
        if (current == null) return;
        for (int i = 0; i < current.size(); i++) {
            if (current.get(i).getId().equals(itemId)) {
                current.remove(i);
                cartItems.postValue(current);
                return;
            }
        }
    }

    public void clearCart() {
        cartItems.postValue(new ArrayList<>());
    }

    public void placeOrder(PlaceOrderRequest request, Callback<OrderResponse> callback) {
        apiService.placeOrder(request).enqueue(callback);
    }

    public void changePassword(com.foodie.app.models.requests.ChangePasswordRequest request, Callback<okhttp3.ResponseBody> callback) {
        apiService.changePassword(request).enqueue(callback);
    }
}
