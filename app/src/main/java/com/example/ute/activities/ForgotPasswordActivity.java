package com.example.ute.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ute.R;

public class ForgotPasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        
        // TODO: Implement forgot password flow
        Toast.makeText(this, "Forgot Password - Coming Soon", Toast.LENGTH_SHORT).show();
    }
}
