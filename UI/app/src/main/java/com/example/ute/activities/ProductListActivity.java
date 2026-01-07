package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.adapters.ProductAdapter;
import com.example.ute.adapters.BrandFilterAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Product;
import com.example.ute.models.Brand;
import com.example.ute.models.response.ProductListResponse;
import com.example.ute.models.response.BrandResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductListActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    public static final String EXTRA_CATEGORY_ID = "category_id";
    public static final String EXTRA_CATEGORY_NAME = "category_name";
    public static final String EXTRA_SEARCH_QUERY = "search_query";
    public static final String EXTRA_FILTER_TYPE = "filter_type";

    private static final int PAGE_SIZE = 12;

    private RecyclerView rvProducts;
    private ProgressBar progressBar;
    private LinearLayout layoutEmpty;
    private TextView tvResultCount;
    private Spinner spinnerSort;
    private RecyclerView rvBrandFilter;
    
    // Pagination
    private LinearLayout layoutPagination;
    private LinearLayout layoutPageNumbers;
    private ImageButton btnPrevPage, btnNextPage;
    
    private ProductAdapter adapter;
    private BrandFilterAdapter brandFilterAdapter;
    private ApiService apiService;

    private Long categoryId;
    private Long selectedBrandId;
    private String searchQuery;
    private String sortBy = "createdAt:desc";
    
    // Pagination state
    private int currentPage = 1;
    private int totalPages = 1;
    private long totalItems = 0;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);

        apiService = ApiClient.getApiService();

        long catId = getIntent().getLongExtra(EXTRA_CATEGORY_ID, -1);
        categoryId = catId > 0 ? catId : null;
        searchQuery = getIntent().getStringExtra(EXTRA_SEARCH_QUERY);
        String categoryName = getIntent().getStringExtra(EXTRA_CATEGORY_NAME);

        setupToolbar(categoryName);
        initViews();
        setupSort();
        setupPagination();
        loadBrands();
        loadProducts(1);
    }

    private void setupToolbar(String title) {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (searchQuery != null && !searchQuery.isEmpty()) {
                getSupportActionBar().setTitle("Tìm kiếm: " + searchQuery);
            } else {
                getSupportActionBar().setTitle(title != null ? title : getString(R.string.featured_products));
            }
        }
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initViews() {
        rvProducts = findViewById(R.id.rvProducts);
        progressBar = findViewById(R.id.progressBar);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        tvResultCount = findViewById(R.id.tvResultCount);
        spinnerSort = findViewById(R.id.spinnerSort);
        rvBrandFilter = findViewById(R.id.rvBrandFilter);
        
        // Pagination views
        layoutPagination = findViewById(R.id.layoutPagination);
        layoutPageNumbers = findViewById(R.id.layoutPageNumbers);
        btnPrevPage = findViewById(R.id.btnPrevPage);
        btnNextPage = findViewById(R.id.btnNextPage);
        
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProducts.setLayoutManager(layoutManager);
        
        adapter = new ProductAdapter(new ArrayList<>(), this);
        rvProducts.setAdapter(adapter);
        
        // Setup brand filter RecyclerView
        LinearLayoutManager brandLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvBrandFilter.setLayoutManager(brandLayoutManager);
    }
    
    private void setupSort() {
        String[] sortOptions = {
            "Mới nhất",
            "Giá thấp đến cao",
            "Giá cao đến thấp",
            "Tên A-Z"
        };
        
        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(
            this, 
            android.R.layout.simple_spinner_item, 
            sortOptions
        );
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(sortAdapter);
        
        spinnerSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String newSortBy;
                switch (position) {
                    case 1: newSortBy = "price:asc"; break;
                    case 2: newSortBy = "price:desc"; break;
                    case 3: newSortBy = "name:asc"; break;
                    default: newSortBy = "createdAt:desc"; break;
                }
                
                if (!newSortBy.equals(sortBy)) {
                    sortBy = newSortBy;
                    loadProducts(1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }
    
    private void setupPagination() {
        btnPrevPage.setOnClickListener(v -> {
            if (currentPage > 1) {
                loadProducts(currentPage - 1);
            }
        });
        
        btnNextPage.setOnClickListener(v -> {
            if (currentPage < totalPages) {
                loadProducts(currentPage + 1);
            }
        });
    }

    private void loadProducts(int page) {
        if (isLoading) return;
        
        isLoading = true;
        currentPage = page;
        showLoading(true);
        
        Call<ProductListResponse> call;
        
        if (searchQuery != null && !searchQuery.isEmpty()) {
            call = apiService.searchProducts(searchQuery, page, PAGE_SIZE);
        } else {
            call = apiService.getProducts(page, PAGE_SIZE, categoryId, selectedBrandId, sortBy);
        }

        call.enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                isLoading = false;
                showLoading(false);
                
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        ProductListResponse productResponse = response.body();
                        if (productResponse.isSuccess() && productResponse.getData() != null) {
                            List<Product> products = productResponse.getData();
                            
                            // Update pagination info
                            totalPages = productResponse.getTotalPages();
                            totalItems = productResponse.getTotalItems();
                            
                            // Update UI
                            adapter.updateData(products);
                            updateResultCount(products.size());
                            updatePagination();
                            
                            // Show empty if no products
                            showEmpty(products.isEmpty());
                            
                            // Scroll to top
                            rvProducts.scrollToPosition(0);
                        } else {
                            adapter.updateData(new ArrayList<>());
                            showEmpty(true);
                        }
                    } else {
                        adapter.updateData(new ArrayList<>());
                        showEmpty(true);
                    }
                } catch (Exception e) {
                    adapter.updateData(new ArrayList<>());
                    showEmpty(true);
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                isLoading = false;
                showLoading(false);
                adapter.updateData(new ArrayList<>());
                showEmpty(true);
            }
        });
    }
    
    private void loadBrands() {
        apiService.getBrands().enqueue(new Callback<BrandResponse>() {
            @Override
            public void onResponse(Call<BrandResponse> call, Response<BrandResponse> response) {
                try {
                    if (response.isSuccessful() && response.body() != null) {
                        BrandResponse brandResponse = response.body();
                        if (brandResponse.isSuccess() && brandResponse.getData() != null) {
                            List<Brand> brands = brandResponse.getData();
                            setupBrandFilter(brands);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<BrandResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
    
    private void setupBrandFilter(List<Brand> brands) {
        brandFilterAdapter = new BrandFilterAdapter(brands, brand -> {
            selectedBrandId = brand.getId();
            loadProducts(1);
        });
        rvBrandFilter.setAdapter(brandFilterAdapter);
    }
    
    private void updateResultCount(int count) {
        if (totalItems > 0) {
            tvResultCount.setText(String.format("Hiển thị %d / %d sản phẩm", count, totalItems));
        } else {
            tvResultCount.setText(count + " sản phẩm");
        }
    }
    
    private void updatePagination() {
        if (totalPages <= 1) {
            layoutPagination.setVisibility(View.GONE);
            return;
        }
        
        layoutPagination.setVisibility(View.VISIBLE);
        
        // Update prev/next buttons
        btnPrevPage.setEnabled(currentPage > 1);
        btnPrevPage.setAlpha(currentPage > 1 ? 1f : 0.5f);
        btnNextPage.setEnabled(currentPage < totalPages);
        btnNextPage.setAlpha(currentPage < totalPages ? 1f : 0.5f);
        
        // Generate page number buttons
        layoutPageNumbers.removeAllViews();
        
        int startPage = Math.max(1, currentPage - 2);
        int endPage = Math.min(totalPages, currentPage + 2);
        
        // First page
        if (startPage > 1) {
            addPageButton(1);
            if (startPage > 2) {
                addDotsView();
            }
        }
        
        // Page range
        for (int i = startPage; i <= endPage; i++) {
            addPageButton(i);
        }
        
        // Last page
        if (endPage < totalPages) {
            if (endPage < totalPages - 1) {
                addDotsView();
            }
            addPageButton(totalPages);
        }
    }
    
    private void addPageButton(int page) {
        TextView pageBtn = new TextView(this);
        pageBtn.setText(String.valueOf(page));
        pageBtn.setTextSize(14);
        pageBtn.setPadding(32, 20, 32, 20);
        
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(4, 0, 4, 0);
        pageBtn.setLayoutParams(params);
        
        if (page == currentPage) {
            pageBtn.setBackgroundResource(R.drawable.pagination_button_active);
            pageBtn.setTextColor(ContextCompat.getColor(this, R.color.white));
        } else {
            pageBtn.setBackgroundResource(R.drawable.pagination_button_bg);
            pageBtn.setTextColor(ContextCompat.getColor(this, R.color.text_primary));
            pageBtn.setOnClickListener(v -> loadProducts(page));
        }
        
        layoutPageNumbers.addView(pageBtn);
    }
    
    private void addDotsView() {
        TextView dots = new TextView(this);
        dots.setText("...");
        dots.setTextSize(14);
        dots.setPadding(16, 20, 16, 20);
        dots.setTextColor(ContextCompat.getColor(this, R.color.text_secondary));
        layoutPageNumbers.addView(dots);
    }
    
    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        rvProducts.setVisibility(show ? View.GONE : View.VISIBLE);
    }
    
    private void showEmpty(boolean show) {
        layoutEmpty.setVisibility(show ? View.VISIBLE : View.GONE);
        rvProducts.setVisibility(show ? View.GONE : View.VISIBLE);
        if (show) {
            layoutPagination.setVisibility(View.GONE);
        }
    }
    
    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, product.getId());
        startActivity(intent);
    }

    @Override
    public void onAddToCartClick(Product product) {
        // TODO: Add to cart
    }
}
