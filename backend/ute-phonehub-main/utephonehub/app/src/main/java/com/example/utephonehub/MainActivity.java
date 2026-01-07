package com.example.utephonehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.utephonehub.adapter.ProductAdapter;
import com.example.utephonehub.model.Product;
import com.example.utephonehub.viewmodel.ProductViewModel;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {
    private ProductViewModel productViewModel;
    private ProductAdapter productAdapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefresh;
    private int currentPage = 0;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        productAdapter = new ProductAdapter(this);
        recyclerView.setAdapter(productAdapter);

        // Setup SwipeRefresh
        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(() -> {
            currentPage = 0;
            loadProducts();
        });

        // Setup RecyclerView scroll listener for pagination
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int totalItemCount = layoutManager.getItemCount();
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                    if (!isLoading && lastVisibleItem == totalItemCount - 1) {
                        loadMoreProducts();
                    }
                }
            }
        });

        // Observe products
        productViewModel.getProducts().observe(this, productPageData -> {
            if (productPageData != null && productPageData.getContent() != null) {
                if (currentPage == 0) {
                    productAdapter.setProducts(productPageData.getContent());
                } else {
                    productAdapter.addProducts(productPageData.getContent());
                }
                swipeRefresh.setRefreshing(false);
                isLoading = false;
            }
        });

        // Initial load
        loadProducts();
    }

    private void loadProducts() {
        if (!isLoading) {
            isLoading = true;
            productViewModel.loadProducts(currentPage, 10);
        }
    }

    private void loadMoreProducts() {
        currentPage++;
        loadProducts();
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("productId", product.getId());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                currentPage = 0;
                productViewModel.searchProducts(query, currentPage, 10);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return true;
    }
}