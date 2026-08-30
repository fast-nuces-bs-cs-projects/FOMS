package com.foodie.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodie.app.adapters.CartAdapter;
import com.foodie.app.models.CartItem;
import com.foodie.app.viewmodels.MainViewModel;

public class CartActivity extends AppCompatActivity {
    
    private MainViewModel viewModel;
    private CartAdapter adapter;
    private TextView tvSubtotal, tvDelivery, tvTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        tvSubtotal = findViewById(R.id.tv_subtotal);
        tvDelivery = findViewById(R.id.tv_delivery);
        tvTotal = findViewById(R.id.tv_total);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_cart);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        adapter = new CartAdapter(new CartAdapter.OnCartItemClickListener() {
            @Override
            public void onIncrease(CartItem item) {
                viewModel.increaseQuantity(item.getId());
            }

            @Override
            public void onDecrease(CartItem item) {
                viewModel.decreaseQuantity(item.getId());
            }

            @Override
            public void onDelete(CartItem item) {
                viewModel.removeCartItem(item.getId());
            }
        });
        recyclerView.setAdapter(adapter);

        TextView tvEmptyCart = findViewById(R.id.tv_empty_cart);
        View bottomArea = findViewById(R.id.bottom_area);

        viewModel.getCartItems().observe(this, items -> {
            if (items == null || items.isEmpty()) {
                tvEmptyCart.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                bottomArea.setVisibility(View.GONE);
            } else {
                tvEmptyCart.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                bottomArea.setVisibility(View.VISIBLE);
                
                adapter.setItems(items);
                
                double subtotal = 0;
                for (CartItem item : items) {
                    try {
                        // Extract digits and the decimal point
                        String priceStr = item.getPrice().replaceAll("[^\\d.]", "");
                        double price = Double.parseDouble(priceStr);
                        subtotal += price * item.getQuantity();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                
                double delivery = subtotal > 0 ? 100.0 : 0.0;
                double total = subtotal + delivery;
                
                // Format to optionally show decimals or cast to int if it's a round number
                tvSubtotal.setText(String.format("Rs. %.0f", subtotal));
                tvDelivery.setText(String.format("Rs. %.0f", delivery));
                tvTotal.setText(String.format("Rs. %.0f", total));
            }
        });

        View btnPlaceOrder = findViewById(R.id.btn_place_order);
        if (btnPlaceOrder != null) {
            btnPlaceOrder.setOnClickListener(v -> {
                viewModel.placeOrder(this);
            });
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_cart);
        // Deselect all items since this is now a secondary screen
        bottomNav.getMenu().setGroupCheckable(0, false, true);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_home) {
                startActivity(new Intent(this, MainActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_orders) {
                startActivity(new Intent(this, OrdersActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_profile) {
                startActivity(new Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return false;
        });
    }
}
