package com.example.ute.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ute.R;
import com.example.ute.adapters.ProductImageAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Product;
import com.example.ute.models.request.AddToCartRequest;
import com.example.ute.models.response.ApiResponse;
import com.example.ute.models.response.ProductDetailResponse;
import com.example.ute.utils.PriceFormatter;
import com.example.ute.utils.SessionManager;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    
    public static final String EXTRA_PRODUCT_ID = "product_id";
    
    private ViewPager2 viewPagerImages;
    private TabLayout tabLayoutIndicator;
    private ImageButton btnShare, btnFavorite;
    private TextView tvProductName, tvProductPrice, tvOriginalPrice;
    private TextView tvStockStatus;
    private TextView tvDescription;
    private Button btnAddToCart, btnBuyNow;
    private ImageButton btnDecrease, btnIncrease;
    private TextView tvQuantity;
    
    private int quantity = 1;
    private Product product;
    private Long productId;
    
    private ApiService apiService;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        
        apiService = ApiClient.getApiService();
        sessionManager = new SessionManager(this);
        
        productId = getIntent().getLongExtra(EXTRA_PRODUCT_ID, -1);
        if (productId == -1) {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        initViews();
        setupListeners();
        loadProductDetail();
    }
    
    private void initViews() {
        viewPagerImages = findViewById(R.id.vpProductImages);
        tabLayoutIndicator = findViewById(R.id.tabIndicator);
        btnShare = findViewById(R.id.btnShare);
        btnFavorite = findViewById(R.id.btnFavorite);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvPrice);
        tvOriginalPrice = findViewById(R.id.tvOriginalPrice);
        tvStockStatus = findViewById(R.id.tvStockStatus);
        tvDescription = findViewById(R.id.tvDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease = findViewById(R.id.btnIncrease);
        tvQuantity = findViewById(R.id.tvQuantity);
        
        // Setup toolbar navigation
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
    
    private void setupListeners() {
        btnShare.setOnClickListener(v -> {
            // TODO: Implement share
        });
        
        btnFavorite.setOnClickListener(v -> {
            // TODO: Implement add to favorites
        });
        
        btnDecrease.setOnClickListener(v -> {
            if (quantity > 1) {
                quantity--;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });
        
        btnIncrease.setOnClickListener(v -> {
            if (product != null && quantity < product.getStockQuantity()) {
                quantity++;
                tvQuantity.setText(String.valueOf(quantity));
            }
        });
        
        btnAddToCart.setOnClickListener(v -> addToCart());
        
        btnBuyNow.setOnClickListener(v -> {
            addToCart();
            // Navigate to cart
        });
    }
    
    private void loadProductDetail() {
        apiService.getProductDetail(productId).enqueue(new Callback<ProductDetailResponse>() {
            @Override
            public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductDetailResponse productResponse = response.body();
                    if (productResponse.isSuccess() && productResponse.getData() != null) {
                        product = productResponse.getData();
                        displayProduct();
                    } else {
                        loadDemoProduct();
                    }
                } else {
                    loadDemoProduct();
                }
            }
            
            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                loadDemoProduct();
            }
        });
    }
    
    private void loadDemoProduct() {
        product = new Product();
        product.setId(productId);
        product.setName("iPhone 15 Pro Max 256GB - Titanium Black");
        product.setPrice(34990000.0);
        product.setOriginalPrice(38990000.0);
        product.setStockQuantity(10);
        product.setDescription("iPhone 15 Pro Max với chip A17 Pro mạnh mẽ nhất, camera 48MP với zoom quang học 5x, " +
                "khung titan bền bỉ cùng thiết kế Action Button hoàn toàn mới. Màn hình Super Retina XDR 6.7 inch " +
                "với ProMotion 120Hz mang đến trải nghiệm hình ảnh sống động. Hỗ trợ USB-C với tốc độ truyền tải " +
                "nhanh hơn. Pin sử dụng cả ngày với sạc nhanh 20W và sạc không dây MagSafe 15W.");
        displayProduct();
    }
    
    private void displayProduct() {
        if (product == null) return;
        
        tvProductName.setText(product.getName());
        tvProductPrice.setText(PriceFormatter.format(product.getPrice()));
        
        if (product.getOriginalPrice() != null && product.getOriginalPrice() > product.getPrice()) {
            tvOriginalPrice.setVisibility(View.VISIBLE);
            tvOriginalPrice.setText(PriceFormatter.format(product.getOriginalPrice()));
        } else {
            tvOriginalPrice.setVisibility(View.GONE);
        }
        
        if (product.getStockQuantity() > 0) {
            tvStockStatus.setText(getString(R.string.in_stock));
            tvStockStatus.setTextColor(getColor(R.color.success));
            btnAddToCart.setEnabled(true);
            btnBuyNow.setEnabled(true);
        } else {
            tvStockStatus.setText(R.string.out_of_stock);
            tvStockStatus.setTextColor(getColor(R.color.error));
            btnAddToCart.setEnabled(false);
            btnBuyNow.setEnabled(false);
        }
        
        tvDescription.setText(product.getDescription());
        
        // Setup image slider
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            ProductImageAdapter adapter = new ProductImageAdapter(product.getImages());
            viewPagerImages.setAdapter(adapter);
            
            new TabLayoutMediator(tabLayoutIndicator, viewPagerImages, (tab, position) -> {
                // No text for indicator
            }).attach();
        }
    }
    
    private void addToCart() {
        if (!sessionManager.isLoggedIn()) {
            Toast.makeText(this, R.string.login_to_continue, Toast.LENGTH_SHORT).show();
            return;
        }
        
        AddToCartRequest request = new AddToCartRequest(productId, quantity);
        String token = "Bearer " + sessionManager.getAuthToken();
        
        apiService.addToCart(token, request).enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ProductDetailActivity.this, R.string.added_to_cart, Toast.LENGTH_SHORT).show();
                }
            }
            
            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, R.string.error_occurred, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
