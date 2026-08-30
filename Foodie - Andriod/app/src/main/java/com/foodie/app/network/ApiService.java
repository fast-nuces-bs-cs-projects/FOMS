package com.foodie.app.network;

import com.foodie.app.models.FoodItem;
import com.foodie.app.models.Order;
import com.foodie.app.models.UserProfile;
import com.foodie.app.models.requests.LoginRequest;
import com.foodie.app.models.requests.PlaceOrderRequest;
import com.foodie.app.models.requests.RegisterRequest;
import com.foodie.app.models.responses.AuthResponse;
import com.foodie.app.models.responses.OrderResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {

    @POST("/api/auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);

    @POST("/api/auth/register")
    Call<AuthResponse> register(@Body RegisterRequest request);

    @GET("/api/user/profile")
    Call<UserProfile> getProfile();

    @GET("/api/foods")
    Call<List<FoodItem>> getFoods();

    @POST("/api/orders/place")
    Call<OrderResponse> placeOrder(@Body PlaceOrderRequest request);

    @GET("/api/orders")
    Call<List<Order>> getOrderHistory();

    @POST("/api/auth/change-password")
    Call<okhttp3.ResponseBody> changePassword(@Body com.foodie.app.models.requests.ChangePasswordRequest request);
}
