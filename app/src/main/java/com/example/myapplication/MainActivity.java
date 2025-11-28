package com.example.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.ProductAdapter;
import com.example.myapplication.api.RetrofitClient;
import com.example.myapplication.model.Category;
import com.example.myapplication.model.Product;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvCategories;
    private RecyclerView rvProducts;
    private CategoryAdapter categoryAdapter;
    private ProductAdapter productAdapter;
    private TextView tvUserName;
    private List<Product> productList = new ArrayList<>();
    
    private String currentCategoryId = null;
    private int currentPage = 1;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupUserInfo();
        setupCategories();
        setupProducts();
        
        loadCategories();
    }

    private void initializeViews() {
        rvCategories = findViewById(R.id.rvCategories);
        rvProducts = findViewById(R.id.rvProducts);
        tvUserName = findViewById(R.id.tvUserName);

        findViewById(R.id.homeBtn).setOnClickListener(v -> Toast.makeText(this, "Home Clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.profileBtn).setOnClickListener(v -> Toast.makeText(this, "Profile Clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.cartBtn).setOnClickListener(v -> Toast.makeText(this, "Cart Clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.supportBtn).setOnClickListener(v -> Toast.makeText(this, "Support Clicked", Toast.LENGTH_SHORT).show());
        findViewById(R.id.settingsBtn).setOnClickListener(v -> Toast.makeText(this, "Settings Clicked", Toast.LENGTH_SHORT).show());
    }

    private void setupUserInfo() {
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        String name = prefs.getString("name", "User");
        tvUserName.setText(name);
    }

    private void setupCategories() {
        rvCategories.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void setupProducts() {
        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        rvProducts.setLayoutManager(layoutManager);
        productAdapter = new ProductAdapter(this, productList);
        rvProducts.setAdapter(productAdapter);

        rvProducts.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if (!isLoading && !isLastPage) {
                    if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                            && firstVisibleItemPosition >= 0) {
                        loadProducts(false);
                    }
                }
            }
        });
    }

    private void loadCategories() {
        RetrofitClient.getApiService().getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Category> categories = response.body();
                    if (!categories.isEmpty()) {
                        // Set first category as default
                        currentCategoryId = categories.get(0).getId();
                        loadProducts(true);
                    }
                    
                    categoryAdapter = new CategoryAdapter(MainActivity.this, categories, category -> {
                        currentCategoryId = category.getId();
                        loadProducts(true);
                    });
                    rvCategories.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to load categories", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadProducts(boolean isFirstPage) {
        if (isLoading && !isFirstPage) return;
        isLoading = true;
        
        if (isFirstPage) {
            currentPage = 1;
            isLastPage = false;
            productList.clear();
            productAdapter.notifyDataSetChanged();
        }

        if (currentCategoryId == null) {
            isLoading = false;
            return;
        }

        android.util.Log.d("MainActivity", "Loading products for category: " + currentCategoryId + ", page: " + currentPage);

        RetrofitClient.getApiService().getProductsByCategory(currentCategoryId, currentPage, 10).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                isLoading = false;
                if (response.isSuccessful() && response.body() != null) {
                    List<Product> newProducts = response.body();
                    android.util.Log.d("MainActivity", "Loaded " + newProducts.size() + " products");
                    if (newProducts.isEmpty()) {
                        isLastPage = true;
                    } else {
                        productAdapter.addProducts(newProducts);
                        currentPage++;
                    }
                } else {
                    android.util.Log.e("MainActivity", "Error loading products: " + response.code() + " " + response.message());
                    try {
                        android.util.Log.e("MainActivity", "Error body: " + response.errorBody().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(MainActivity.this, "Failed to load products: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                isLoading = false;
                android.util.Log.e("MainActivity", "Failure loading products", t);
                Toast.makeText(MainActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}