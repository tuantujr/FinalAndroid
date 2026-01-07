package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ute.R;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Province;
import com.example.ute.models.Ward;
import com.example.ute.models.request.AddressRequest;
import com.example.ute.models.response.AddressResponse;
import com.example.ute.models.response.ProvinceListResponse;
import com.example.ute.models.response.WardListResponse;
import com.example.ute.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {
    
    private static final String TAG = "AddAddressActivity";
    
    private TextInputLayout tilRecipientName, tilPhoneNumber, tilStreetAddress;
    private TextInputEditText etRecipientName, etPhoneNumber, etStreetAddress;
    private Spinner spProvince, spWard;
    private CheckBox cbIsDefault;
    private Button btnSaveAddress;
    private ImageButton btnBack;
    private ProgressBar progressBar;
    
    private SessionManager sessionManager;
    private ApiService apiService;
    private Long addressId;
    
    private List<Province> provinceList = new ArrayList<>();
    private List<Ward> wardList = new ArrayList<>();
    private Province selectedProvince;
    private Ward selectedWard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        
        sessionManager = new SessionManager(this);
        apiService = ApiClient.getApiService();
        
        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, R.string.please_login, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return;
        }
        
        // Check if editing existing address
        addressId = getIntent().getLongExtra("address_id", -1);
        
        initViews();
        setupListeners();
        loadProvinces();
    }
    
    private void initViews() {
        tilRecipientName = findViewById(R.id.tilRecipientName);
        tilPhoneNumber = findViewById(R.id.tilPhoneNumber);
        tilStreetAddress = findViewById(R.id.tilStreetAddress);
        
        etRecipientName = findViewById(R.id.etRecipientName);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        etStreetAddress = findViewById(R.id.etStreetAddress);
        
        spProvince = findViewById(R.id.spProvince);
        spWard = findViewById(R.id.spWard);
        
        cbIsDefault = findViewById(R.id.cbIsDefault);
        btnSaveAddress = findViewById(R.id.btnSaveAddress);
        btnBack = findViewById(R.id.btnBack);
        progressBar = findViewById(R.id.progressBar);
    }
    
    private void setupListeners() {
        btnSaveAddress.setOnClickListener(v -> attemptSaveAddress());
        btnBack.setOnClickListener(v -> finish());
        
        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedProvince = provinceList.get(position - 1);
                    loadWards(selectedProvince.getCode());
                } else {
                    selectedProvince = null;
                    wardList.clear();
                    updateWardSpinner();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedProvince = null;
            }
        });
        
        spWard.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedWard = wardList.get(position - 1);
                } else {
                    selectedWard = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedWard = null;
            }
        });
    }
    
    private void loadProvinces() {
        apiService.getProvinces().enqueue(new Callback<ProvinceListResponse>() {
            @Override
            public void onResponse(Call<ProvinceListResponse> call, Response<ProvinceListResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        ProvinceListResponse res = response.body();
                        if (res.isSuccess() && res.getData() != null) {
                            provinceList = res.getData();
                            updateProvinceSpinner();
                        } else {
                            Log.w(TAG, "Province list response not successful");
                        }
                    } else {
                        Log.w(TAG, "Response not successful: " + response.code());
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception loading provinces", e);
                }
            }

            @Override
            public void onFailure(Call<ProvinceListResponse> call, Throwable t) {
                Log.e(TAG, "Request failed", t);
                Toast.makeText(AddAddressActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void loadWards(String provinceCode) {
        apiService.getWardsByProvince(provinceCode).enqueue(new Callback<WardListResponse>() {
            @Override
            public void onResponse(Call<WardListResponse> call, Response<WardListResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        WardListResponse res = response.body();
                        if (res.isSuccess() && res.getData() != null) {
                            wardList = res.getData();
                            updateWardSpinner();
                        } else {
                            Log.w(TAG, "Ward list response not successful");
                            wardList.clear();
                            updateWardSpinner();
                        }
                    } else {
                        Log.w(TAG, "Response not successful: " + response.code());
                        wardList.clear();
                        updateWardSpinner();
                    }
                } catch (Exception e) {
                    Log.e(TAG, "Exception loading wards", e);
                    wardList.clear();
                    updateWardSpinner();
                }
            }

            @Override
            public void onFailure(Call<WardListResponse> call, Throwable t) {
                Log.e(TAG, "Request failed", t);
                wardList.clear();
                updateWardSpinner();
            }
        });
    }
    
    private void updateProvinceSpinner() {
        List<String> provinceNames = new ArrayList<>();
        provinceNames.add("-- Chọn Tỉnh/Thành phố --");
        for (Province p : provinceList) {
            provinceNames.add(p.getName());
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                provinceNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvince.setAdapter(adapter);
    }
    
    private void updateWardSpinner() {
        List<String> wardNames = new ArrayList<>();
        wardNames.add("-- Chọn Phường/Xã --");
        for (Ward w : wardList) {
            wardNames.add(w.getName());
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                wardNames
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spWard.setAdapter(adapter);
    }
    
    private void attemptSaveAddress() {
        // Reset errors
        tilRecipientName.setError(null);
        tilPhoneNumber.setError(null);
        tilStreetAddress.setError(null);
        
        String recipientName = etRecipientName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();
        String streetAddress = etStreetAddress.getText().toString().trim();
        Boolean isDefault = cbIsDefault.isChecked();
        
        boolean cancel = false;
        View focusView = null;
        
        // Validate province
        if (selectedProvince == null) {
            Toast.makeText(this, "Vui lòng chọn Tỉnh/Thành phố", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Validate ward
        if (selectedWard == null) {
            Toast.makeText(this, "Vui lòng chọn Phường/Xã", Toast.LENGTH_SHORT).show();
            return;
        }
        
        // Validate street address
        if (TextUtils.isEmpty(streetAddress)) {
            tilStreetAddress.setError(getString(R.string.field_required));
            focusView = etStreetAddress;
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
            performSaveAddress(recipientName, phoneNumber, streetAddress, isDefault);
        }
    }
    
    private void performSaveAddress(String recipientName, String phoneNumber, String streetAddress, Boolean isDefault) {
        showLoading(true);
        String token = "Bearer " + sessionManager.getAuthToken();
        AddressRequest request = new AddressRequest(
                recipientName,
                phoneNumber,
                streetAddress,
                selectedProvince.getName(),
                selectedProvince.getCode(),
                selectedWard.getName(),
                selectedWard.getCode(),
                isDefault
        );
        
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
