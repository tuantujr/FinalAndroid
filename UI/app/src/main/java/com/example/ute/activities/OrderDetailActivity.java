package com.example.ute.activities;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.utils.SessionManager;
import com.example.ute.models.Order;
import com.example.ute.models.response.OrderResponse;
import com.example.ute.adapters.OrderDetailItemsAdapter;
import com.example.ute.utils.PriceFormatter;

import retrofit2.Callback;
import retrofit2.Response;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class OrderDetailActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private ApiService apiService;
    private Long orderId;
    
    private TextView tvOrderCode, tvOrderDate, tvOrderStatus, tvPaymentStatus;
    private TextView tvShippingName, tvShippingPhone, tvShippingAddress;
    private RecyclerView rvOrderItems;
    private OrderDetailItemsAdapter itemsAdapter;
    private TextView tvSubtotal, tvShippingFee, tvTotalAmount;
    private ImageView btnBack;
    private LinearLayout loadingLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        
        sessionManager = new SessionManager(this);
        apiService = ApiClient.getApiService();
        
        orderId = getIntent().getLongExtra("order_id", -1);
        
        if (orderId == -1) {
            finish();
            return;
        }
        
        initViews();
        loadOrderDetail();
    }
    
    private void initViews() {
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> finish());
        
        loadingLayout = findViewById(R.id.loadingLayout);
        
        tvOrderCode = findViewById(R.id.tvOrderCode);
        tvOrderDate = findViewById(R.id.tvOrderDate);
        tvOrderStatus = findViewById(R.id.tvOrderStatus);
        tvPaymentStatus = findViewById(R.id.tvPaymentStatus);
        
        tvShippingName = findViewById(R.id.tvShippingName);
        tvShippingPhone = findViewById(R.id.tvShippingPhone);
        tvShippingAddress = findViewById(R.id.tvShippingAddress);
        
        rvOrderItems = findViewById(R.id.rvOrderItems);
        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));
        itemsAdapter = new OrderDetailItemsAdapter();
        rvOrderItems.setAdapter(itemsAdapter);
        
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShippingFee = findViewById(R.id.tvShippingFee);
        tvTotalAmount = findViewById(R.id.tvTotalAmount);
    }
    
    private void loadOrderDetail() {
        loadingLayout.setVisibility(android.view.View.VISIBLE);
        
        String token = "Bearer " + sessionManager.getAuthToken();
        apiService.getOrderDetail(token, orderId).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(retrofit2.Call<OrderResponse> call, Response<OrderResponse> response) {
                loadingLayout.setVisibility(android.view.View.GONE);
                
                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Order order = response.body().getData();
                    displayOrderDetail(order);
                } else {
                    // Handle error silently
                }
            }
            
            @Override
            public void onFailure(retrofit2.Call<OrderResponse> call, Throwable t) {
                loadingLayout.setVisibility(android.view.View.GONE);
                // Handle error silently
            }
        });
    }
    
    private void displayOrderDetail(Order order) {
        // Order Info
        tvOrderCode.setText("Mã đơn hàng: " + order.getOrderCode());
        
        if (order.getCreatedAt() != null) {
            try {
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                String formattedDate = outputFormat.format(inputFormat.parse(order.getCreatedAt()));
                tvOrderDate.setText("Ngày đặt: " + formattedDate);
            } catch (Exception e) {
                tvOrderDate.setText("Ngày đặt: " + order.getCreatedAt());
            }
        }
        
        String statusText = getStatusText(order.getStatus());
        tvOrderStatus.setText(statusText);
        tvOrderStatus.setBackgroundColor(getColor(R.color.background_primary));  // White background
        tvOrderStatus.setTextColor(getColor(R.color.text_primary));  // Black text
        
        tvPaymentStatus.setText("Thanh toán khi nhận hàng");
        
        // Shipping Info
        String address = "";
        if (order.getShippingInfo() != null) {
            Order.ShippingInfo info = order.getShippingInfo();
            tvShippingName.setText(info.getRecipientName() != null ? info.getRecipientName() : "");
            tvShippingPhone.setText(info.getPhoneNumber() != null ? info.getPhoneNumber() : "");
            
            if (info.getStreetAddress() != null) {
                address = info.getStreetAddress();
            }
            if (info.getCity() != null && !info.getCity().isEmpty()) {
                address += ", " + info.getCity();
            }
            if (info.getWard() != null && !info.getWard().isEmpty()) {
                address += ", " + info.getWard();
            }
            tvShippingAddress.setText(address);
        }
        
        // Order Items
        if (order.getItems() != null && !order.getItems().isEmpty()) {
            itemsAdapter.updateData(order.getItems());
            Toast.makeText(this, "Items: " + order.getItems().size(), Toast.LENGTH_SHORT).show();
        }
        
        // Totals
        Double subtotal = order.getTotalAmount() != null ? order.getTotalAmount() : 0;
        Double shippingFee = order.getShippingFee() != null ? order.getShippingFee() : 0;
        Double total = subtotal + shippingFee;
        
        tvSubtotal.setText("Tổng tiền hàng: " + PriceFormatter.format(subtotal));
        tvShippingFee.setText("Phí vận chuyển: " + PriceFormatter.format(shippingFee));
        tvTotalAmount.setText("Tổng thanh toán: " + PriceFormatter.format(total));
    }
    
    private String getStatusText(String status) {
        if (status == null) return "Không xác định";
        
        switch (status.toUpperCase()) {
            case "PENDING":
                return "Chờ xác nhận";
            case "PROCESSING":
                return "Đang xử lý";
            case "SHIPPED":
                return "Đã gửi hàng";
            case "DELIVERED":
                return "Đã giao";
            case "CANCELLED":
                return "Đã hủy";
            default:
                return status;
        }
    }
    
    private int getStatusColor(String status) {
        if (status == null) return getColor(R.color.text_secondary);
        
        switch (status.toUpperCase()) {
            case "PENDING":
                return getColor(R.color.color_warning);  // Yellow - waiting
            case "PROCESSING":
                return getColor(R.color.info);  // Blue - processing
            case "SHIPPED":
                return getColor(R.color.color_primary);  // Orange - in transit
            case "DELIVERED":
                return getColor(R.color.color_success);  // Green - done
            case "CANCELLED":
                return getColor(R.color.error);  // Red - cancelled
            default:
                return getColor(R.color.text_secondary);
        }
    }
}
