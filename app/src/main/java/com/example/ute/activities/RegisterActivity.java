package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ute.R;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.request.RegisterRequest;
import com.example.ute.models.response.AuthResponse;
import com.example.ute.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    
    private TextInputLayout tilUsername, tilFullName, tilEmail, tilPassword, tilConfirmPassword;
    private TextInputEditText etUsername, etFullName, etEmail, etPassword, etConfirmPassword;
    private Button btnRegister;
    private TextView tvLogin;
    private ImageButton btnGoogleRegister;
    private TextView tvBackToHome;
    
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        sessionManager = new SessionManager(this);
        apiService = ApiClient.getApiService();
        
        initViews();
        setupListeners();
    }
    
    private void initViews() {
        tilUsername = findViewById(R.id.tilUsername);
        tilFullName = findViewById(R.id.tilFullName);
        tilEmail = findViewById(R.id.tilEmail);
        tilPassword = findViewById(R.id.tilPassword);
        tilConfirmPassword = findViewById(R.id.tilConfirmPassword);
        
        etUsername = findViewById(R.id.etUsername);
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);
        btnGoogleRegister = findViewById(R.id.btnGoogleRegister);
        tvBackToHome = findViewById(R.id.tvBackToHome);
    }
    
    private void setupListeners() {
        btnRegister.setOnClickListener(v -> attemptRegister());
        
        tvLogin.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            finish();
        });
        
        btnGoogleRegister.setOnClickListener(v -> {
            // TODO: Implement Google Sign Up
            Toast.makeText(this, "Google Sign Up - Coming Soon", Toast.LENGTH_SHORT).show();
        });
        
        tvBackToHome.setOnClickListener(v -> {
            navigateToMain();
        });
    }
    
    private void attemptRegister() {
        // Reset errors
        tilUsername.setError(null);
        tilFullName.setError(null);
        tilEmail.setError(null);
        tilPassword.setError(null);
        tilConfirmPassword.setError(null);
        
        String username = etUsername.getText().toString().trim();
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String confirmPassword = etConfirmPassword.getText().toString().trim();
        
        boolean cancel = false;
        View focusView = null;
        
        // Validate confirm password
        if (TextUtils.isEmpty(confirmPassword)) {
            tilConfirmPassword.setError(getString(R.string.field_required));
            focusView = etConfirmPassword;
            cancel = true;
        } else if (!confirmPassword.equals(password)) {
            tilConfirmPassword.setError(getString(R.string.password_not_match));
            focusView = etConfirmPassword;
            cancel = true;
        }
        
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
        
        // Validate email
        if (TextUtils.isEmpty(email)) {
            tilEmail.setError(getString(R.string.field_required));
            focusView = etEmail;
            cancel = true;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            tilEmail.setError(getString(R.string.invalid_email));
            focusView = etEmail;
            cancel = true;
        }
        
        // Validate full name
        if (TextUtils.isEmpty(fullName)) {
            tilFullName.setError(getString(R.string.field_required));
            focusView = etFullName;
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
            performRegister(username, fullName, email, password);
        }
    }
    
    private void performRegister(String username, String fullName, String email, String password) {
        // Show loading
        btnRegister.setEnabled(false);
        btnRegister.setText(R.string.loading);
        
        RegisterRequest request = new RegisterRequest(username, fullName, email, password);
        
        apiService.register(request).enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                btnRegister.setEnabled(true);
                btnRegister.setText(R.string.register);
                
                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(RegisterActivity.this, R.string.register_success, Toast.LENGTH_SHORT).show();
                    
                    // Navigate to login
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại. Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                btnRegister.setEnabled(true);
                btnRegister.setText(R.string.register);
                Toast.makeText(RegisterActivity.this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
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
        startActivity(new Intent(this, LoginActivity.class));
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
