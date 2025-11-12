package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText etUsername;
    private EditText etPassword;
    private ImageView ivTogglePassword;
    private Button btnLogin;
    private TextView tvForgotPassword;
    private TextView tvSignUp;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvForgotPassword = findViewById(R.id.tvForgotPassword);
        tvSignUp = findViewById(R.id.tvSignUp);
    }

    private void setupClickListeners() {
        // Toggle password visibility
        ivTogglePassword.setOnClickListener(v -> togglePasswordVisibility());

        // Login button
        btnLogin.setOnClickListener(v -> handleLogin());

        // Forgot password
        tvForgotPassword.setOnClickListener(v -> navigateToForgotPassword());

        // Sign up
        tvSignUp.setOnClickListener(v -> navigateToRegister());

        // Social login buttons
        findViewById(R.id.btnGoogle).setOnClickListener(v -> 
            Toast.makeText(this, "Google login clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnFacebook).setOnClickListener(v -> 
            Toast.makeText(this, "Facebook login clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnGithub).setOnClickListener(v -> 
            Toast.makeText(this, "Github login clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnLinkedin).setOnClickListener(v -> 
            Toast.makeText(this, "LinkedIn login clicked", Toast.LENGTH_SHORT).show());
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivTogglePassword.setImageResource(R.drawable.ic_eye_off);
            isPasswordVisible = false;
        } else {
            etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivTogglePassword.setImageResource(R.drawable.ic_eye);
            isPasswordVisible = true;
        }
        etPassword.setSelection(etPassword.getText().length());
    }

    private void handleLogin() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();

        if (username.isEmpty()) {
            etUsername.setError("Username/Email is required");
            return;
        }

        if (password.isEmpty()) {
            etPassword.setError("Password is required");
            return;
        }

        if (password.length() < 6) {
            etPassword.setError("Password must be at least 6 characters");
            return;
        }

        // Validation passed
        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show();
        // TODO: Proceed with login logic (backend call)
    }

    private void navigateToForgotPassword() {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordActivity.class));
    }

    private void navigateToRegister() {
        startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
    }
}
