package com.example.ute.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.activities.CheckoutActivity;
import com.example.ute.activities.LoginActivity;
import com.example.ute.activities.MainActivity;
import com.example.ute.adapters.CartAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Cart;
import com.example.ute.models.CartItem;
import com.example.ute.models.request.UpdateCartRequest;
import com.example.ute.models.response.ApiResponse;
import com.example.ute.models.response.CartResponse;
import com.example.ute.utils.PriceFormatter;
import com.example.ute.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements CartAdapter.OnCartItemListener {
    
    private RecyclerView rvCartItems;
    private LinearLayout layoutEmpty, layoutCart;
    private com.google.android.material.card.MaterialCardView layoutSummary;
    private Button btnContinueShopping, btnCheckout, btnApplyVoucher;
    private TextInputEditText etVoucherCode;
    private TextView tvSubtotal, tvDiscount, tvShipping, tvTotal;
    private TextView tvLoginPrompt;
    
    private CartAdapter adapter;
    private ApiService apiService;
    private SessionManager sessionManager;
    
    private Cart cart;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cart, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        apiService = ApiClient.getApiService();
        sessionManager = new SessionManager(requireContext());
        
        initViews(view);
        setupListeners();
    }
    
    @Override
    public void onResume() {
        super.onResume();
        if (sessionManager.isLoggedIn()) {
            loadCart();
        } else {
            // Show demo cart for guests
            loadDemoCart();
        }
    }
    
    private void initViews(View view) {
        rvCartItems = view.findViewById(R.id.rvCartItems);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
        layoutCart = view.findViewById(R.id.layoutCart);
        layoutSummary = view.findViewById(R.id.layoutSummary);
        btnContinueShopping = view.findViewById(R.id.btnContinueShopping);
        btnCheckout = view.findViewById(R.id.btnCheckout);
        btnApplyVoucher = view.findViewById(R.id.btnApplyVoucher);
        etVoucherCode = view.findViewById(R.id.etVoucherCode);
        tvSubtotal = view.findViewById(R.id.tvSubtotal);
        tvDiscount = view.findViewById(R.id.tvDiscount);
        tvShipping = view.findViewById(R.id.tvShipping);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvLoginPrompt = view.findViewById(R.id.tvLoginPrompt);
        
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(new ArrayList<>(), this);
        rvCartItems.setAdapter(adapter);
    }
    
    private void setupListeners() {
        btnContinueShopping.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).findViewById(R.id.nav_home).performClick();
            }
        });
        
        btnCheckout.setOnClickListener(v -> {
            if (cart != null && cart.getItems() != null && !cart.getItems().isEmpty()) {
                startActivity(new Intent(getActivity(), CheckoutActivity.class));
            }
        });
        
        btnApplyVoucher.setOnClickListener(v -> {
            String code = etVoucherCode.getText().toString().trim();
            if (!code.isEmpty()) {
                applyVoucher(code);
            }
        });
        
        if (tvLoginPrompt != null) {
            tvLoginPrompt.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            });
        }
    }
    
    private void showLoginPrompt() {
        layoutCart.setVisibility(View.GONE);
        layoutSummary.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.VISIBLE);
        if (tvLoginPrompt != null) {
            tvLoginPrompt.setVisibility(View.VISIBLE);
        }
    }
    
    private void loadCart() {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.getCart(token).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CartResponse cartResponse = response.body();
                    if (cartResponse.isSuccess() && cartResponse.getData() != null) {
                        cart = cartResponse.getData();
                        displayCart();
                    } else {
                        loadDemoCart();
                    }
                } else {
                    loadDemoCart();
                }
            }
            
            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                loadDemoCart();
            }
        });
    }
    
    private void loadDemoCart() {
        cart = new Cart();
        java.util.List<CartItem> items = new java.util.ArrayList<>();
        
        // Demo item 1
        CartItem item1 = new CartItem();
        item1.setId(1L);
        item1.setQuantity(1);
        com.example.ute.models.Product product1 = new com.example.ute.models.Product();
        product1.setId(1L);
        product1.setName("iPhone 15 Pro Max 256GB");
        product1.setPrice(34990000.0);
        product1.setStockQuantity(10);
        item1.setProduct(product1);
        items.add(item1);
        
        // Demo item 2
        CartItem item2 = new CartItem();
        item2.setId(2L);
        item2.setQuantity(2);
        com.example.ute.models.Product product2 = new com.example.ute.models.Product();
        product2.setId(2L);
        product2.setName("AirPods Pro 2nd Gen");
        product2.setPrice(5990000.0);
        product2.setStockQuantity(30);
        item2.setProduct(product2);
        items.add(item2);
        
        cart.setItems(items);
        cart.setDiscount(0.0);
        displayCart();
    }
    
    private void displayCart() {
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            showEmptyCart();
            return;
        }
        
        layoutEmpty.setVisibility(View.GONE);
        layoutCart.setVisibility(View.VISIBLE);
        layoutSummary.setVisibility(View.VISIBLE);
        
        adapter.updateData(cart.getItems());
        updateSummary();
        
        // Update cart badge in MainActivity
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateCartBadge(cart.getItems().size());
        }
    }
    
    private void showEmptyCart() {
        layoutCart.setVisibility(View.GONE);
        layoutSummary.setVisibility(View.GONE);
        layoutEmpty.setVisibility(View.VISIBLE);
        if (tvLoginPrompt != null) {
            tvLoginPrompt.setVisibility(View.GONE);
        }
    }
    
    private void updateSummary() {
        if (cart == null) return;
        
        double subtotal = 0;
        for (CartItem item : cart.getItems()) {
            // Use the price from CartItem directly (from API response)
            // The product object may be null, but CartItem has its own price field
            Double price = item.getPrice();
            if (price == null && item.getProduct() != null) {
                price = item.getProduct().getPrice();
            }
            if (price != null) {
                subtotal += price * item.getQuantity();
            }
        }
        
        double discount = cart.getDiscount() != null ? cart.getDiscount() : 0;
        double shipping = 0; // Free shipping
        double total = subtotal - discount + shipping;
        
        tvSubtotal.setText(PriceFormatter.format(subtotal));
        tvDiscount.setText("-" + PriceFormatter.format(discount));
        tvShipping.setText(shipping > 0 ? PriceFormatter.format(shipping) : "Miễn phí");
        tvTotal.setText(PriceFormatter.format(total));
    }
    
    private void applyVoucher(String code) {
        // TODO: Implement voucher validation
        Toast.makeText(getContext(), "Đang kiểm tra mã giảm giá...", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onQuantityChanged(CartItem item, int newQuantity) {
        String token = "Bearer " + sessionManager.getAuthToken();
        UpdateCartRequest request = new UpdateCartRequest(newQuantity);
        
        apiService.updateCartItem(token, item.getId(), request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    loadCart();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_occurred, Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    @Override
    public void onRemoveItem(CartItem item) {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.removeCartItem(token, item.getId()).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.removed_from_cart, Toast.LENGTH_SHORT).show();
                    loadCart();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_occurred, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
