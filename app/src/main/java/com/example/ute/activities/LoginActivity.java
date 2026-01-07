package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.example.ute.models.request.LoginRequest;
import com.example.ute.models.response.AuthResponse;
import com.example.ute.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    
    private TextInputLayout tilUsername, tilPassword;
    private TextInputEditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvForgotPassword, tvRegister;
    private ImageButton btnGoogleLogin;
    private TextView tvBackToHome;
    
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
    }
    
    private void setupListeners() {
        btnLogin.setOnClickListener(v -> attemptLogin());
        
        tvRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        });
        
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
        
        LoginRequest request = new LoginRequest(username, password);
        
        apiService.login(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnLogin.setEnabled(true);
                btnLogin.setText(R.string.login);
                
                if (response.isSuccessful() && response.body() != null) {
                    AuthResponse authResponse = response.body();
                    
                    // Save tokens and user info
                    sessionManager.saveAuthToken(authResponse.getAccessToken());
                    sessionManager.saveRefreshToken(authResponse.getRefreshToken());
                    if (authResponse.getUser() != null) {
                        sessionManager.saveUser(authResponse.getUser());
                    }
                    
                    Toast.makeText(LoginActivity.this, R.string.login_success, Toast.LENGTH_SHORT).show();
                    navigateToMain();
                } else {
                    Toast.makeText(LoginActivity.this, "Đăng nhập thất bại. Vui lòng kiểm tra lại thông tin.", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnLogin.setEnabled(true);
                btnLogin.setText(R.string.login);
                Toast.makeText(LoginActivity.this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void navigateToMain() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
    
    @Override
    public void onBackPressed() {
        navigateToMain();
    }
}
