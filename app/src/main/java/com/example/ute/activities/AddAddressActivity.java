package com.example.ute.activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ute.R;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.request.AddressRequest;
import com.example.ute.models.response.AddressResponse;
import com.example.ute.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {
    
    private static final String TAG = "AddAddressActivity";
    
    private TextInputLayout tilRecipientName, tilPhoneNumber, tilStreet, tilWard, tilDistrict, tilCity;
    private TextInputEditText etRecipientName, etPhoneNumber, etStreet, etWard, etDistrict, etCity;
    private CheckBox cbIsDefault;
    private Button btnSaveAddress;
    private ImageButton btnBack;
    private ProgressBar progressBar;
    
    private SessionManager sessionManager;
    private ApiService apiService;
    private Long addressId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        
        sessionManager = new SessionManager(this);
        apiService = ApiClient.getApiService();
        
        // Check if editing existing address
        addressId = getIntent().getLongExtra("address_id", -1);
        
        initViews();
        setupListeners();
    }
    
    private void initViews() {
        tilRecipientName = findViewById(R.id.tilRecipientName);
        tilPhoneNumber = findViewById(R.id.tilPhoneNumber);
        tilStreet = findViewById(R.id.tilStreet);
        tilWard = findViewById(R.id.tilWard);
        tilDistrict = findViewById(R.id.tilDistrict);
        tilCity = findViewById(R.id.tilCity);
        
        etRecipientName = findViewById(R.id.etRecipientName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etStreet = findViewById(R.id.etStreet);
        etWard = findViewById(R.id.etWard);
        etDistrict = findViewById(R.id.etDistrict);
        etCity = findViewById(R.id.etCity);
        
        cbIsDefault = findViewById(R.id.cbIsDefault);
        btnSaveAddress = findViewById(R.id.btnSaveAddress);
        btnBack = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
    }
    
    private void setupListeners() {
        btnSaveAddress.setOnClickListener(v -> attemptSaveAddress());
        btnBack.setOnClickListener(v -> finish());
    }
    
    private void attemptSaveAddress() {
        // Reset errors
        tilRecipientName.setError(null);
        tilPhoneNumber.setError(null);
        tilStreet.setError(null);
        tilWard.setError(null);
        tilDistrict.setError(null);
        tilCity.setError(null);
        
        String recipientName = etRecipientName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String street = etStreet.getText().toString().trim();
        String ward = etWard.getText().toString().trim();
        String district = etDistrict.getText().toString().trim();
        String city = etCity.getText().toString().trim();
        Boolean isDefault = cbIsDefault.isChecked();
        
        boolean cancel = false;
        View focusView = null;
        
        // Validate city
        if (TextUtils.isEmpty(city)) {
            tilCity.setError(getString(R.string.field_required));
            focusView = etCity;
            cancel = true;
        }
        
        // Validate district
        if (TextUtils.isEmpty(district)) {
            tilDistrict.setError(getString(R.string.field_required));
            focusView = etDistrict;
            cancel = true;
        }
        
        // Validate ward
        if (TextUtils.isEmpty(ward)) {
            tilWard.setError(getString(R.string.field_required));
            focusView = etWard;
            cancel = true;
        }
        
        // Validate street
        if (TextUtils.isEmpty(street)) {
            tilStreet.setError(getString(R.string.field_required));
            focusView = etStreet;
            cancel = true;
        }
        
        // Validate phone number
        if (TextUtils.isEmpty(phoneNumber)) {
            tilPhoneNumber.setError(getString(R.string.field_required));
            focusView = etPhoneNumber;
            cancel = true;
        }
        
        // Validate recipient name
        if (TextUtils.isEmpty(recipientName)) {
            tilRecipientName.setError(getString(R.string.field_required));
            focusView = etRecipientName;
            cancel = true;
        }
        
        if (cancel) {
            focusView.requestFocus();
        } else {
            performSaveAddress(recipientName, phoneNumber, street, ward, district, city, isDefault);
        }
    }
    
    private void performSaveAddress(String recipientName, String phoneNumber, String street, String ward, String district, String city, Boolean isDefault) {
        showLoading(true);
        String token = "Bearer " + sessionManager.getAuthToken();
        AddressRequest request = new AddressRequest(recipientName, phoneNumber, street, ward, district, city, isDefault);
        
        Call<AddressResponse> call;
        if (addressId != -1) {
            // Update existing address
            call = apiService.updateAddress(token, addressId, request);
        } else {
            // Add new address
            call = apiService.addAddress(token, request);
        }
        
        call.enqueue(new Callback<AddressResponse>() {
            @Override
            public void onResponse(Call<AddressResponse> call, Response<AddressResponse> response) {
                showLoading(false);
                
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        AddressResponse addressResponse = response.body();
                        if (addressResponse.isSuccess()) {
                            Toast.makeText(AddAddressActivity.this, "Lưu địa chỉ thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            String msg = addressResponse.getMessage() != null ? addressResponse.getMessage() : "Lỗi lưu địa chỉ";
                            Toast.makeText(AddAddressActivity.this, msg, Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Log.w(TAG, "Response not successful: " + response.code());
                        Toast.makeText(AddAddressActivity.this, "Lỗi lưu địa chỉ", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception saving address", e);
                    Toast.makeText(AddAddressActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<AddressResponse> call, Throwable t) {
                showLoading(false);
                Log.e(TAG, "Request failed", t);
                Toast.makeText(AddAddressActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnSaveAddress.setEnabled(!show);
    }
    
    @Override
    public void onBackPressed() {
        finish();
    }
}
