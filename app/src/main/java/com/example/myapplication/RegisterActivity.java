package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText etFullName;
    private EditText etUsername;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etConfirmPassword;
    private ImageView ivTogglePassword;
    private ImageView ivToggleConfirmPassword;
    private CheckBox cbTerms;
    private Button btnRegister;
    private TextView tvTerms;
    private TextView tvSignIn;
    private ImageButton btnBack;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initializeViews();
        setupClickListeners();
    }

    private void initializeViews() {
        btnBack = findViewById(R.id.btnBack);
        etFullName = findViewById(R.id.etFullName);
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);
        ivToggleConfirmPassword = findViewById(R.id.ivToggleConfirmPassword);
        cbTerms = findViewById(R.id.cbTerms);
        btnRegister = findViewById(R.id.btnRegister);
        tvTerms = findViewById(R.id.tvTerms);
        tvSignIn = findViewById(R.id.tvSignIn);
    }

    private void setupClickListeners() {
        // Back button
        btnBack.setOnClickListener(v -> onBackPressed());

        // Toggle password visibility
        ivTogglePassword.setOnClickListener(v -> togglePasswordVisibility());
        ivToggleConfirmPassword.setOnClickListener(v -> toggleConfirmPasswordVisibility());

        // Register button
        btnRegister.setOnClickListener(v -> handleRegister());

        // Terms & Conditions
        tvTerms.setOnClickListener(v -> 
            Toast.makeText(this, "Terms & Conditions clicked", Toast.LENGTH_SHORT).show());

        // Sign in
        tvSignIn.setOnClickListener(v -> navigateToLogin());

        // Social register buttons
        findViewById(R.id.btnGoogle).setOnClickListener(v -> 
            Toast.makeText(this, "Google register clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnFacebook).setOnClickListener(v -> 
            Toast.makeText(this, "Facebook register clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnGithub).setOnClickListener(v -> 
            Toast.makeText(this, "Github register clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.btnLinkedin).setOnClickListener(v -> 
            Toast.makeText(this, "LinkedIn register clicked", Toast.LENGTH_SHORT).show());
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

    private void toggleConfirmPasswordVisibility() {
        if (isConfirmPasswordVisible) {
            etConfirmPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivToggleConfirmPassword.setImageResource(R.drawable.ic_eye_off);
            isConfirmPasswordVisible = false;
        } else {
            etConfirmPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivToggleConfirmPassword.setImageResource(R.drawable.ic_eye);
            isConfirmPasswordVisible = true;
        }
        etConfirmPassword.setSelection(etConfirmPassword.getText().length());
    }

    private void handleRegister() {
        String fullName = etFullName.getText().toString().trim();
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        // Validation
        if (fullName.isEmpty()) {
            etFullName.setError("Full name is required");
            return;
        }

        if (username.isEmpty()) {
            etUsername.setError("Username is required");
            return;
        }

        if (username.length() < 3) {
            etUsername.setError("Username must be at least 3 characters");
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError("Email is required");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            etEmail.setError("Please enter a valid email");
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

        if (!password.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        if (!cbTerms.isChecked()) {
            Toast.makeText(this, "Please agree to Terms & Conditions", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validation passed
        Toast.makeText(this, "Registration successful! Verify OTP", Toast.LENGTH_SHORT).show();
        
        // Navigate to OTP verification
        Intent intent = new Intent(RegisterActivity.this, OTPVerificationActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
    }

    private void navigateToLogin() {
        finish();
    }
}
