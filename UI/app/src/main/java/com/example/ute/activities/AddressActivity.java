package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.adapters.AddressAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Address;
import com.example.ute.models.response.AddressListResponse;
import com.example.ute.utils.SessionManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    private static final String TAG = "AddressActivity";
    
    private RecyclerView rvAddresses;
    private FloatingActionButton fabAddAddress;
    private SessionManager sessionManager;
    private ApiService apiService;
    private AddressAdapter addressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);

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
        setupListeners();
        loadAddresses();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.addresses);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initViews() {
        rvAddresses = findViewById(R.id.rvAddresses);
        rvAddresses.setLayoutManager(new LinearLayoutManager(this));
        
        fabAddAddress = findViewById(R.id.fabAddAddress);
    }
    
    private void setupListeners() {
        fabAddAddress.setOnClickListener(v -> {
            Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
            startActivity(intent);
        });
    }
    
    private void loadAddresses() {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.getAddresses(token).enqueue(new Callback<AddressListResponse>() {
            @Override
            public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        AddressListResponse addressListResponse = response.body();
                        if (addressListResponse.isSuccess() && addressListResponse.getData() != null) {
                            List<Address> addresses = addressListResponse.getData();
                            setupAdapter(addresses);
                        } else {
                            Log.w(TAG, "Response not successful or data is null");
                            setupAdapter(new ArrayList<>());
                        }
                    } else {
                        Log.w(TAG, "Response not successful: " + response.code());
                        setupAdapter(new ArrayList<>());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception loading addresses", e);
                    setupAdapter(new ArrayList<>());
                }
            }

            @Override
            public void onFailure(Call<AddressListResponse> call, Throwable t) {
                Log.e(TAG, "Request failed", t);
                setupAdapter(new ArrayList<>());
            }
        });
    }
    
    private void setupAdapter(List<Address> addresses) {
        addressAdapter = new AddressAdapter(
            addresses,
            address -> editAddress(address),
            address -> deleteAddress(address)
        );
        rvAddresses.setAdapter(addressAdapter);
    }
    
    private void editAddress(Address address) {
        Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
        intent.putExtra("address_id", address.getId());
        startActivity(intent);
    }
    
    private void deleteAddress(Address address) {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.deleteAddress(token, address.getId()).enqueue(new Callback<com.example.ute.models.response.ApiResponse>() {
            @Override
            public void onResponse(Call<com.example.ute.models.response.ApiResponse> call, Response<com.example.ute.models.response.ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(AddressActivity.this, "Xóa địa chỉ thành công", Toast.LENGTH_SHORT).show();
                    loadAddresses();
                } else {
                    Toast.makeText(AddressActivity.this, "Lỗi xóa địa chỉ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.ute.models.response.ApiResponse> call, Throwable t) {
                Log.e(TAG, "Delete request failed", t);
                Toast.makeText(AddressActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        // Reload addresses when returning from AddAddressActivity
        loadAddresses();
    }
}
