package com.foodie.app;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.foodie.app.viewmodels.MainViewModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {
    
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        View btnBack = findViewById(R.id.btn_back);
        btnBack.setOnClickListener(v -> finish());

        EditText etCurrent = findViewById(R.id.et_current_password);
        EditText etNew = findViewById(R.id.et_new_password);
        EditText etConfirm = findViewById(R.id.et_confirm_password);

        TextView btnUpdate = findViewById(R.id.btn_update_password);
        btnUpdate.setOnClickListener(v -> {
            String currentStr = etCurrent.getText().toString();
            String newStr = etNew.getText().toString();
            String confirmStr = etConfirm.getText().toString();
            
            if (currentStr.isEmpty() || newStr.isEmpty() || confirmStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!newStr.equals(confirmStr)) {
                Toast.makeText(this, "New passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            btnUpdate.setEnabled(false);
            btnUpdate.setText("Updating...");

            viewModel.changePassword(currentStr, newStr, new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    btnUpdate.setEnabled(true);
                    btnUpdate.setText("Update Password");
                    
                    if (response.isSuccessful()) {
                        Toast.makeText(ChangePasswordActivity.this, "Password changed successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        try {
                            String err = response.errorBody() != null ? response.errorBody().string() : "Error occurred";
                            Toast.makeText(ChangePasswordActivity.this, err, Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(ChangePasswordActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    btnUpdate.setEnabled(true);
                    btnUpdate.setText("Update Password");
                    Toast.makeText(ChangePasswordActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
