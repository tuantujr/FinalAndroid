package com.example.ute.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.adapters.CartAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Cart;
import com.example.ute.models.CartItem;
import com.example.ute.models.response.CartResponse;
import com.example.ute.utils.PriceFormatter;
import com.example.ute.utils.SessionManager;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView rvOrderItems;
    private TextInputEditText etFullName, etPhone, etAddress;
    private RadioGroup rgPaymentMethod;
    private TextView tvSubtotal, tvShipping, tvTotal;
    private MaterialButton btnPlaceOrder;
    private ProgressBar progressBar;

    private SessionManager sessionManager;
    private ApiService apiService;
    private List<CartItem> cartItems = new ArrayList<>();
    private double totalAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getClient().create(ApiService.class);

        initViews();
        setupToolbar();
        loadUserInfo();
        loadCartItems();
    }

    private void initViews() {
        rvOrderItems = findViewById(R.id.rvOrderItems);
        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShipping = findViewById(R.id.tvShipping);
        tvTotal = findViewById(R.id.tvTotal);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        progressBar = findViewById(R.id.progressBar);

        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));

        btnPlaceOrder.setOnClickListener(v -> placeOrder());
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(R.string.checkout);
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void loadUserInfo() {
        String fullName = sessionManager.getUserFullName();
        String phone = sessionManager.getUserPhone();

        if (fullName != null) etFullName.setText(fullName);
        if (phone != null) etPhone.setText(phone);
    }

    private void loadCartItems() {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.getCart(token).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse.isSuccess() && cartResponse.getData() != null 
                            && cartResponse.getData().getItems() != null) {
                        cartItems.clear();
                        cartItems.addAll(cartResponse.getData().getItems());
                        setupCartAdapter();
                        updatePriceSummary();
                    } else {
                        loadDemoCartItems();
                    }
                } else {
                    loadDemoCartItems();
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                loadDemoCartItems();
            }
        });
    }

    private void setupCartAdapter() {
        CartAdapter adapter = new CartAdapter(cartItems, new CartAdapter.OnCartItemListener() {
            @Override
            public void onQuantityChanged(CartItem item, int newQuantity) {
                updateCartItemQuantity(item, newQuantity);
            }

            @Override
            public void onRemoveItem(CartItem item) {
                removeCartItem(item);
            }
        });
        rvOrderItems.setAdapter(adapter);
    }

    private void updateCartItemQuantity(CartItem item, int newQuantity) {
        String token = "Bearer " + sessionManager.getAuthToken();
        // Call API to update quantity
        // For now, update locally
        item.setQuantity(newQuantity);
        ((CartAdapter)rvOrderItems.getAdapter()).notifyDataSetChanged();
        updatePriceSummary();
        Toast.makeText(this, "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
    }

    private void removeCartItem(CartItem item) {
        String token = "Bearer " + sessionManager.getAuthToken();
        // Call API to remove item
        // For now, remove locally
        cartItems.remove(item);
        ((CartAdapter)rvOrderItems.getAdapter()).notifyDataSetChanged();
        updatePriceSummary();
        Toast.makeText(this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
    }
    
    private void loadDemoCartItems() {
        cartItems.clear();
        
        CartItem item1 = new CartItem();
        item1.setId(1L);
        item1.setQuantity(1);
        item1.setPrice(34990000.0);
        cartItems.add(item1);
        
        CartItem item2 = new CartItem();
        item2.setId(2L);
        item2.setQuantity(2);
        item2.setPrice(5990000.0);
        cartItems.add(item2);
        
        updatePriceSummary();
    }

    private void updatePriceSummary() {
        double subtotal = 0;
        for (CartItem item : cartItems) {
            subtotal += item.getPrice() * item.getQuantity();
        }

        double shipping = subtotal > 500000 ? 0 : 30000;
        totalAmount = subtotal + shipping;

        tvSubtotal.setText(PriceFormatter.format(subtotal));
        tvShipping.setText(shipping == 0 ? getString(R.string.free_shipping) : PriceFormatter.format(shipping));
        tvTotal.setText(PriceFormatter.format(totalAmount));
    }

    private void placeOrder() {
        String fullName = etFullName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String address = etAddress.getText().toString().trim();

        if (fullName.isEmpty() || phone.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, R.string.field_required, Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO: Implement place order API call
        Toast.makeText(this, R.string.order_success, Toast.LENGTH_SHORT).show();
        finish();
    }
}
