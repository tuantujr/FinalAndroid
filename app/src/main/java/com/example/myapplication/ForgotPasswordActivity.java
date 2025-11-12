package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ForgotPasswordActivity extends AppCompatActivity {

    // Step 1: Email
    private EditText etEmail;
    private Button btnSendOtp;

    // Step 2: OTP Verification
    private EditText[] otpFields;
    private Button btnVerifyOtp;
    private TextView tvResendOtp;
    private TextView tvChangeEmail;

    // Step 3: New Password
    private EditText etNewPassword;
    private EditText etConfirmPassword;
    private ImageView ivTogglePassword;
    private ImageView ivToggleConfirmPassword;
    private Button btnResetPassword;

    // Layout containers
    private LinearLayout layoutStep1;
    private LinearLayout layoutStep2;
    private LinearLayout layoutStep3;

    // Common
    private ImageButton btnBack;
    private TextView tvTimer;
    private CountDownTimer countDownTimer;
    private static final long TIMER_DURATION = 30000;
    private String userEmail = "";
    private int currentStep = 1;
    private boolean isPasswordVisible = false;
    private boolean isConfirmPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        initializeViews();
        setupClickListeners();
        displayStep(1);
    }

    private void initializeViews() {
        // Step 1
        etEmail = findViewById(R.id.etEmail);
        btnSendOtp = findViewById(R.id.btnSendOtp);

        // Step 2
        otpFields = new EditText[6];
        otpFields[0] = findViewById(R.id.etOtp1);
        otpFields[1] = findViewById(R.id.etOtp2);
        otpFields[2] = findViewById(R.id.etOtp3);
        otpFields[3] = findViewById(R.id.etOtp4);
        otpFields[4] = findViewById(R.id.etOtp5);
        otpFields[5] = findViewById(R.id.etOtp6);
        btnVerifyOtp = findViewById(R.id.btnVerifyOtp);
        tvResendOtp = findViewById(R.id.tvResendOtp);
        tvChangeEmail = findViewById(R.id.tvChangeEmail);
        tvTimer = findViewById(R.id.tvTimer);

        // Step 3
        etNewPassword = findViewById(R.id.etNewPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        ivTogglePassword = findViewById(R.id.ivTogglePassword);
        ivToggleConfirmPassword = findViewById(R.id.ivToggleConfirmPassword);
        btnResetPassword = findViewById(R.id.btnResetPassword);

        // Layout containers
        layoutStep1 = findViewById(R.id.layoutStep1);
        layoutStep2 = findViewById(R.id.layoutStep2);
        layoutStep3 = findViewById(R.id.layoutStep3);

        // Common
        btnBack = findViewById(R.id.btnBack);

        setupOTPFields();
    }

    private void setupClickListeners() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> onBackPressed());
        }

        // Step 1
        if (btnSendOtp != null) {
            btnSendOtp.setOnClickListener(v -> handleSendOtp());
        }

        // Step 2
        if (btnVerifyOtp != null) {
            btnVerifyOtp.setOnClickListener(v -> handleVerifyOtp());
        }
        if (tvResendOtp != null) {
            tvResendOtp.setOnClickListener(v -> resendOtp());
        }
        if (tvChangeEmail != null) {
            tvChangeEmail.setOnClickListener(v -> goBackToStep1());
        }

        // Step 3
        if (ivTogglePassword != null) {
            ivTogglePassword.setOnClickListener(v -> togglePasswordVisibility());
        }
        if (ivToggleConfirmPassword != null) {
            ivToggleConfirmPassword.setOnClickListener(v -> toggleConfirmPasswordVisibility());
        }
        if (btnResetPassword != null) {
            btnResetPassword.setOnClickListener(v -> handleResetPassword());
        }
    }

    private void setupOTPFields() {
        for (int i = 0; i < otpFields.length; i++) {
            if (otpFields[i] == null) {
                continue;
            }

            final int index = i;
            otpFields[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1 && index < otpFields.length - 1) {
                        if (otpFields[index + 1] != null) {
                            otpFields[index + 1].requestFocus();
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            otpFields[i].setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == android.view.KeyEvent.KEYCODE_DEL && 
                    otpFields[index].getText().toString().isEmpty() && index > 0) {
                    if (otpFields[index - 1] != null) {
                        otpFields[index - 1].requestFocus();
                    }
                }
                return false;
            });
        }
    }

    private void displayStep(int step) {
        currentStep = step;
        layoutStep1.setVisibility(View.GONE);
        layoutStep2.setVisibility(View.GONE);
        layoutStep3.setVisibility(View.GONE);

        switch (step) {
            case 1:
                layoutStep1.setVisibility(View.VISIBLE);
                break;
            case 2:
                layoutStep2.setVisibility(View.VISIBLE);
                startCountdown();
                otpFields[0].requestFocus();
                break;
            case 3:
                layoutStep3.setVisibility(View.VISIBLE);
                if (countDownTimer != null) {
                    countDownTimer.cancel();
                }
                etNewPassword.requestFocus();
                break;
        }
    }

    private void handleSendOtp() {
        userEmail = etEmail.getText().toString().trim();

        if (userEmail.isEmpty()) {
            etEmail.setError("Email is required");
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
            etEmail.setError("Please enter a valid email");
            return;
        }

        Toast.makeText(this, "OTP sent to " + userEmail, Toast.LENGTH_SHORT).show();
        displayStep(2);
    }

    private void handleVerifyOtp() {
        StringBuilder otp = new StringBuilder();
        for (EditText field : otpFields) {
            if (field != null) {
                otp.append(field.getText().toString());
            }
        }

        String otpCode = otp.toString();

        if (otpCode.length() < 6) {
            Toast.makeText(this, "Please enter complete OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        Toast.makeText(this, "OTP verified successfully!", Toast.LENGTH_SHORT).show();
        displayStep(3);
    }

    private void handleResetPassword() {
        String newPassword = etNewPassword.getText().toString();
        String confirmPassword = etConfirmPassword.getText().toString();

        if (newPassword.isEmpty()) {
            etNewPassword.setError("Password is required");
            return;
        }

        if (newPassword.length() < 6) {
            etNewPassword.setError("Password must be at least 6 characters");
            return;
        }

        if (!newPassword.equals(confirmPassword)) {
            etConfirmPassword.setError("Passwords do not match");
            return;
        }

        Toast.makeText(this, "Password reset successfully!", Toast.LENGTH_SHORT).show();
        
        // Navigate back to login
        Intent intent = new Intent(ForgotPasswordActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void togglePasswordVisibility() {
        if (isPasswordVisible) {
            etNewPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
            ivTogglePassword.setImageResource(R.drawable.ic_eye_off);
            isPasswordVisible = false;
        } else {
            etNewPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            ivTogglePassword.setImageResource(R.drawable.ic_eye);
            isPasswordVisible = true;
        }
        etNewPassword.setSelection(etNewPassword.getText().length());
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

    private void startCountdown() {
        if (tvResendOtp != null) {
            tvResendOtp.setEnabled(false);
            tvResendOtp.setAlpha(0.5f);
        }

        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                if (tvTimer != null) {
                    long secondsRemaining = millisUntilFinished / 1000;
                    tvTimer.setText(String.format("Resend code in %02d:%02d", 
                        secondsRemaining / 60, secondsRemaining % 60));
                }
            }

            @Override
            public void onFinish() {
                if (tvResendOtp != null) {
                    tvResendOtp.setEnabled(true);
                    tvResendOtp.setAlpha(1.0f);
                }
                if (tvTimer != null) {
                    tvTimer.setText("Code expired");
                }
            }
        }.start();
    }

    private void resendOtp() {
        // Clear OTP fields
        for (EditText field : otpFields) {
            if (field != null) {
                field.setText("");
            }
        }
        if (otpFields[0] != null) {
            otpFields[0].requestFocus();
        }

        Toast.makeText(this, "OTP resent to " + userEmail, Toast.LENGTH_SHORT).show();

        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        startCountdown();
    }

    private void goBackToStep1() {
        etEmail.setText("");
        for (EditText field : otpFields) {
            field.setText("");
        }
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        displayStep(1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
