package com.example.ute.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ute.R;
import com.example.ute.activities.ProductDetailActivity;
import com.example.ute.activities.ProductListActivity;
import com.example.ute.adapters.BannerAdapter;
import com.example.ute.adapters.CategoryAdapter;
import com.example.ute.adapters.ProductAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Category;
import com.example.ute.models.Product;
import com.example.ute.models.response.CategoryListResponse;
import com.example.ute.models.response.ProductListResponse;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment implements ProductAdapter.OnProductClickListener {
    
    private ViewPager2 viewPagerBanner;
    private TabLayout tabLayoutIndicator;
    private RecyclerView rvCategories;
    private RecyclerView rvFeaturedProducts;
    private TextView tvCountdownHours, tvCountdownMinutes, tvCountdownSeconds;
    private TextView tvViewAllProducts;
    private MaterialCardView cardFlashSale, cardOnlineOnly;
    
    private ApiService apiService;
    private ProductAdapter productAdapter;
    private CategoryAdapter categoryAdapter;
    private BannerAdapter bannerAdapter;
    
    private Timer bannerTimer;
    private Timer countdownTimer;
    private int currentBannerPosition = 0;
    
    // Countdown values (for demo)
    private int countdownSeconds = 55;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        apiService = ApiClient.getApiService();
        
        initViews(view);
        setupBanner();
        setupCategories();
        setupFeaturedProducts();
        setupCountdown();
        setupListeners();
        
        loadData();
    }
    
    private void initViews(View view) {
        viewPagerBanner = view.findViewById(R.id.viewPagerBanner);
        tabLayoutIndicator = view.findViewById(R.id.tabLayoutIndicator);
        rvCategories = view.findViewById(R.id.rvCategories);
        rvFeaturedProducts = view.findViewById(R.id.rvFeaturedProducts);
        tvCountdownHours = view.findViewById(R.id.tvCountdownHours);
        tvCountdownMinutes = view.findViewById(R.id.tvCountdownMinutes);
        tvCountdownSeconds = view.findViewById(R.id.tvCountdownSeconds);
        tvViewAllProducts = view.findViewById(R.id.tvViewAllProducts);
        cardFlashSale = view.findViewById(R.id.cardFlashSale);
        cardOnlineOnly = view.findViewById(R.id.cardOnlineOnly);
    }
    
    private void setupBanner() {
        // Skip banner setup if view is hidden
        if (viewPagerBanner == null || viewPagerBanner.getVisibility() == View.GONE) {
            return;
        }
        
        List<Integer> bannerImages = new ArrayList<>();
        bannerImages.add(R.drawable.banner_placeholder);
        bannerImages.add(R.drawable.banner_placeholder);
        bannerImages.add(R.drawable.banner_placeholder);
        
        bannerAdapter = new BannerAdapter(bannerImages);
        viewPagerBanner.setAdapter(bannerAdapter);
        
        new TabLayoutMediator(tabLayoutIndicator, viewPagerBanner, (tab, position) -> {
            // No text for indicator
        }).attach();
        
        // Auto scroll banner
        startBannerAutoScroll();
    }
    
    private void startBannerAutoScroll() {
        if (viewPagerBanner == null || bannerAdapter == null) {
            return;
        }
        
        bannerTimer = new Timer();
        bannerTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (bannerAdapter != null && bannerAdapter.getItemCount() > 0 && viewPagerBanner != null) {
                        currentBannerPosition++;
                        if (currentBannerPosition >= bannerAdapter.getItemCount()) {
                            currentBannerPosition = 0;
                        }
                        viewPagerBanner.setCurrentItem(currentBannerPosition, true);
                    }
                });
            }
        }, 3000, 3000);
    }
    
    private void setupCategories() {
        rvCategories.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter(new ArrayList<>(), category -> {
            // Navigate to product list with category filter
            Intent intent = new Intent(getActivity(), ProductListActivity.class);
            intent.putExtra(ProductListActivity.EXTRA_CATEGORY_ID, category.getId());
            intent.putExtra(ProductListActivity.EXTRA_CATEGORY_NAME, category.getName());
            startActivity(intent);
        });
        rvCategories.setAdapter(categoryAdapter);
    }
    
    private void setupFeaturedProducts() {
        rvFeaturedProducts.setLayoutManager(new GridLayoutManager(getContext(), 2));
        productAdapter = new ProductAdapter(new ArrayList<>(), this);
        rvFeaturedProducts.setAdapter(productAdapter);
        rvFeaturedProducts.setNestedScrollingEnabled(false);
    }
    
    private void setupCountdown() {
        if (tvCountdownHours == null || tvCountdownMinutes == null || tvCountdownSeconds == null) {
            return;
        }
        
        countdownTimer = new Timer();
        countdownTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                new Handler(Looper.getMainLooper()).post(() -> {
                    if (countdownSeconds > 0) {
                        countdownSeconds--;
                    } else {
                        countdownSeconds = 59;
                    }
                    updateCountdownDisplay();
                });
            }
        }, 0, 1000);
    }
    
    private void updateCountdownDisplay() {
        if (tvCountdownHours == null || tvCountdownMinutes == null || tvCountdownSeconds == null) {
            return;
        }
        
        int hours = 0;
        int minutes = 0;
        int seconds = countdownSeconds;
        
        tvCountdownHours.setText(String.format(Locale.getDefault(), "%02d", hours));
        tvCountdownMinutes.setText(String.format(Locale.getDefault(), "%02d", minutes));
        tvCountdownSeconds.setText(String.format(Locale.getDefault(), "%02d", seconds));
    }
    
    private void setupListeners() {
        if (tvViewAllProducts != null) {
            tvViewAllProducts.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                startActivity(intent);
            });
        }
        
        if (cardFlashSale != null) {
            cardFlashSale.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra(ProductListActivity.EXTRA_FILTER_TYPE, "flash_sale");
                startActivity(intent);
            });
        }
        
        if (cardOnlineOnly != null) {
            cardOnlineOnly.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), ProductListActivity.class);
                intent.putExtra(ProductListActivity.EXTRA_FILTER_TYPE, "online_only");
                startActivity(intent);
            });
        }
    }
    
    private void loadData() {
        loadCategories();
        loadFeaturedProducts();
    }
    
    private void loadCategories() {
        apiService.getCategories().enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryListResponse categoryResponse = response.body();
                    if (categoryResponse.isSuccess() && categoryResponse.getData() != null) {
                        categoryAdapter.updateData(categoryResponse.getData());
                    } else {
                        categoryAdapter.updateData(new ArrayList<>());
                    }
                } else {
                    // Load demo categories when API fails
                    categoryAdapter.updateData(new ArrayList<>());
                }
            }
            
            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                // Load demo categories when API fails
                categoryAdapter.updateData(new ArrayList<>());
            }
        });
    }
    
    private void loadFeaturedProducts() {
        apiService.getProducts(1, 6, null, null, null).enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductListResponse productResponse = response.body();
                    if (productResponse.isSuccess() && productResponse.getData() != null) {
                        productAdapter.updateData(productResponse.getData());
                    } else {
                        productAdapter.updateData(new ArrayList<>());
                    }
                } else {
                    productAdapter.updateData(new ArrayList<>());
                }
            }
            
            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                productAdapter.updateData(new ArrayList<>());
            }
        });
    }
    
    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.getId());
        startActivity(intent);
    }
    
    @Override
    public void onAddToCartClick(Product product) {
        // Handle add to cart
    }
    
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (bannerTimer != null) {
            bannerTimer.cancel();
        }
        if (countdownTimer != null) {
            countdownTimer.cancel();
        }
    }
}
