package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ute.R;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.request.UpdateProfileRequest;
import com.example.ute.models.response.UserDataResponse;
import com.example.ute.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditProfileActivity extends AppCompatActivity {

    private TextInputEditText etFullName, etEmail, etPhone;
    private MaterialButton btnSave;
    private SessionManager sessionManager;
    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

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
        loadUserData();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.edit_profile);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initViews() {
        etFullName = findViewById(R.id.etFullName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(v -> saveProfile());
    }

    private void loadUserData() {
        etFullName.setText(sessionManager.getUserFullName());
        etEmail.setText(sessionManager.getUserEmail());
        etPhone.setText(sessionManager.getUserPhone());
    }

    private void saveProfile() {
        String fullName = etFullName.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        if (fullName.isEmpty()) {
            etFullName.setError(getString(R.string.field_required));
            return;
        }

        if (email.isEmpty()) {
            etEmail.setError(getString(R.string.field_required));
            return;
        }

        if (phone.isEmpty()) {
            etPhone.setError(getString(R.string.field_required));
            return;
        }

        btnSave.setEnabled(false);

        // Create update profile request with only fullName and phoneNumber
        UpdateProfileRequest updateRequest = new UpdateProfileRequest(fullName, phone);

        String token = "Bearer " + sessionManager.getAuthToken();
        apiService.updateProfile(token, updateRequest).enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {
                btnSave.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    UserDataResponse userResponse = response.body();
                    if (userResponse.isSuccess() && userResponse.getData() != null) {
                        // Update session manager with new data
                        sessionManager.updateUser(userResponse.getData());
                        
                        Toast.makeText(EditProfileActivity.this, "Cập nhật hồ sơ thành công!", Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        String message = userResponse.getMessage() != null ? userResponse.getMessage() : "Lỗi cập nhật hồ sơ";
                        Toast.makeText(EditProfileActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("EditProfile", "Error code: " + response.code() + " Message: " + response.message());
                    Toast.makeText(EditProfileActivity.this, "Lỗi cập nhật hồ sơ: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDataResponse> call, Throwable t) {
                btnSave.setEnabled(true);
                Log.e("EditProfile", "API Error: " + t.getMessage());
                Toast.makeText(EditProfileActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
