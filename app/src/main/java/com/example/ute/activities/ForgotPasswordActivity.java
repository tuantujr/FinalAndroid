package com.example.ute.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ute.R;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.response.ApiResponse;
import com.example.ute.models.request.ForgotPasswordRequest;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity {
    
    private TextInputEditText etEmail, etOTP, etPassword, etConfirmPassword;
    private Button btnSendOTP, btnVerifyOTP, btnResetPassword;
    private ProgressBar progressBar;
    private LinearLayout layoutStep1, layoutStep2, layoutStep3;
    
    private ApiService apiService;
    private String currentEmail;
    private String currentOTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        
        apiService = ApiClient.getApiService();
        
        initViews();
        setupListeners();
        showStep(1);
    }
    
    private void initViews() {
        ImageButton btnBack = findViewById(R.id.btnBack);
        etEmail = findViewById(R.id.etEmail);
        etOTP = findViewById(R.id.etOTP);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnSendOTP = findViewById(R.id.btnSendOTP);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        btnResetPassword = findViewById(R.id.btnResetPassword);
        progressBar = findViewById(R.id.progressBar);
        layoutStep1 = findViewById(R.id.layoutStep1);
        layoutStep2 = findViewById(R.id.layoutStep2);
        layoutStep3 = findViewById(R.id.layoutStep3);
        
        btnBack.setOnClickListener(v -> onBackPressed());
    }
    
    private void setupListeners() {
        btnSendOTP.setOnClickListener(v -> requestOTP());
        btnVerifyOTP.setOnClickListener(v -> verifyOTP());
        btnResetPassword.setOnClickListener(v -> resetPassword());
        
        findViewById(R.id.layoutStep1).findViewById(R.id.tvQuayLaiDangNhap).setOnClickListener(v -> onBackPressed());
    }
    
    // Step 1: Request OTP
    private void requestOTP() {
        String email = etEmail.getText().toString().trim();
        
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Vui lòng nhập email", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        
        currentEmail = email;
        showLoading(true);
        
        ForgotPasswordRequest request = new ForgotPasswordRequest(email);
        
        apiService.requestForgotPasswordOTP(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                showLoading(false);
                
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse apiResponse = response.body();
                        if (apiResponse.isSuccess()) {
                            Toast.makeText(ForgotPasswordActivity.this, 
                                "OTP đã được gửi đến email của bạn", Toast.LENGTH_SHORT).show();
                            showStep(2);
                        } else {
                            String msg = apiResponse.getMessage() != null ? apiResponse.getMessage() : "Gửi OTP thất bại";
                            Toast.makeText(ForgotPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "Gửi OTP thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ForgotPasswordActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                showLoading(false);
                Toast.makeText(ForgotPasswordActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    // Step 2: Verify OTP
    private void verifyOTP() {
        String otp = etOTP.getText().toString().trim();
        
        if (TextUtils.isEmpty(otp)) {
            Toast.makeText(this, "Vui lòng nhập mã OTP", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (otp.length() < 4) {
            Toast.makeText(this, "Mã OTP không hợp lệ", Toast.LENGTH_SHORT).show();
            return;
        }
        
        currentOTP = otp;
        showLoading(true);
        
        ForgotPasswordRequest request = new ForgotPasswordRequest();
        request.setEmail(currentEmail);
        request.setOtp(otp);
        
        apiService.verifyForgotPasswordOTP(request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                showLoading(false);
                
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse apiResponse = response.body();
                        if (apiResponse.isSuccess()) {
                            Toast.makeText(ForgotPasswordActivity.this, 
                                "OTP xác nhận thành công", Toast.LENGTH_SHORT).show();
                            showStep(3);
                        } else {
                            String msg = apiResponse.getMessage() != null ? apiResponse.getMessage() : "OTP không hợp lệ";
                            Toast.makeText(ForgotPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(ForgotPasswordActivity.this, "OTP không hợp lệ", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(ForgotPasswordActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                showLoading(false);
                Toast.makeText(ForgotPasswordActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    // Step 3: Reset Password
    private void resetPassword() {
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (password.length() < 6) {
            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu không trùng khớp", Toast.LENGTH_SHORT).show();
            return;
        }
        
        showLoading(true);
        
        ForgotPasswordRequest request = new ForgotPasswordRequest(currentEmail, currentOTP, password);
        
        apiService.resetForgotPassword(request)
            .enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    showLoading(false);
                    
                    try {
                        if (response.isSuccessful() && response.body() != null) {
                            ApiResponse apiResponse = response.body();
                            if (apiResponse.isSuccess()) {
                                Toast.makeText(ForgotPasswordActivity.this, 
                                    "Đặt lại mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                onBackPressed();
                            } else {
                                String msg = apiResponse.getMessage() != null ? apiResponse.getMessage() : "Đặt lại mật khẩu thất bại";
                                Toast.makeText(ForgotPasswordActivity.this, msg, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ForgotPasswordActivity.this, "Đặt lại mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(ForgotPasswordActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                
                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    showLoading(false);
                    Toast.makeText(ForgotPasswordActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
    
    private void showStep(int step) {
        layoutStep1.setVisibility(step == 1 ? View.VISIBLE : View.GONE);
        layoutStep2.setVisibility(step == 2 ? View.VISIBLE : View.GONE);
        layoutStep3.setVisibility(step == 3 ? View.VISIBLE : View.GONE);
    }
    
    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnSendOTP.setEnabled(!show);
        btnVerifyOTP.setEnabled(!show);
        btnResetPassword.setEnabled(!show);
    }
}
