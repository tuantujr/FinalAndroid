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
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.adapters.ProductAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Product;
import com.example.ute.models.response.ProductListResponse;

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
    
    // Pagination
    private LinearLayout layoutPagination;
    private LinearLayout layoutPageNumbers;
    private ImageButton btnPrevPage, btnNextPage;
    
    private ProductAdapter adapter;
    private ApiService apiService;

    private Long categoryId;
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
        
        // Pagination views
        layoutPagination = findViewById(R.id.layoutPagination);
        layoutPageNumbers = findViewById(R.id.layoutPageNumbers);
        btnPrevPage = findViewById(R.id.btnPrevPage);
        btnNextPage = findViewById(R.id.btnNextPage);
        
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProducts.setLayoutManager(layoutManager);
        
        adapter = new ProductAdapter(new ArrayList<>(), this);
        rvProducts.setAdapter(adapter);
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
            call = apiService.getProducts(page, PAGE_SIZE, categoryId, null, sortBy);
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
                            loadDemoProducts();
                        }
                    } else {
                        loadDemoProducts();
                    }
                } catch (Exception e) {
                    loadDemoProducts();
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                isLoading = false;
                showLoading(false);
                loadDemoProducts();
            }
        });
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
    
    private void loadDemoProducts() {
        List<Product> demoProducts = new ArrayList<>();
        demoProducts.add(createProduct(1L, "iPhone 15 Pro Max 256GB", 34990000.0, 38990000.0, 10));
        demoProducts.add(createProduct(2L, "Samsung Galaxy S24 Ultra 512GB", 31990000.0, 35990000.0, 15));
        demoProducts.add(createProduct(3L, "MacBook Pro M3 14 inch", 49990000.0, null, 5));
        demoProducts.add(createProduct(4L, "iPad Pro M2 12.9 inch WiFi", 28990000.0, 32990000.0, 8));
        demoProducts.add(createProduct(5L, "Apple Watch Series 9 GPS", 10990000.0, 12990000.0, 20));
        demoProducts.add(createProduct(6L, "AirPods Pro 2nd Gen", 5990000.0, 6990000.0, 30));
        demoProducts.add(createProduct(7L, "Samsung Galaxy Tab S9+", 23990000.0, 26990000.0, 12));
        demoProducts.add(createProduct(8L, "Xiaomi 14 Ultra 512GB", 24990000.0, 27990000.0, 18));
        
        adapter.updateData(demoProducts);
        totalItems = demoProducts.size();
        totalPages = 1;
        updateResultCount(demoProducts.size());
        updatePagination();
        showEmpty(false);
    }
    
    private Product createProduct(Long id, String name, Double price, Double originalPrice, Integer stock) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setPrice(price);
        product.setOriginalPrice(originalPrice);
        product.setStockQuantity(stock);
        return product;
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
