package com.example.ute.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.adapters.VoucherAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.response.VoucherListResponse;
import com.example.ute.models.response.VoucherResponse;
import com.example.ute.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;

public class VouchersFragment extends Fragment {
    
    private static final String TAG = "VouchersFragment";
    
    private RecyclerView rvVouchers;
    private VoucherAdapter adapter;
    private ApiService apiService;
    private ProgressBar progressBar;
    private TextView tvEmptyState;
    private SessionManager sessionManager;
    
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_vouchers, container, false);
    }
    
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        apiService = ApiClient.getApiService();
        sessionManager = new SessionManager(getContext());
        
        initViews(view);
        loadVouchers();
    }
    
    private void initViews(View view) {
        rvVouchers = view.findViewById(R.id.rvVouchers);
        progressBar = view.findViewById(R.id.progressBar);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
        
        rvVouchers.setLayoutManager(new LinearLayoutManager(getContext()));
        
        adapter = new VoucherAdapter(new ArrayList<>(), voucher -> {
            // Handle voucher click - show voucher details
            showVoucherDetails(voucher);
        });
        
        rvVouchers.setAdapter(adapter);
    }
    
    private void loadVouchers() {
        progressBar.setVisibility(View.VISIBLE);
        rvVouchers.setVisibility(View.GONE);
        tvEmptyState.setVisibility(View.GONE);
        
        Log.d(TAG, "=== LOADING VOUCHERS FROM API ===");
        Log.d(TAG, "Endpoint: api/v1/vouchers?page=1&limit=50&status=ACTIVE");
        
        // Get authentication token (required for voucher access)
        String authToken = sessionManager.getAuthToken();
        if (authToken == null || authToken.isEmpty()) {
            Log.w(TAG, "⚠️ No auth token found, cannot load vouchers");
            showEmptyState();
            tvEmptyState.setText("Vui lòng đăng nhập để xem vouchers");
            return;
        }
        
        Log.d(TAG, "Sending authorization token");
        apiService.getVouchersWithAuth("Bearer " + authToken, 1, 50, "ACTIVE").enqueue(new Callback<VoucherListResponse>() {
            @Override
            public void onResponse(Call<VoucherListResponse> call, Response<VoucherListResponse> response) {
                handleVoucherResponse(response);
            }
            
            @Override
            public void onFailure(Call<VoucherListResponse> call, Throwable t) {
                handleVoucherFailure(t);
            }
        });
    }
    
    private void handleVoucherResponse(Response<VoucherListResponse> response) {
        progressBar.setVisibility(View.GONE);
        
        Log.d(TAG, "API Response received - Status Code: " + response.code());
        
        if (response.isSuccessful() && response.body() != null) {
            VoucherListResponse voucherResponse = response.body();
            int dataSize = voucherResponse.getData() != null ? voucherResponse.getData().size() : 0;
            Log.d(TAG, "API returned " + dataSize + " vouchers");
            
            if (voucherResponse.isSuccess() && voucherResponse.getData() != null && !voucherResponse.getData().isEmpty()) {
                Log.d(TAG, "✅ SUCCESS: Displaying " + voucherResponse.getData().size() + " vouchers from DATABASE");
                adapter.updateData(voucherResponse.getData());
                rvVouchers.setVisibility(View.VISIBLE);
            } else {
                Log.d(TAG, "⚠️ API returned empty data");
                showEmptyState();
            }
        } else {
            Log.e(TAG, "❌ API Response failed - Status Code: " + response.code());
            try {
                String errorBody = response.errorBody() != null ? response.errorBody().string() : "Unknown error";
                Log.e(TAG, "Error body: " + errorBody);
            } catch (Exception e) {
                Log.e(TAG, "Could not read error body", e);
            }
            showEmptyState();
        }
    }
    
    private void handleVoucherFailure(Throwable t) {
        progressBar.setVisibility(View.GONE);
        Log.e(TAG, "❌ API call FAILED: " + t.getMessage());
        t.printStackTrace();
        showEmptyState();
    }
    
    private void showEmptyState() {
        tvEmptyState.setVisibility(View.VISIBLE);
        rvVouchers.setVisibility(View.GONE);
        tvEmptyState.setText("Không có voucher khả dụng");
    }
    
    private void showVoucherDetails(VoucherResponse voucher) {
        String message = "Mã: " + voucher.getCode() + 
                        "\nGiảm: " + formatDiscount(voucher) +
                        "\nHết hạn: " + voucher.getEndDate();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
    
    private String formatDiscount(VoucherResponse voucher) {
        if (voucher.getDiscountType() == null || voucher.getDiscountValue() == null) {
            return "Khuyến mãi";
        }
        
        if ("PERCENTAGE".equalsIgnoreCase(voucher.getDiscountType())) {
            return "Giảm " + voucher.getDiscountValue() + "%";
        } else if ("FIXED_AMOUNT".equalsIgnoreCase(voucher.getDiscountType())) {
            return "Giảm " + String.format("%,d", voucher.getDiscountValue().longValue()) + "đ";
        }
        
        return "Khuyến mãi";
    }
}
