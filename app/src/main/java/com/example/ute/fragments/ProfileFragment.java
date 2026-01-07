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
import com.example.ute.models.User;
import com.example.ute.utils.SessionManager;

public class ProfileFragment extends Fragment {
    
    private LinearLayout layoutLoggedIn, layoutGuest;
    private ImageView ivAvatar;
    private TextView tvUserName, tvUserEmail;
    private LinearLayout menuEditProfile, menuChangePassword, menuAddresses;
    private LinearLayout menuOrders, menuOrderTracking, menuHelp, menuAbout;
    private LinearLayout menuLogout, menuLogin;
    
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        sessionManager = new SessionManager(requireContext());
        
        initViews(view);
        setupListeners();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }
    
    private void initViews(View view) {
        layoutLoggedIn = view.findViewById(R.id.layoutLoggedIn);
        layoutGuest = view.findViewById(R.id.layoutGuest);
        ivAvatar = view.findViewById(R.id.ivAvatar);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvUserEmail = view.findViewById(R.id.tvUserEmail);
        
        menuEditProfile = view.findViewById(R.id.menuEditProfile);
        menuChangePassword = view.findViewById(R.id.menuChangePassword);
        menuAddresses = view.findViewById(R.id.menuAddresses);
        menuOrders = view.findViewById(R.id.menuOrders);
        menuOrderTracking = view.findViewById(R.id.menuOrderTracking);
        menuHelp = view.findViewById(R.id.menuHelp);
        menuAbout = view.findViewById(R.id.menuAbout);
        menuLogout = view.findViewById(R.id.menuLogout);
        menuLogin = view.findViewById(R.id.menuLogin);
    }
    
    private void setupListeners() {
        menuEditProfile.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), EditProfileActivity.class));
        });
        
        menuChangePassword.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
        });
        
        menuAddresses.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), AddressActivity.class));
        });
        
        menuOrders.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), OrderHistoryActivity.class));
        });
        
        menuOrderTracking.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), OrderTrackingActivity.class));
        });
        
        menuHelp.setOnClickListener(v -> {
            // TODO: Open help page
        });
        
        menuAbout.setOnClickListener(v -> {
            // TODO: Open about page
        });
        
        menuLogout.setOnClickListener(v -> showLogoutDialog());
        
        menuLogin.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        });
    }
    
    private void updateUI() {
        if (sessionManager.isLoggedIn()) {
            layoutLoggedIn.setVisibility(View.VISIBLE);
            layoutGuest.setVisibility(View.GONE);
            
            User user = sessionManager.getUser();
            if (user != null) {
                tvUserName.setText(user.getFullName());
                tvUserEmail.setText(user.getEmail());
                
                if (user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                    Glide.with(this)
                            .load(user.getAvatarUrl())
                            .placeholder(R.drawable.ic_person)
                            .circleCrop()
                            .into(ivAvatar);
                }
            }
        } else {
            layoutLoggedIn.setVisibility(View.GONE);
            layoutGuest.setVisibility(View.VISIBLE);
        }
    }
    
    private void showLogoutDialog() {
        new AlertDialog.Builder(requireContext())
                .setTitle(R.string.logout)
                .setMessage(R.string.logout_confirm)
                .setPositiveButton(R.string.confirm, (dialog, which) -> {
                    sessionManager.logout();
                    updateUI();
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }
}
