package com.example.ute.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ute.R;
import com.example.ute.activities.AddressActivity;
import com.example.ute.activities.ChangePasswordActivity;
import com.example.ute.activities.EditProfileActivity;
import com.example.ute.activities.LoginActivity;
import com.example.ute.activities.OrderHistoryActivity;
import com.example.ute.activities.OrderTrackingActivity;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.User;
import com.example.ute.models.response.UserDataResponse;
import com.example.ute.utils.SessionManager;
import com.google.android.material.button.MaterialButton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {
    
    private ImageView ivAvatar;
    private TextView tvUserName, tvUserEmail;
    private MaterialButton btnLogin, btnLogout;
    private LinearLayout btnEditProfile, btnChangePassword, btnAddresses;
    private LinearLayout btnOrderHistory;
    private LinearLayout btnHelp, btnAbout;
    
    private SessionManager sessionManager;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        sessionManager = new SessionManager(requireContext());
        apiService = ApiClient.getApiService();
        
        initViews(view);
        setupListeners();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (sessionManager.isLoggedIn()) {
            fetchUserInfo();
        } else {
            updateUI();
        }
    }
    
    private void initViews(View view) {
        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        btnLogin = view.findViewById(R.id.btnLogin);
        btnLogout = view.findViewById(R.id.btnLogout);
        
        btnEditProfile = view.findViewById(R.id.btnEditProfile);
        btnChangePassword = view.findViewById(R.id.btnChangePassword);
        btnAddresses = view.findViewById(R.id.btnAddresses);
        btnOrderHistory = view.findViewById(R.id.btnOrderHistory);
        btnHelp = view.findViewById(R.id.btnHelp);
        btnAbout = view.findViewById(R.id.btnAbout);
    }
    
    private void setupListeners() {
        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
        
        btnLogout.setOnClickListener(v -> showLogoutDialog());
        
        btnEditProfile.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), EditProfileActivity.class));
        });
        
        btnChangePassword.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
        });
        
        btnAddresses.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddressActivity.class));
        });
        
        btnOrderHistory.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), OrderHistoryActivity.class));
        });
        
        btnHelp.setOnClickListener(v -> {
            // TODO: Open help page
        });
        
        btnAbout.setOnClickListener(v -> {
            // TODO: Open about page
        });
    }
    
    private void updateUI() {
        if (sessionManager.isLoggedIn()) {
            btnLogin.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
            
            User user = sessionManager.getUser();
            if (user != null) {
                String displayName = user.getFullName();
                if (displayName == null || displayName.isEmpty()) {
                    displayName = user.getUsername();
                }
                tvUserName.setText(displayName != null ? displayName : "User");
                tvUserEmail.setText(user.getEmail() != null ? user.getEmail() : "");
                
                if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                    Glide.with(this)
                            .load(user.getAvatarUrl())
                            .placeholder(R.drawable.ic_person)
                            .circleCrop()
                            .into(ivAvatar);
                }
            } else {
                tvUserName.setText("Đã đăng nhập");
                tvUserEmail.setText("");
            }
        } else {
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            tvUserName.setText(R.string.guest);
            tvUserEmail.setText("");
        }
    }
    
    private void fetchUserInfo() {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.getCurrentUser(token).enqueue(new Callback<UserDataResponse>() {
            @Override
            public void onResponse(Call<UserDataResponse> call, Response<UserDataResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserDataResponse userResponse = response.body();
                    if (userResponse.isSuccess() && userResponse.getData() != null) {
                        sessionManager.saveUser(userResponse.getData());
                    }
                }
                updateUI();
            }

            @Override
            public void onFailure(Call<UserDataResponse> call, Throwable t) {
                // Use cached user info
                updateUI();
            }
        });
    }
    
    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.logout)
                .setMessage(R.string.logout_confirm)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    performLogout();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
    
    private void performLogout() {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.logout(token).enqueue(new Callback<com.example.ute.models.response.ApiResponse>() {
            @Override
            public void onResponse(Call<com.example.ute.models.response.ApiResponse> call, Response<com.example.ute.models.response.ApiResponse> response) {
                // Clear local session regardless of server response
                sessionManager.logout();
                updateUI();
            }

            @Override
            public void onFailure(Call<com.example.ute.models.response.ApiResponse> call, Throwable t) {
                // Clear local session even if server call fails
                sessionManager.logout();
                updateUI();
            }
        });
    }
}
