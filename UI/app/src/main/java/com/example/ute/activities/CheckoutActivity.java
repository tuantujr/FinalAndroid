package com.example.ute.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Spinner;
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
import com.example.ute.models.Address;
import com.example.ute.models.Cart;
import com.example.ute.models.CartItem;
import com.example.ute.models.Order;
import com.example.ute.models.OrderItem;
import com.example.ute.models.response.AddressListResponse;
import com.example.ute.models.response.CartResponse;
import com.example.ute.models.response.CartDataResponse;
import com.example.ute.models.response.OrderResponse;
import com.example.ute.models.response.VoucherValidationResponse;
import com.example.ute.models.request.CheckoutRequest;
import com.example.ute.models.request.UpdateCartRequest;

import com.example.ute.models.request.ValidateVoucherRequest;
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
    
    private static final String TAG = "CheckoutActivity";

    private RecyclerView rvOrderItems;
    private TextInputEditText etFullName, etPhone, etVoucherCode;
    private Spinner spinnerAddress;
    private TextView tvSelectedAddress, tvSubtotal, tvShipping, tvDiscount, tvTotal;
    private MaterialButton btnPlaceOrder, btnApplyVoucher, btnAddNewAddress;
    private RadioGroup rgPaymentMethod;
    private ProgressBar progressBar;

    private SessionManager sessionManager;
    private ApiService apiService;
    private List<CartItem> cartItems = new ArrayList<>();
    private List<CartDataResponse.CartItemData> cartItemDataList = new ArrayList<>();
    private List<Address> addresses = new ArrayList<>();
    private Address selectedAddress = null;
    private double totalAmount = 0;
    private double discountAmount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        sessionManager = new SessionManager(this);
        apiService = ApiClient.getApiService(sessionManager.getAuthToken());

        // Check if user is logged in
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, R.string.login_to_continue, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        initViews();
        setupToolbar();
        loadUserInfo();
        loadCartItems();
    }

    private void initViews() {
        rvOrderItems = findViewById(R.id.rvOrderItems);
        etFullName = findViewById(R.id.etFullName);
        etPhone = findViewById(R.id.etPhone);
        spinnerAddress = findViewById(R.id.spinnerAddress);
        tvSelectedAddress = findViewById(R.id.tvSelectedAddress);
        etVoucherCode = findViewById(R.id.etVoucherCode);
        rgPaymentMethod = findViewById(R.id.rgPaymentMethod);
        tvSubtotal = findViewById(R.id.tvSubtotal);
        tvShipping = findViewById(R.id.tvShipping);
        tvDiscount = findViewById(R.id.tvDiscount);
        tvTotal = findViewById(R.id.tvTotal);
        btnPlaceOrder = findViewById(R.id.btnPlaceOrder);
        btnApplyVoucher = findViewById(R.id.btnApplyVoucher);
        btnAddNewAddress = findViewById(R.id.btnAddNewAddress);
        progressBar = findViewById(R.id.progressBar);

        rvOrderItems.setLayoutManager(new LinearLayoutManager(this));

        btnPlaceOrder.setOnClickListener(v -> placeOrder());
        btnApplyVoucher.setOnClickListener(v -> applyVoucher());
        btnAddNewAddress.setOnClickListener(v -> addNewAddress());
        
        loadAddresses();
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

    private void loadAddresses() {
        String token = "Bearer " + sessionManager.getAuthToken();
        Log.d(TAG, "loadAddresses: Loading addresses from API");
        
        apiService.getAddresses(token).enqueue(new Callback<AddressListResponse>() {
            @Override
            public void onResponse(Call<AddressListResponse> call, Response<AddressListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AddressListResponse addressResponse = response.body();
                    if (addressResponse.isSuccess() && addressResponse.getData() != null) {
                        addresses.clear();
                        addresses.addAll(addressResponse.getData());
                        setupAddressSpinner();
                        
                        // Select default address
                        for (int i = 0; i < addresses.size(); i++) {
                            if (addresses.get(i).getIsDefault() != null && addresses.get(i).getIsDefault()) {
                                spinnerAddress.setSelection(i);
                                onAddressSelected(i);
                                break;
                            }
                        }
                    }
                } else {
                    Log.w(TAG, "loadAddresses: Failed to load addresses");
                    Toast.makeText(CheckoutActivity.this, "Không thể tải địa chỉ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AddressListResponse> call, Throwable t) {
                Log.e(TAG, "loadAddresses: Network error", t);
                Toast.makeText(CheckoutActivity.this, "Lỗi mạng: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAddressSpinner() {
        List<String> addressLabels = new ArrayList<>();
        for (Address address : addresses) {
            String label = address.getRecipientName() + " - " + address.getStreetAddress();
            addressLabels.add(label);
        }
        
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                addressLabels
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddress.setAdapter(adapter);
        
        spinnerAddress.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                onAddressSelected(position);
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });
    }

    private void onAddressSelected(int position) {
        if (position >= 0 && position < addresses.size()) {
            selectedAddress = addresses.get(position);
            displaySelectedAddress();
        }
    }

    private void displaySelectedAddress() {
        if (selectedAddress != null) {
            String addressText = String.format(
                    "%s\n%s\n%s, %s, %s\nĐT: %s",
                    selectedAddress.getRecipientName(),
                    selectedAddress.getStreetAddress(),
                    selectedAddress.getWard(),
                    selectedAddress.getProvince(),
                    "Việt Nam",
                    selectedAddress.getPhoneNumber()
            );
            tvSelectedAddress.setText(addressText);
            tvSelectedAddress.setVisibility(View.VISIBLE);
        }
    }

    private void addNewAddress() {
        // Open AddressActivity to add new address
        startActivity(new android.content.Intent(this, AddressActivity.class));
        // Reload addresses when returning
        loadAddresses();
    }

    private void loadCartItems() {
        String token = "Bearer " + sessionManager.getAuthToken();
        Log.d(TAG, "loadCartItems: Loading cart from API with token: " + token.substring(0, 20) + "...");
        
        apiService.getCart(token).enqueue(new Callback<CartResponse>() {
            @Override
            public void onResponse(Call<CartResponse> call, Response<CartResponse> response) {
                Log.d(TAG, "onResponse: Code=" + response.code() + ", Success=" + response.isSuccessful());
                
                if (response.isSuccessful() && response.body() != null) {
                    CartResponse cartResponse = response.body();
                    Log.d(TAG, "onResponse: CartResponse success=" + cartResponse.isSuccess());
                    
                    if (cartResponse.isSuccess() && cartResponse.getData() != null) {
                        Object data = cartResponse.getData();
                        Log.d(TAG, "onResponse: Data type = " + data.getClass().getSimpleName());
                        
                        // Convert Object data to CartDataResponse using Gson
                        com.google.gson.Gson gson = new com.google.gson.Gson();
                        CartDataResponse cartDataResponse = gson.fromJson(
                            gson.toJsonTree(data), 
                            CartDataResponse.class
                        );
                        
                        if (cartDataResponse != null && cartDataResponse.getItems() != null) {
                            Log.d(TAG, "onResponse: Got " + cartDataResponse.getItems().size() + " items from API");
                            Log.d(TAG, "onResponse: Total price from API: " + cartDataResponse.getTotalPrice());
                            
                            for (CartDataResponse.CartItemData itemData : cartDataResponse.getItems()) {
                                Log.d(TAG, "Item:");
                                Log.d(TAG, "  - CartItemId: " + itemData.getCartItemId());
                                Log.d(TAG, "  - ProductId: " + itemData.getProductId());
                                Log.d(TAG, "  - ProductName: " + itemData.getProductName());
                                Log.d(TAG, "  - ThumbnailUrl: " + itemData.getThumbnailUrl());
                                Log.d(TAG, "  - Price: " + itemData.getPrice());
                                Log.d(TAG, "  - Quantity: " + itemData.getQuantity());
                                Log.d(TAG, "  - LineTotal: " + itemData.getLineTotal());
                            }
                            
                            // Store cart data
                            totalAmount = cartDataResponse.getTotalPrice() != null ? 
                                    cartDataResponse.getTotalPrice().doubleValue() : 0;
                            
                            // Setup adapter with new format
                            setupCartAdapterNewFormat(cartDataResponse.getItems());
                            updatePriceSummary();
                        } else {
                            Log.w(TAG, "onResponse: CartDataResponse is null or has no items");
                            Toast.makeText(CheckoutActivity.this, "Lỗi tải giỏ hàng: Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    } else {
                        Log.w(TAG, "onResponse: CartResponse not successful or data is null");
                        Toast.makeText(CheckoutActivity.this, "Lỗi tải giỏ hàng: Dữ liệu không hợp lệ", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility(View.GONE);
                    }
                } else {
                    Log.w(TAG, "onResponse: Response not successful or body is null");
                    try {
                        if (response.errorBody() != null) {
                            Log.e(TAG, "Error: " + response.errorBody().string());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(CheckoutActivity.this, "Lỗi: " + response.code() + " - " + response.message(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CartResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage(), t);
                Toast.makeText(CheckoutActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void setupCartAdapter() {
        // Legacy method - no longer used. Use setupCartAdapterNewFormat instead.
    }

    private void setupCartAdapterNewFormat(List<CartDataResponse.CartItemData> items) {
        // Store items for placeOrder() to check if cart is empty
        cartItemDataList = items;
        
        CartAdapter adapter = new CartAdapter(items, new CartAdapter.OnCartItemListener() {
            @Override
            public void onQuantityChanged(CartDataResponse.CartItemData item, int newQuantity) {
                updateCartItemQuantityNew(item, newQuantity);
            }

            @Override
            public void onRemoveItem(CartDataResponse.CartItemData item) {
                removeCartItemNew(item);
            }
        });
        rvOrderItems.setAdapter(adapter);
    }

    private void updateCartItemQuantity(CartItem item, int newQuantity) {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        // Determine which ID to use (in order of preference)
        Long itemId = item.getId();
        if (itemId == null && item.getProductId() != null) {
            itemId = item.getProductId();
            Log.w("CheckoutActivity", "CartItem ID is null, using productId=" + itemId);
        }
        if (itemId == null && item.getProduct() != null && item.getProduct().getId() != null) {
            itemId = item.getProduct().getId();
            Log.w("CheckoutActivity", "Both CartItem ID and productId are null, using product.getId()=" + itemId);
        }
        
        if (itemId == null) {
            Log.e("CheckoutActivity", "Cannot update quantity - all ID sources are null!");
            Toast.makeText(CheckoutActivity.this, "Lỗi: Không thể cập nhật sản phẩm (thiếu ID)", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Log.d("CheckoutActivity", "updateCartItemQuantity: Using itemId=" + itemId + ", quantity=" + newQuantity);
        
        UpdateCartRequest request = new UpdateCartRequest(newQuantity);
        
        apiService.updateCartItem(token, itemId, request).enqueue(new Callback<com.example.ute.models.response.ApiResponse>() {
            @Override
            public void onResponse(Call<com.example.ute.models.response.ApiResponse> call, Response<com.example.ute.models.response.ApiResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().isSuccess()) {
                            item.setQuantity(newQuantity);
                            if (rvOrderItems.getAdapter() != null) {
                                ((CartAdapter)rvOrderItems.getAdapter()).notifyDataSetChanged();
                            }
                            updatePriceSummary();
                            Toast.makeText(CheckoutActivity.this, "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = response.body().getMessage();
                            Toast.makeText(CheckoutActivity.this, message != null ? message : "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CheckoutActivity.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CheckoutActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.ute.models.response.ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CheckoutActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeCartItem(CartItem item) {
        String token = "Bearer " + sessionManager.getAuthToken();
        
        // Determine which ID to use (same logic as updateCartItemQuantity)
        Long itemId = item.getId();
        if (itemId == null && item.getProductId() != null) {
            itemId = item.getProductId();
            Log.w("CheckoutActivity", "CartItem ID is null, using productId=" + itemId);
        }
        if (itemId == null && item.getProduct() != null && item.getProduct().getId() != null) {
            itemId = item.getProduct().getId();
            Log.w("CheckoutActivity", "Both CartItem ID and productId are null, using product.getId()=" + itemId);
        }
        
        if (itemId == null) {
            Log.e("CheckoutActivity", "Cannot remove item - all ID sources are null!");
            Toast.makeText(CheckoutActivity.this, "Lỗi: Không thể xóa sản phẩm (thiếu ID)", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Log.d("CheckoutActivity", "removeCartItem: Using itemId=" + itemId);
        
        apiService.removeCartItem(token, itemId).enqueue(new Callback<com.example.ute.models.response.ApiResponse>() {
            @Override
            public void onResponse(Call<com.example.ute.models.response.ApiResponse> call, Response<com.example.ute.models.response.ApiResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().isSuccess()) {
                            cartItems.remove(item);
                            if (rvOrderItems.getAdapter() != null) {
                                ((CartAdapter)rvOrderItems.getAdapter()).notifyDataSetChanged();
                            }
                            updatePriceSummary();
                            Toast.makeText(CheckoutActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = response.body().getMessage();
                            Toast.makeText(CheckoutActivity.this, message != null ? message : "Lỗi xóa sản phẩm", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CheckoutActivity.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CheckoutActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.ute.models.response.ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CheckoutActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCartItemQuantityNew(CartDataResponse.CartItemData item, int newQuantity) {
        String token = "Bearer " + sessionManager.getAuthToken();
        Long productId = item.getProductId();
        
        if (productId == null) {
            Log.e("CheckoutActivity", "Cannot update quantity - productId is null!");
            Toast.makeText(CheckoutActivity.this, "Lỗi: Không thể cập nhật sản phẩm (thiếu ID)", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Log.d("CheckoutActivity", "updateCartItemQuantityNew: Using productId=" + productId + ", quantity=" + newQuantity);
        
        UpdateCartRequest request = new UpdateCartRequest(newQuantity);
        
        apiService.updateCartItem(token, productId, request).enqueue(new Callback<com.example.ute.models.response.ApiResponse>() {
            @Override
            public void onResponse(Call<com.example.ute.models.response.ApiResponse> call, Response<com.example.ute.models.response.ApiResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().isSuccess()) {
                            item.setQuantity(newQuantity);
                            updatePriceSummary();
                            Toast.makeText(CheckoutActivity.this, "Cập nhật số lượng thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = response.body().getMessage();
                            Toast.makeText(CheckoutActivity.this, message != null ? message : "Lỗi cập nhật", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CheckoutActivity.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CheckoutActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.ute.models.response.ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CheckoutActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeCartItemNew(CartDataResponse.CartItemData item) {
        String token = "Bearer " + sessionManager.getAuthToken();
        Long productId = item.getProductId();
        
        if (productId == null) {
            Log.e("CheckoutActivity", "Cannot remove item - productId is null!");
            Toast.makeText(CheckoutActivity.this, "Lỗi: Không thể xóa sản phẩm (thiếu ID)", Toast.LENGTH_SHORT).show();
            return;
        }
        
        Log.d("CheckoutActivity", "removeCartItemNew: Using productId=" + productId);
        
        apiService.removeCartItem(token, productId).enqueue(new Callback<com.example.ute.models.response.ApiResponse>() {
            @Override
            public void onResponse(Call<com.example.ute.models.response.ApiResponse> call, Response<com.example.ute.models.response.ApiResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        if (response.body().isSuccess()) {
                            loadCartItems(); // Reload cart after removal
                            Toast.makeText(CheckoutActivity.this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            String message = response.body().getMessage();
                            Toast.makeText(CheckoutActivity.this, message != null ? message : "Lỗi xóa sản phẩm", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CheckoutActivity.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CheckoutActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<com.example.ute.models.response.ApiResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(CheckoutActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    
    private void updatePriceSummary() {
        // totalAmount là giá gốc trước khi giảm
        double subtotal = totalAmount;
        double shipping = subtotal > 500000 ? 0 : 30000;
        double finalTotal = subtotal - discountAmount + shipping;

        tvSubtotal.setText(PriceFormatter.format(subtotal));
        tvShipping.setText(shipping == 0 ? getString(R.string.free_shipping) : PriceFormatter.format(shipping));
        
        if (discountAmount > 0) {
            tvDiscount.setVisibility(View.VISIBLE);
            tvDiscount.setText("- " + PriceFormatter.format(discountAmount));
        } else {
            tvDiscount.setVisibility(View.GONE);
        }
        
        tvTotal.setText(PriceFormatter.format(finalTotal));
    }

    private void applyVoucher() {
        String voucherCode = etVoucherCode.getText().toString().trim();
        
        if (voucherCode.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã voucher", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        
        // Convert totalAmount to Long to avoid scientific notation
        Long totalAmountLong = Math.round(totalAmount);
        ValidateVoucherRequest request = new ValidateVoucherRequest(voucherCode, totalAmountLong);
        
        apiService.validateVoucher(request).enqueue(new Callback<VoucherValidationResponse>() {
            @Override
            public void onResponse(Call<VoucherValidationResponse> call, Response<VoucherValidationResponse> response) {
                progressBar.setVisibility(View.GONE);
                
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        VoucherValidationResponse validationResponse = response.body();
                        if (validationResponse.isSuccess() && validationResponse.getData() != null) {
                            VoucherValidationResponse.VoucherValidationData validationData = validationResponse.getData();
                            
                            // Use discountAmount from backend response
                            Double backendDiscount = validationData.getDiscountAmount();
                            if (backendDiscount != null && backendDiscount > 0) {
                                discountAmount = backendDiscount;
                                updatePriceSummary();
                                etVoucherCode.setEnabled(false);
                                btnApplyVoucher.setEnabled(false);
                                Toast.makeText(CheckoutActivity.this, "Áp dụng voucher thành công!", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(CheckoutActivity.this, "Mã voucher không hợp lệ hoặc không có giảm giá", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            String message = validationResponse.getMessage();
                            Toast.makeText(CheckoutActivity.this, message != null ? message : "Mã voucher không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CheckoutActivity.this, "Server error: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(CheckoutActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<VoucherValidationResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                t.printStackTrace();
                Toast.makeText(CheckoutActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void placeOrder() {
        // Validate cart is not empty first
        if (cartItemDataList == null || cartItemDataList.isEmpty()) {
            Toast.makeText(this, "Giỏ hàng trống. Vui lòng thêm sản phẩm", Toast.LENGTH_LONG).show();
            return;
        }

        String fullName = etFullName.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();

        // Validate form fields
        if (fullName.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập tên người nhận", Toast.LENGTH_SHORT).show();
            etFullName.requestFocus();
            return;
        }

        if (phone.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập số điện thoại", Toast.LENGTH_SHORT).show();
            etPhone.requestFocus();
            return;
        }

        if (phone.length() < 10) {
            Toast.makeText(this, "Số điện thoại không hợp lệ (tối thiểu 10 số)", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedAddress == null) {
            Toast.makeText(this, "Vui lòng chọn địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        btnPlaceOrder.setEnabled(false);

        // Create checkout request with proper format
        CheckoutRequest.ShippingInfo shippingInfo = new CheckoutRequest.ShippingInfo();
        shippingInfo.setRecipientName(fullName);
        shippingInfo.setPhoneNumber(phone);
        shippingInfo.setStreetAddress(selectedAddress.getStreetAddress());
        shippingInfo.setCity(selectedAddress.getProvince());
        shippingInfo.setWard(selectedAddress.getWard());
        shippingInfo.setEmail(sessionManager.getUserEmail());  // Add email

        CheckoutRequest checkoutRequest = new CheckoutRequest();
        checkoutRequest.setShippingInfo(shippingInfo);
        checkoutRequest.setPaymentMethod("COD");
        
        // Add voucher code if applied
        String voucherCode = etVoucherCode.getText().toString().trim();
        if (!voucherCode.isEmpty()) {
            checkoutRequest.setVoucherCode(voucherCode);
        }
        
        Log.d(TAG, "placeOrder: Order details - name=" + fullName + ", phone=" + phone + 
            ", address=" + selectedAddress.getStreetAddress() + 
            ", ward=" + selectedAddress.getWard() + 
            ", city=" + selectedAddress.getProvince() + 
            ", totalAmount=" + totalAmount);

        String token = "Bearer " + sessionManager.getAuthToken();
        apiService.checkout(token, checkoutRequest).enqueue(new Callback<OrderResponse>() {
            @Override
            public void onResponse(Call<OrderResponse> call, Response<OrderResponse> response) {
                progressBar.setVisibility(View.GONE);
                btnPlaceOrder.setEnabled(true);

                if (response.isSuccessful() && response.body() != null) {
                    OrderResponse orderResponse = response.body();
                    if (orderResponse.isSuccess()) {
                        Log.d(TAG, "Order created successfully");
                        Toast.makeText(CheckoutActivity.this, "Đặt hàng thành công!", Toast.LENGTH_SHORT).show();
                        // Save order to session or navigate to order detail
                        finish();
                    } else {
                        String message = orderResponse.getMessage();
                        Toast.makeText(CheckoutActivity.this, message != null ? message : "Lỗi đặt hàng", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Order failed: " + response.code() + " - " + response.message());
                    Toast.makeText(CheckoutActivity.this, "Lỗi đặt hàng: " + response.code(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<OrderResponse> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                btnPlaceOrder.setEnabled(true);
                Log.e(TAG, "Order API failed", t);
                Toast.makeText(CheckoutActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
