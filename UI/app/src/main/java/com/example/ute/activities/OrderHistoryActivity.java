package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.adapters.OrderAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Order;
import com.example.ute.models.response.OrderListResponse;
import com.example.ute.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderHistoryActivity extends AppCompatActivity {

    private RecyclerView rvOrders;
    private OrderAdapter adapter;
    private ApiService apiService;
    private SessionManager sessionManager;
    private List<Order> orders;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        apiService = ApiClient.getApiService();
        sessionManager = new SessionManager(this);
        orders = new ArrayList<>();

        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {
            finish();
            return;
        }

        setupToolbar();
        initViews();
        loadOrders();
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.order_history);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initViews() {
        rvOrders = findViewById(R.id.rvOrders);
        rvOrders.setLayoutManager(new LinearLayoutManager(this));
        
        // Create adapter with click listener
        adapter = new OrderAdapter(orders, order -> {
            // Navigate to order detail
            Intent intent = new Intent(OrderHistoryActivity.this, OrderDetailActivity.class);
            intent.putExtra("order_id", order.getId());
            startActivity(intent);
        });
        rvOrders.setAdapter(adapter);
    }

    private void loadOrders() {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.getOrders(token).enqueue(new Callback<OrderListResponse>() {
            @Override
            public void onResponse(Call<OrderListResponse> call, Response<OrderListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    OrderListResponse orderResponse = response.body();
                    if (orderResponse.isSuccess() && orderResponse.getData() != null) {
                        orders.clear();
                        orders.addAll(orderResponse.getData());
                        adapter.updateData(orders);
                    }
                }
            }

            @Override
            public void onFailure(Call<OrderListResponse> call, Throwable t) {
                // Error loading orders
            }
        });
    }
}
