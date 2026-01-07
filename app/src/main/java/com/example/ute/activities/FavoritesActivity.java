package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.adapters.ProductAdapter;
import com.example.ute.models.Product;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private MaterialToolbar toolbar;
    private RecyclerView rvFavorites;
    private LinearLayout layoutEmpty;
    private ProgressBar progressBar;
    private MaterialButton btnBrowseProducts;
    
    private ProductAdapter adapter;
    private List<Product> favoriteProducts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        
        initViews();
        setupToolbar();
        setupRecyclerView();
        loadFavorites();
    }
    
    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        rvFavorites = findViewById(R.id.rvFavorites);
        layoutEmpty = findViewById(R.id.layoutEmpty);
        progressBar = findViewById(R.id.progressBar);
        btnBrowseProducts = findViewById(R.id.btnBrowseProducts);
        
        btnBrowseProducts.setOnClickListener(v -> {
            finish(); // Go back to main activity
        });
    }
    
    private void setupToolbar() {
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
    
    private void setupRecyclerView() {
        adapter = new ProductAdapter(favoriteProducts, this);
        rvFavorites.setLayoutManager(new GridLayoutManager(this, 2));
        rvFavorites.setAdapter(adapter);
    }
    
    private void loadFavorites() {
        // Load demo favorites
        favoriteProducts.clear();
        favoriteProducts.add(createProduct(1L, "iPhone 15 Pro Max 256GB", 34990000.0, 38990000.0, 10));
        favoriteProducts.add(createProduct(2L, "Samsung Galaxy S24 Ultra", 31990000.0, 35990000.0, 15));
        favoriteProducts.add(createProduct(3L, "AirPods Pro 2nd Gen", 5990000.0, 6990000.0, 30));
        favoriteProducts.add(createProduct(4L, "Apple Watch Series 9", 10990000.0, 12990000.0, 20));
        
        if (favoriteProducts.isEmpty()) {
            showEmpty();
        } else {
            showFavorites();
        }
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
    
    private void showEmpty() {
        layoutEmpty.setVisibility(View.VISIBLE);
        rvFavorites.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }
    
    private void showFavorites() {
        layoutEmpty.setVisibility(View.GONE);
        rvFavorites.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        adapter.updateData(favoriteProducts);
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
