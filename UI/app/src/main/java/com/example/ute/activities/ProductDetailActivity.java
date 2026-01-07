package com.example.ute.activities;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ute.R;
import com.example.ute.adapters.ProductImageAdapter;
import com.example.ute.adapters.ReviewAdapter;
import com.example.ute.adapters.SpecificationAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Product;
import com.example.ute.models.Review;
import com.example.ute.models.request.AddToCartRequest;
import com.example.ute.models.response.ApiResponse;
import com.example.ute.models.response.ProductDetailResponse;
import com.example.ute.models.response.ReviewListResponse;
import com.example.ute.utils.PriceFormatter;
import com.example.ute.utils.SessionManager;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    
    public static final String EXTRA_PRODUCT_ID = "product_id";
    
    private ViewPager2 viewPagerImages;
    private TabLayout tabLayoutIndicator;
    private TextView tvProductName, tvProductPrice, tvOriginalPrice;
    private TextView tvStockStatus;
    private TextView tvDescription;
    private Button btnAddToCart, btnBuyNow, btnWriteReview;
    private ImageButton btnDecrease, btnIncrease;
    private TextView tvQuantity;
    private RecyclerView rvReviews;
    private RecyclerView rvSpecifications;
    private ImageView ivProductThumbnail;
    
    private int quantity = 1;
    private Product product;
    private Long productId;
    
    private ApiService apiService;
    private SessionManager sessionManager;
    private List<Review> reviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        
        apiService = ApiClient.getApiService();
        sessionManager = new SessionManager(this);
        reviews = new ArrayList<>();
        
        productId = getIntent().getLongExtra(EXTRA_PRODUCT_ID, -1);
        if (productId == -1) {
            Toast.makeText(this, "Product not found", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        
        initViews();
        setupListeners();
        loadProductDetail();
        loadProductReviews();
    }
    
    private void initViews() {
        viewPagerImages = findViewById(R.id.vpProductImages);
        tabLayoutIndicator = findViewById(R.id.tabIndicator);
        tvProductName = findViewById(R.id.tvProductName);
        tvProductPrice = findViewById(R.id.tvPrice);
        tvOriginalPrice = findViewById(R.id.tvOriginalPrice);
        tvStockStatus = findViewById(R.id.tvStockStatus);
        tvDescription = findViewById(R.id.tvDescription);
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnBuyNow = findViewById(R.id.btnBuyNow);
        btnWriteReview = findViewById(R.id.btnWriteReview);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease = findViewById(R.id.btnIncrease);
        tvQuantity = findViewById(R.id.tvQuantity);
        rvSpecifications = findViewById(R.id.rvSpecifications);
        rvReviews = findViewById(R.id.rvReviews);
        
        // rvReviews = findViewById(R.id.rvReviews);  // TODO: Add to layout
        // Setup recycler view for reviews
        if (rvReviews != null) {
            rvReviews.setLayoutManager(new LinearLayoutManager(this));
            rvReviews.setNestedScrollingEnabled(false);
        }
        
        // Setup specifications RecyclerView
        if (rvSpecifications != null) {
            rvSpecifications.setLayoutManager(new LinearLayoutManager(this));
            rvSpecifications.setNestedScrollingEnabled(false);
        }
        
        // Setup toolbar navigation
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
    
    private void setupListeners() {
        btnWriteReview.setOnClickListener(v -> showReviewDialog());
        
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
                        showEmptyState();
                    }
                } else {
                    showEmptyState();
                }
            }
            
            @Override
            public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                showEmptyState();
            }
        });
    }
    
    private void loadProductReviews() {
        Log.d("ProductDetail", "Loading reviews for productId: " + productId);
        String token = sessionManager.getAuthToken();
        if (token == null || token.isEmpty()) {
            Log.d("ProductDetail", "No token available, reviews may not load if authentication is required");
            token = "";
        } else {
            token = "Bearer " + token;
        }
        
        apiService.getProductReviews(token, productId, 1, 5).enqueue(new Callback<ReviewListResponse>() {
            @Override
            public void onResponse(Call<ReviewListResponse> call, Response<ReviewListResponse> response) {
                Log.d("ProductDetail", "Reviews API response code: " + response.code());
                if (response.isSuccessful() && response.body() != null) {
                    ReviewListResponse reviewResponse = response.body();
                    Log.d("ProductDetail", "Reviews response: success=" + reviewResponse.isSuccess() + ", data=" + (reviewResponse.getData() != null ? reviewResponse.getData().size() : "null"));
                    if (reviewResponse.isSuccess()) {
                        if (reviewResponse.getData() != null) {
                            reviews.clear();
                            reviews.addAll(reviewResponse.getData());
                            Log.d("ProductDetail", "Successfully loaded " + reviews.size() + " reviews");
                        } else {
                            Log.d("ProductDetail", "No reviews in response (data is null)");
                        }
                        setupReviewsAdapter();
                    } else {
                        Log.e("ProductDetail", "Response not successful: " + reviewResponse.getMessage());
                    }
                } else {
                    Log.e("ProductDetail", "Failed to load reviews: response not successful, code=" + response.code());
                    if (response.errorBody() != null) {
                        try {
                            Log.e("ProductDetail", "Error body: " + response.errorBody().string());
                        } catch (Exception e) {
                            Log.e("ProductDetail", "Could not read error body");
                        }
                    }
                }
            }
            
            @Override
            public void onFailure(Call<ReviewListResponse> call, Throwable t) {
                Log.e("ProductDetail", "Failed to load reviews: " + t.getMessage(), t);
            }
        });
    }
    
    private void setupReviewsAdapter() {
        if (rvReviews != null) {
            Log.d("ProductDetail", "Setting up reviews adapter with " + reviews.size() + " reviews");
            ReviewAdapter adapter = new ReviewAdapter(reviews, sessionManager, review -> {
                // On like/unlike, refresh the reviews if needed
            });
            rvReviews.setAdapter(adapter);
        } else {
            Log.e("ProductDetail", "rvReviews is null!");
        }
    }
    
    private void showEmptyState() {
        Toast.makeText(this, "Không thể tải sản phẩm", Toast.LENGTH_SHORT).show();
        finish();
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
        
        // Load thumbnail image from URL using Glide
        if (product.getThumbnailUrl() != null && !product.getThumbnailUrl().isEmpty()) {
            Log.d("ProductDetail", "Loading thumbnail from: " + product.getThumbnailUrl());
            // Load into the CollapsibleAppBar or placeholder ImageView if available
            // For now, we'll use the ViewPager if there are images
        }
        
        // Setup image slider with Glide
        if (product.getImages() != null && !product.getImages().isEmpty()) {
            ProductImageAdapter adapter = new ProductImageAdapter(product.getImages(), getBaseUrl());
            viewPagerImages.setAdapter(adapter);
            
            new TabLayoutMediator(tabLayoutIndicator, viewPagerImages, (tab, position) -> {
                // No text for indicator
            }).attach();
        } else if (product.getThumbnailUrl() != null && !product.getThumbnailUrl().isEmpty()) {
            // If no product images array, create a list with just the thumbnail URL
            List<String> thumbnailList = new ArrayList<>();
            thumbnailList.add(product.getThumbnailUrl());
            ProductImageAdapter adapter = new ProductImageAdapter(thumbnailList, "");
            viewPagerImages.setAdapter(adapter);
            tabLayoutIndicator.setVisibility(View.GONE);
        } else {
            // Load placeholder if no images
            setupPlaceholderImage();
        }
        
        // Parse and display specifications if available
        displaySpecifications();
    }
    
    private void setupPlaceholderImage() {
        // Load placeholder using Glide
        Glide.with(this)
                .load(R.drawable.placeholder_product)
                .centerInside()
                .into(new com.bumptech.glide.request.target.SimpleTarget<android.graphics.drawable.Drawable>() {
                    @Override
                    public void onResourceReady(android.graphics.drawable.Drawable resource, com.bumptech.glide.request.transition.Transition<? super android.graphics.drawable.Drawable> transition) {
                        // Placeholder loaded
                    }
                });
    }
    
    private String getBaseUrl() {
        // Return emulator base URL or device base URL
        return "http://10.0.2.2:8080/"; // Adjust if using physical device
    }
    
    private void displaySpecifications() {
        if (product == null || product.getSpecifications() == null) {
            Log.d("ProductDetail", "No specifications to display");
            return;
        }
        
        try {
            Object specsObj = product.getSpecifications();
            Map<String, Object> specs = null;
            
            // Parse specifications based on type
            if (specsObj instanceof String) {
                // If specifications is a JSON string, parse it
                String specsJson = (String) specsObj;
                Log.d("ProductDetail", "Parsing specifications JSON: " + specsJson);
                Gson gson = new Gson();
                specs = gson.fromJson(specsJson, Map.class);
            } else if (specsObj instanceof Map) {
                // If already a Map, use it directly
                specs = (Map<String, Object>) specsObj;
            }
            
            if (specs != null && !specs.isEmpty()) {
                Log.d("ProductDetail", "Found " + specs.size() + " specifications");
                SpecificationAdapter adapter = new SpecificationAdapter(specs);
                rvSpecifications.setAdapter(adapter);
            } else {
                Log.d("ProductDetail", "Specifications map is empty");
            }
        } catch (JsonSyntaxException e) {
            Log.e("ProductDetail", "Error parsing specifications JSON: " + e.getMessage(), e);
        } catch (Exception e) {
            Log.e("ProductDetail", "Error displaying specifications: " + e.getMessage(), e);
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
    
    private void showReviewDialog() {
        String token = sessionManager.getAuthToken();
        if (token == null || token.isEmpty()) {
            Toast.makeText(this, "Vui lòng đăng nhập để viết đánh giá", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_submit_review, null);

        RatingBar ratingBar = dialogView.findViewById(R.id.ratingBarSubmit);
        EditText etComment = dialogView.findViewById(R.id.etComment);
        Button btnCancel = dialogView.findViewById(R.id.btnCancel);
        Button btnSubmit = dialogView.findViewById(R.id.btnSubmit);

        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        btnCancel.setOnClickListener(v -> dialog.dismiss());

        btnSubmit.setOnClickListener(v -> {
            int rating = (int) ratingBar.getRating();
            String comment = etComment.getText().toString().trim();

            if (rating == 0) {
                Toast.makeText(this, "Vui lòng chọn đánh giá", Toast.LENGTH_SHORT).show();
                return;
            }

            submitReview(rating, comment);
            dialog.dismiss();
        });

        dialog.show();
    }

    private void submitReview(int rating, String comment) {
        Review review = new Review();
        review.setRating(rating);
        review.setComment(comment.isEmpty() ? null : comment);

        String token = sessionManager.getAuthToken();
        apiService.submitReview("Bearer " + token, productId, review)
                .enqueue(new Callback<com.example.ute.models.response.ReviewResponse>() {
                    @Override
                    public void onResponse(Call<com.example.ute.models.response.ReviewResponse> call,
                            Response<com.example.ute.models.response.ReviewResponse> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ProductDetailActivity.this, "Gửi đánh giá thành công!", Toast.LENGTH_SHORT).show();
                            // Reload reviews
                            loadProductReviews();
                        } else if (response.code() == 409) {
                            Toast.makeText(ProductDetailActivity.this, "Bạn đã đánh giá sản phẩm này rồi", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ProductDetailActivity.this, "Lỗi khi gửi đánh giá", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<com.example.ute.models.response.ReviewResponse> call, Throwable t) {
                        Toast.makeText(ProductDetailActivity.this, "Lỗi: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
