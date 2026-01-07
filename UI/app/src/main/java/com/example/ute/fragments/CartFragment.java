package com.example.ute.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.example.ute.models.response.CartDataResponse;
import com.example.ute.utils.PriceFormatter;
import com.example.ute.utils.SessionManager;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {
    
    private RecyclerView rvCartItems;
    private LinearLayout layoutEmpty, layoutCart;
    private com.google.android.material.card.MaterialCardView layoutSummary;
    private Button btnContinueShopping, btnCheckout;
    private TextView tvSubtotal, tvDiscount, tvShipping, tvTotal;
    private TextView tvLoginPrompt;
    
    private CartAdapter adapter;
    private ApiService apiService;
    private SessionManager sessionManager;
    
    private Cart cart;
    private java.util.List<CartDataResponse.CartItemData> cartItems;

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
            // Show empty cart for guests
            showLoginPrompt();
        }
    }
    
    private void initViews(View view) {
        rvCartItems = view.findViewById(R.id.rvCartItems);
        layoutEmpty = view.findViewById(R.id.layoutEmpty);
        layoutCart = view.findViewById(R.id.layoutCart);
        layoutSummary = view.findViewById(R.id.layoutSummary);
        btnContinueShopping = view.findViewById(R.id.btnContinueShopping);
        btnCheckout = view.findViewById(R.id.btnCheckout);
        tvSubtotal = view.findViewById(R.id.tvSubtotal);
        tvDiscount = view.findViewById(R.id.tvDiscount);
        tvShipping = view.findViewById(R.id.tvShipping);
        tvTotal = view.findViewById(R.id.tvTotal);
        tvLoginPrompt = view.findViewById(R.id.tvLoginPrompt);
        
        rvCartItems.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CartAdapter(new java.util.ArrayList<>(), new CartAdapter.OnCartItemListener() {
            @Override
            public void onQuantityChanged(CartDataResponse.CartItemData item, int newQuantity) {
                onQuantityChangedNew(item, newQuantity);
            }

            @Override
            public void onRemoveItem(CartDataResponse.CartItemData item) {
                onRemoveItemNew(item);
            }
        });
        rvCartItems.setAdapter(adapter);
    }
    
    private void setupListeners() {
        btnContinueShopping.setOnClickListener(v -> {
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).findViewById(R.id.nav_home).performClick();
            }
        });
        
        btnCheckout.setOnClickListener(v -> {
            if (cartItems != null && !cartItems.isEmpty()) {
                startActivity(new Intent(getActivity(), CheckoutActivity.class));
            } else if (sessionManager.isLoggedIn()) {
                Toast.makeText(getContext(), "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
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
                        Object data = cartResponse.getData();
                        
                        // Convert Object data to CartDataResponse using Gson
                        com.google.gson.Gson gson = new com.google.gson.Gson();
                        
                        // LOG FULL JSON TO SEE WHAT BACKEND SENDS
                        String jsonData = gson.toJson(data);
                        Log.d("CartFragment", "Raw cart JSON from backend:\n" + jsonData);
                        
                        CartDataResponse cartDataResponse = gson.fromJson(
                            gson.toJsonTree(data), 
                            CartDataResponse.class
                        );
                        
                        if (cartDataResponse != null && cartDataResponse.getItems() != null) {
                            cartItems = cartDataResponse.getItems();
                            Log.d("CartFragment", "Loaded " + cartItems.size() + " cart items");
                            for (CartDataResponse.CartItemData item : cartItems) {
                                Log.d("CartFragment", "Cart item - cartItemId=" + item.getCartItemId() + 
                                    ", productId=" + item.getProductId() + 
                                    ", name=" + item.getProductName());
                            }
                            displayCartNew();
                        } else {
                            showEmptyCart();
                        }
                    } else {
                        showEmptyCart();
                    }
                } else {
                    showEmptyCart();
                }
            }
            
            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                showEmptyCart();
            }
        });
    }
    
    private void displayCart() {
        // Legacy method - now using displayCartNew with CartDataResponse format
        if (cartItems == null || cartItems.isEmpty()) {
            showEmptyCart();
            return;
        }
        displayCartNew();
    }

    private void displayCartNew() {
        if (cartItems == null || cartItems.isEmpty()) {
            showEmptyCart();
            return;
        }
        
        layoutEmpty.setVisibility(View.GONE);
        layoutCart.setVisibility(View.VISIBLE);
        layoutSummary.setVisibility(View.VISIBLE);
        
        adapter.updateData(cartItems);
        updateSummaryNew();
        
        // Update cart badge in MainActivity
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).updateCartBadge(cartItems.size());
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

    private void updateSummaryNew() {
        if (cartItems == null || cartItems.isEmpty()) return;
        
        double subtotal = 0;
        for (CartDataResponse.CartItemData item : cartItems) {
            Double price = item.getPrice();
            if (price != null) {
                subtotal += price * item.getQuantity();
            }
        }
        
        double discount = 0; // TODO: Get from API response if available
        double shipping = 0; // Free shipping
        double total = subtotal - discount + shipping;
        
        tvSubtotal.setText(PriceFormatter.format(subtotal));
        tvDiscount.setText("-" + PriceFormatter.format(discount));
        tvShipping.setText(shipping > 0 ? PriceFormatter.format(shipping) : "Miễn phí");
        tvTotal.setText(PriceFormatter.format(total));
    }
    

    
    public void onQuantityChangedLegacy(CartItem item, int newQuantity) {
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

    public void onQuantityChangedNew(CartDataResponse.CartItemData item, int newQuantity) {
        String token = "Bearer " + sessionManager.getAuthToken();
        UpdateCartRequest request = new UpdateCartRequest(newQuantity);
        Long productId = item.getProductId();
        
        Log.d("CartFragment", "onQuantityChangedNew: productId=" + productId + ", newQuantity=" + newQuantity);
        
        if (productId == null) {
            Log.e("CartFragment", "productId is null! Item details: cartItemId=" + item.getCartItemId() + ", productName=" + item.getProductName());
            Toast.makeText(getContext(), "Lỗi: Không thể cập nhật sản phẩm (thiếu ID)", Toast.LENGTH_SHORT).show();
            return;
        }
        
        apiService.updateCartItem(token, productId, request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("CartFragment", "updateCartItem response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        item.setQuantity(newQuantity);
                        updateSummaryNew();
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getContext(), "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("CartFragment", "API returned error: " + response.body().getMessage());
                        Toast.makeText(getContext(), "Lỗi: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("CartFragment", "API call failed with code: " + response.code() + ", message: " + response.message());
                    Toast.makeText(getContext(), "Lỗi cập nhật: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("CartFragment", "onQuantityChangedNew failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    public void onRemoveItemLegacy(CartItem item) {
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

    public void onRemoveItemNew(CartDataResponse.CartItemData item) {
        String token = "Bearer " + sessionManager.getAuthToken();
        Long productId = item.getProductId();
        
        Log.d("CartFragment", "onRemoveItemNew: productId=" + productId + ", productName=" + item.getProductName());
        
        if (productId == null) {
            Log.e("CartFragment", "productId is null! Item details: cartItemId=" + item.getCartItemId() + ", productName=" + item.getProductName());
            Toast.makeText(getContext(), "Lỗi: Không thể xóa sản phẩm (thiếu ID)", Toast.LENGTH_SHORT).show();
            return;
        }
        
        apiService.removeCartItem(token, productId).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                Log.d("CartFragment", "removeCartItem response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSuccess()) {
                        Toast.makeText(getContext(), "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        loadCart();
                    } else {
                        Log.e("CartFragment", "API returned error: " + response.body().getMessage());
                        Toast.makeText(getContext(), "Lỗi: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e("CartFragment", "API call failed with code: " + response.code() + ", message: " + response.message());
                    Toast.makeText(getContext(), "Lỗi xóa: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Log.e("CartFragment", "onRemoveItemNew failed: " + t.getMessage(), t);
                Toast.makeText(getContext(), "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
