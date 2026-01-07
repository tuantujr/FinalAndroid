package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ute.R;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.request.ChangePasswordRequest;
import com.example.ute.models.response.ApiResponse;
import com.example.ute.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputEditText etCurrentPassword, etNewPassword, etConfirmPassword;
    private MaterialButton btnChangePassword;
    private ProgressBar progressBar;
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getApiService();
        
        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, R.string.please_login, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        setupToolbar();
        initViews();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.change_password);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initViews() {
        etCurrentPassword = findViewById(R.id.etCurrentPassword);
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnChangePassword = findViewById(R.id.btnChangePassword);
        progressBar = findViewById(R.id.progressBar);

        btnChangePassword.setOnClickListener(v -> changePassword());
    }

    private void changePassword() {
        String currentPassword = etCurrentPassword.getText().toString();
        String newPassword = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (currentPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, R.string.field_required, Toast.LENGTH_SHORT).show();
            return;
        }

        if (newPassword.length() < 6) {
            etNewPassword.setError(getString(R.string.password_too_short));
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            etConfirmPassword.setError(getString(R.string.password_not_match));
            return;
        }

        // Call API to change password
        changePasswordViaAPI(currentPassword, newPassword);
    }

    private void changePasswordViaAPI(String currentPassword, String newPassword) {
        btnChangePassword.setEnabled(false);
        progressBar.setVisibility(android.view.View.VISIBLE);
        
        String token = "Bearer " + sessionManager.getAuthToken();
        ChangePasswordRequest request = new ChangePasswordRequest(currentPassword, newPassword);
        
        apiService.changePassword(token, request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                btnChangePassword.setEnabled(true);
                progressBar.setVisibility(android.view.View.GONE);
                
                if (response.isSuccessful() && response.body() != null) {
                    ApiResponse apiResponse = response.body();
                    if (apiResponse.isSuccess()) {
                        Toast.makeText(ChangePasswordActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String msg = apiResponse.getMessage() != null ? apiResponse.getMessage() : "Đổi mật khẩu thất bại";
                        Toast.makeText(ChangePasswordActivity.this, msg, Toast.LENGTH_LONG).show();
                    }
                } else {
                    String errorMsg = "Tên tài khoản hoặc mật khẩu không đúng";
                    if (response.code() == 400) {
                        errorMsg = "Dữ liệu không hợp lệ";
                    } else if (response.code() == 401 || response.code() == 500) {
                        errorMsg = "Tên tài khoản hoặc mật khẩu không đúng";
                    }
                    Toast.makeText(ChangePasswordActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                btnChangePassword.setEnabled(true);
                progressBar.setVisibility(android.view.View.GONE);
                String errorMsg = t.getMessage() != null ? t.getMessage() : "Lỗi kết nối";
                // Handle "end of stream" error from parsing HTML as JSON
                if (errorMsg.contains("end of stream") || errorMsg.contains("EOFException")) {
                    Toast.makeText(ChangePasswordActivity.this, "Tên tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ChangePasswordActivity.this, "Lỗi kết nối: " + errorMsg, Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
