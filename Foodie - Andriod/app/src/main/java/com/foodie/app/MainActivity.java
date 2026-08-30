package com.foodie.app;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.foodie.app.adapters.FoodAdapter;
import com.foodie.app.viewmodels.MainViewModel;
import com.foodie.app.models.CartItem;

public class MainActivity extends AppCompatActivity {

    private TextView cartBadge;
    private MainViewModel viewModel;
    private FoodAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cartBadge = findViewById(R.id.cart_badge);
        
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        RecyclerView recyclerView = findViewById(R.id.recyclerView_food);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        
        adapter = new FoodAdapter(item -> {
            viewModel.addToCart(item);
        });
        recyclerView.setAdapter(adapter);

        viewModel.fetchFoods(); // Trigger API call

        viewModel.getFoodItems().observe(this, items -> {
            if (items != null) {
                adapter.setItems(items);
            }
        });
        
        viewModel.getCartItems().observe(this, items -> {
            if (items != null) {
                int count = 0;
                for (CartItem item : items) {
                    count += item.getQuantity();
                }
                cartBadge.setText(String.valueOf(count));
            }
        });

        View btnTopCart = findViewById(R.id.btn_top_cart);
        btnTopCart.setOnClickListener(v -> {
            startActivity(new android.content.Intent(this, CartActivity.class));
            overridePendingTransition(0, 0);
        });

        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.nav_orders) {
                startActivity(new android.content.Intent(this, OrdersActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            } else if (item.getItemId() == R.id.nav_profile) {
                startActivity(new android.content.Intent(this, ProfileActivity.class));
                overridePendingTransition(0, 0);
                finish();
                return true;
            }
            return item.getItemId() == R.id.nav_home;
        });
    }
}
