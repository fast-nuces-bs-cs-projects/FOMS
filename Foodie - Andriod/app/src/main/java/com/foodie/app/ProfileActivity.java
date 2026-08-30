package com.foodie.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.foodie.app.viewmodels.MainViewModel;

public class ProfileActivity extends AppCompatActivity {
    
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        
        viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        viewModel.fetchProfile();
        
        TextView tvName = findViewById(R.id.tv_profile_name);
        TextView tvEmail = findViewById(R.id.tv_profile_email);
        TextView tvPhone = findViewById(R.id.tv_profile_phone);
        ImageView ivProfile = findViewById(R.id.iv_profile);
        
        viewModel.getUserProfile().observe(this, profile -> {
            if (profile != null) {
                if (profile.getName() != null) tvName.setText(profile.getName());
                if (profile.getEmail() != null) tvEmail.setText(profile.getEmail());
                if (profile.getPhone() != null) tvPhone.setText(profile.getPhone());
                
                if (profile.getProfileImageUrl() != null && !profile.getProfileImageUrl().isEmpty() && ivProfile != null) {
                    String baseUrl = "http://10.0.2.2:8081";
                    com.bumptech.glide.Glide.with(this)
                        .load(baseUrl + profile.getProfileImageUrl())
                        .placeholder(R.drawable.logo)
                        .into(ivProfile);
                }
            }
        });

        View changePasswordView = findViewById(R.id.menu_change_password);
        setupMenu(changePasswordView, "Change Password", R.drawable.ic_lock);
        changePasswordView.setOnClickListener(v -> {
            startActivity(new Intent(this, ChangePasswordActivity.class));
        });

        TextView tvDeveloperGuide = findViewById(R.id.tv_developer_guide);
        tvDeveloperGuide.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse("https://rohanfarooqui.github.io"));
            startActivity(browserIntent);
        });

        View logoutView = findViewById(R.id.menu_logout);
        setupMenu(logoutView, "Logout", R.drawable.ic_logout);
        logoutView.setOnClickListener(v -> {
            Toast.makeText(this, "Logging out...", Toast.LENGTH_SHORT).show();
            // Clear actual token
            new com.foodie.app.utils.SessionManager(this).clearSession();
            
            Intent intent = new Intent(this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        });

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation_profile);
        bottomNav.setSelectedItemId(R.id.nav_profile);
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
            }
            return item.getItemId() == R.id.nav_profile;
        });
    }

    private void setupMenu(View item, String title, int iconResId) {
        TextView titleView = item.findViewById(R.id.menu_title);
        ImageView iconView = item.findViewById(R.id.menu_icon);
        
        titleView.setText(title);
        iconView.setImageResource(iconResId);
    }
}
