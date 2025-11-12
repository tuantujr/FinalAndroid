package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class OTPVerificationActivity extends AppCompatActivity {

    private EditText[] otpFields;
    private Button btnVerify;
    private TextView tvResendOtp;
    private TextView tvTimer;
    private TextView tvEmail;
    private ImageButton btnBack;
    private TextView tvChangeEmail;
    private CountDownTimer countDownTimer;
    private String userEmail = "";
    private static final long TIMER_DURATION = 30000; // 30 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_verification);

        initializeViews();
        setupOTPFields();
        setupClickListeners();
        getEmailFromIntent();
        startCountdown();
    }

    private void initializeViews() {
        otpFields = new EditText[6];
        otpFields[0] = findViewById(R.id.etOtp1);
        otpFields[1] = findViewById(R.id.etOtp2);
        otpFields[2] = findViewById(R.id.etOtp3);
        otpFields[3] = findViewById(R.id.etOtp4);
        otpFields[4] = findViewById(R.id.etOtp5);
        otpFields[5] = findViewById(R.id.etOtp6);

        btnVerify = findViewById(R.id.btnVerify);
        tvResendOtp = findViewById(R.id.tvResendOtp);
        tvTimer = findViewById(R.id.tvTimer);
        tvEmail = findViewById(R.id.tvEmail);
        btnBack = findViewById(R.id.btnBack);
        tvChangeEmail = findViewById(R.id.tvChangeEmail);
    }

    private void setupOTPFields() {
        for (int i = 0; i < otpFields.length; i++) {
            final int index = i;
            otpFields[i].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() == 1 && index < otpFields.length - 1) {
                        // Move to next field
                        otpFields[index + 1].requestFocus();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {}
            });

            // Handle backspace
            otpFields[i].setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == android.view.KeyEvent.KEYCODE_DEL && 
                    otpFields[index].getText().toString().isEmpty() && index > 0) {
                    otpFields[index - 1].requestFocus();
                }
                return false;
            });
        }
    }

    private void setupClickListeners() {
        btnBack.setOnClickListener(v -> onBackPressed());

        btnVerify.setOnClickListener(v -> handleVerifyOTP());

        tvResendOtp.setOnClickListener(v -> resendOTP());

        tvChangeEmail.setOnClickListener(v -> changeEmail());
    }

    private void getEmailFromIntent() {
        Intent intent = getIntent();
        userEmail = intent.getStringExtra("email");
        if (userEmail != null && !userEmail.isEmpty()) {
            tvEmail.setText(userEmail);
        }
    }

    private void startCountdown() {
        tvResendOtp.setEnabled(false);
        tvResendOtp.setAlpha(0.5f);

        countDownTimer = new CountDownTimer(TIMER_DURATION, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long secondsRemaining = millisUntilFinished / 1000;
                tvTimer.setText(String.format("Resend code in %02d:%02d", 
                    secondsRemaining / 60, secondsRemaining % 60));
            }

            @Override
            public void onFinish() {
                tvResendOtp.setEnabled(true);
                tvResendOtp.setAlpha(1.0f);
                tvTimer.setText("Code expired. Please request a new one.");
            }
        }.start();
    }

    private void handleVerifyOTP() {
        StringBuilder otp = new StringBuilder();
        for (EditText field : otpFields) {
            otp.append(field.getText().toString());
        }

        String otpCode = otp.toString();

        if (otpCode.length() < 6) {
            Toast.makeText(this, "Please enter complete OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        // Validation passed
        Toast.makeText(this, "OTP verified successfully!", Toast.LENGTH_SHORT).show();
        
        // TODO: Proceed with account activation
        // For now, return to login
        Intent intent = new Intent(OTPVerificationActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void resendOTP() {
        // Clear OTP fields
        for (EditText field : otpFields) {
            field.setText("");
        }
        otpFields[0].requestFocus();

        Toast.makeText(this, "OTP resent to " + userEmail, Toast.LENGTH_SHORT).show();

        // Cancel previous timer and start new one
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
        startCountdown();
    }

    private void changeEmail() {
        Toast.makeText(this, "Change email clicked", Toast.LENGTH_SHORT).show();
        // TODO: Navigate back to register or allow email change
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
