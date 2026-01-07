package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ute.R;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.User;
import com.example.ute.models.request.LoginRequest;
import com.example.ute.models.response.AuthResponse;
import com.example.ute.models.response.UserDataResponse;
import com.example.ute.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    
    private static final String TAG = "LoginActivity";
    
    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvRegister;
    private ImageButton btnGoogleLogin;
    private TextView tvBackToHome;
    private com.google.android.material.button.MaterialButton btnGoToRegister;
    
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        
        sessionManager = new SessionManager(this);
        apiService = ApiClient.getApiService();
        
        initViews();
        setupListeners();
    }
    
    private void initViews() {
        tilUsername = findViewById(R.id.tilUsername);
        tilPassword = findViewById(R.id.tilPassword);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvRegister = findViewById(R.id.tvRegister);
        btnGoogleLogin = findViewById(R.id.btnGoogleLogin);
        tvBackToHome = findViewById(R.id.tvBackToHome);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
    }
    
    private void setupListeners() {
        btnLogin.setOnClickListener(v -> attemptLogin());
        
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        
        if (btnGoToRegister != null) {
            btnGoToRegister.setOnClickListener(v -> {
                startActivity(new Intent(this, RegisterActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            });
        }
        
        tvForgotPassword.setOnClickListener(v -> {
            startActivity(new Intent(this, ForgotPasswordActivity.class));
        });
        
        btnGoogleLogin.setOnClickListener(v -> {
            // TODO: Implement Google Sign In
            Toast.makeText(this, "Google Sign In - Coming Soon", Toast.LENGTH_SHORT).show();
        });
        
        tvBackToHome.setOnClickListener(v -> {
            navigateToMain();
        });
    }
    
    private void attemptLogin() {
        // Reset errors
        tilUsername.setError(null);
        tilPassword.setError(null);
        
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        
        boolean cancel = false;
        View focusView = null;
        
        // Validate password
        if (TextUtils.isEmpty(password)) {
            tilPassword.setError(getString(R.string.field_required));
            focusView = etPassword;
            cancel = true;
        } else if (password.length() < 6) {
            tilPassword.setError(getString(R.string.password_too_short));
            focusView = etPassword;
            cancel = true;
        }
        
        // Validate username
        if (TextUtils.isEmpty(username)) {
            tilUsername.setError(getString(R.string.field_required));
            focusView = etUsername;
            cancel = true;
        }
        
        if (cancel) {
            focusView.requestFocus();
        } else {
            performLogin(username, password);
        }
    }
    
    private void performLogin(String username, String password) {
        // Show loading
        btnLogin.setEnabled(false);
        btnLogin.setText(R.string.loading);
        
        Log.d(TAG, "Attempting login for user: " + username);
        
        LoginRequest request = new LoginRequest(username, password);
        
        apiService.login(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                Log.d(TAG, "Login response received. Code: " + response.code());
                
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        AuthResponse authResponse = response.body();
                        Log.d(TAG, "Response success: " + authResponse.isSuccess());
                        
                        // Check if response is successful
                        if (authResponse.isSuccess() && authResponse.getData() != null) {
                            AuthResponse.AuthData authData = authResponse.getData();
                            Log.d(TAG, "Auth data received, token: " + (authData.getAccessToken() != null ? "present" : "null"));
                            
                            // Save access token
                            if (authData.getAccessToken() != null) {
                                sessionManager.saveAuthToken(authData.getAccessToken());
                                Log.d(TAG, "Token saved");
                            }
                            
                            // Save refresh token if available (may be in HttpOnly cookie instead)
                            if (authData.getRefreshToken() != null) {
                                sessionManager.saveRefreshToken(authData.getRefreshToken());
                            }
                            
                            // If user is included in response, save it
                            if (authData.getUser() != null) {
                                Log.d(TAG, "User data present, saving...");
                                sessionManager.saveUser(authData.getUser());
                                Log.d(TAG, "User saved, navigating to main...");
                                runOnUiThread(() -> {
                                    Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                                    navigateToMain();
                                });
                            } else {
                                Log.d(TAG, "No user in response, fetching separately...");
                                // Fetch user info separately
                                fetchUserInfo();
                            }
                        } else {
                            // Backend returned success=false
                            Log.w(TAG, "Login failed: " + authResponse.getMessage());
                            resetLoginButton();
                            String errorMsg = authResponse.getMessage() != null ? 
                                authResponse.getMessage() : "Đăng nhập thất bại";
                            Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.w(TAG, "Response not successful. Code: " + response.code());
                        resetLoginButton();
                        // Show user-friendly error message instead of HTML error body
                        String errorMsg = "Tên tài khoản hoặc mật khẩu không đúng";
                        if (response.code() == 400) {
                            errorMsg = "Dữ liệu không hợp lệ";
                        } else if (response.code() == 500) {
                            errorMsg = "Tên tài khoản hoặc mật khẩu không đúng";
                        }
                        Toast.makeText(LoginActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception during login processing", e);
                    resetLoginButton();
                    Toast.makeText(LoginActivity.this, "Đã xảy ra lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Log.e(TAG, "Login call failed", t);
                resetLoginButton();
                Toast.makeText(LoginActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
    
    private void resetLoginButton() {
        btnLogin.setEnabled(true);
        btnLogin.setText(R.string.login);
    }
    
    private void fetchUserInfo() {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.getCurrentUser(token).enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {
                resetLoginButton();
                
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        UserDataResponse userResponse = response.body();
                        if (userResponse.isSuccess() && userResponse.getData() != null) {
                            sessionManager.saveUser(userResponse.getData());
                        }
                    }
                } catch (Exception e) {
                    // Ignore error - just proceed to main
                }
                
                Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                navigateToMain();
            }
            
            @Override
            public void onFailure(Call<UserDataResponse> call, Throwable t) {
                resetLoginButton();
                // Even if fetching user info fails, login was successful
                Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                navigateToMain();
            }
        });
    }
    
    private void navigateToMain() {
        Log.d(TAG, "navigateToMain() called");
        try {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            Log.e(TAG, "Error navigating to main", e);
            Toast.makeText(this, "Lỗi chuyển trang: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
    
    @Override
    public void onBackPressed() {
        navigateToMain();
    }
}
