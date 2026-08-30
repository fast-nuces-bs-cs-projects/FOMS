package com.foodie.app;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SplashActivity extends AppCompatActivity {

    private TextView tvStatus;
    private static final String API_URL = "http://10.0.2.2:8081/api/foods"; 

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        View dot1 = findViewById(R.id.dot1);
        View dot2 = findViewById(R.id.dot2);
        View dot3 = findViewById(R.id.dot3);
        tvStatus = findViewById(R.id.tv_status);

        animateDot(dot1, 0);
        animateDot(dot2, 200);
        animateDot(dot3, 400);

        performStartupChecks();
    }

    private void animateDot(View dot, long delay) {
        ObjectAnimator alpha = ObjectAnimator.ofFloat(dot, "alpha", 0.3f, 1f, 0.3f);
        alpha.setDuration(1000);
        alpha.setRepeatCount(ObjectAnimator.INFINITE);
        alpha.setStartDelay(delay);
        alpha.start();
    }

    private void performStartupChecks() {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                // 1. Check Internet Connectivity
                handler.post(() -> tvStatus.setText("Checking internet connection..."));
                Thread.sleep(800); // Simulate realistic check delay
                
                boolean isConnected = isNetworkAvailable();
                if (!isConnected) {
                    handler.post(() -> {
                        tvStatus.setText("No internet connection.");
                        Toast.makeText(SplashActivity.this, "Offline: Proceeding to prototype mock mode.", Toast.LENGTH_LONG).show();
                        proceedToLogin();
                    });
                    return;
                }

                // 2. Ping API Server
                handler.post(() -> tvStatus.setText("Connecting to API server (localhost)..."));
                
                boolean isServerUp = checkServerStatus();
                
                handler.post(() -> {
                    if (isServerUp) {
                        tvStatus.setText("API Server connected successfully!");
                    } else {
                        tvStatus.setText("API offline. Falling back to Mock API.");
                        Toast.makeText(SplashActivity.this, "Backend down. Using MockApiRepository.", Toast.LENGTH_LONG).show();
                    }
                    
                    // Allow user to read the status
                    new Handler(Looper.getMainLooper()).postDelayed(this::proceedToLogin, 1200);
                });

            } catch (Exception e) {
                handler.post(this::proceedToLogin);
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) return false;
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    private boolean checkServerStatus() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(1500); // 1.5 seconds timeout
            connection.setReadTimeout(1500);
            connection.connect();
            // Just getting a response code means it's running
            return connection.getResponseCode() > 0;
        } catch (Exception e) {
            return false;
        }
    }

    private void proceedToLogin() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
