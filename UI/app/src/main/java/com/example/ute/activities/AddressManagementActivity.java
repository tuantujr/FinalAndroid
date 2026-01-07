package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.adapters.AddressAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Address;
import com.example.ute.models.response.AddressListResponse;
import com.example.ute.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressManagementActivity extends AppCompatActivity {
    
    private static final String TAG = "AddressManagementActivity";
    
    private RecyclerView rvAddresses;
    private ProgressBar progressBar;
    private TextView tvEmptyState;
    private Button btnAddAddress;
    private ImageButton btnBack;
    
    private AddressAdapter addressAdapter;
    private SessionManager sessionManager;
    private ApiService apiService;
    private List<Address> addresses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_management);
        
        sessionManager = new SessionManager(this);
        apiService = ApiClient.getApiService();
        
        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, R.string.please_login, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        initViews();
        setupRecyclerView();
        setupListeners();
        loadAddresses();
    }
    
    private void initViews() {
        rvAddresses = findViewById(R.id.rvAddresses);
        progressBar = findViewById(R.id.progressBar);
        tvEmptyState = findViewById(R.id.tvEmptyState);
        btnAddAddress = findViewById(R.id.btnAddAddress);
        btnBack = findViewById(R.id.btnBack);
    }
    
    private void setupRecyclerView() {
        addressAdapter = new AddressAdapter(addresses, address -> {
            // Handle address click (edit)
            Intent intent = new Intent(this, AddAddressActivity.class);
            intent.putExtra("address_id", address.getId());
            startActivity(intent);
        }, address -> {
            // Handle delete
            deleteAddress(address.getId());
        });
        
        rvAddresses.setLayoutManager(new LinearLayoutManager(this));
        rvAddresses.setAdapter(addressAdapter);
    }
    
    private void setupListeners() {
        btnAddAddress.setOnClickListener(v -> {
            startActivity(new Intent(this, AddAddressActivity.class));
        });
        
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void loadAddresses() {
        showLoading(true);
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.getAddresses(token).enqueue(new Callback<AddressListResponse>() {
            @Override
            public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                showLoading(false);
                
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        AddressListResponse addressResponse = response.body();
                        if (addressResponse.isSuccess() && addressResponse.getData() != null) {
                            addresses.clear();
                            addresses.addAll(addressResponse.getData());
                            addressAdapter.notifyDataSetChanged();
                            
                            if (addresses.isEmpty()) {
                                showEmptyState(true);
                            } else {
                                showEmptyState(false);
                            }
                        } else {
                            String msg = addressResponse.getMessage() != null ? addressResponse.getMessage() : "Lỗi tải địa chỉ";
                            Toast.makeText(AddressManagementActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.w(TAG, "Response not successful: " + response.code());
                        Toast.makeText(AddressManagementActivity.this, "Lỗi tải địa chỉ", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception loading addresses", e);
                    Toast.makeText(AddressManagementActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<AddressListResponse> call, Throwable t) {
                showLoading(false);
                Log.e(TAG, "Request failed", t);
                Toast.makeText(AddressManagementActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void deleteAddress(Long addressId) {
        showLoading(true);
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.deleteAddress(token, addressId).enqueue(new Callback<com.example.ute.models.response.ApiResponse>() {
            @Override
            public void onResponse(Call<com.example.ute.models.response.ApiResponse> call, Response<com.example.ute.models.response.ApiResponse> response) {
                showLoading(false);
                
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        com.example.ute.models.response.ApiResponse apiResponse = response.body();
                        if (apiResponse.isSuccess()) {
                            Toast.makeText(AddressManagementActivity.this, "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                            loadAddresses();
                        } else {
                            Toast.makeText(AddressManagementActivity.this, apiResponse.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(AddressManagementActivity.this, "Xóa địa chỉ thất bại", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception deleting address", e);
                }
            }
            
            @Override
            public void onFailure(Call<com.example.ute.models.response.ApiResponse> call, Throwable t) {
                showLoading(false);
                Log.e(TAG, "Delete request failed", t);
                Toast.makeText(AddressManagementActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
    }
    
    private void showEmptyState(boolean show) {
        tvEmptyState.setVisibility(show ? View.VISIBLE : View.GONE);
        rvAddresses.setVisibility(show ? View.GONE : View.VISIBLE);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        loadAddresses();
    }
}
