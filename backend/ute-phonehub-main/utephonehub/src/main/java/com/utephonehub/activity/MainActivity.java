package com.utephonehub.activity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import com.utephonehub.adapter.ProductAdapter;
import com.utephonehub.databinding.ActivityMainBinding;
import com.utephonehub.model.Product;
import com.utephonehub.viewmodel.ProductViewModel;

public class MainActivity extends AppCompatActivity implements ProductAdapter.OnProductClickListener {

    private ActivityMainBinding binding;
    private ProductViewModel viewModel;
    private ProductAdapter adapter;
    private int currentPage = 1;
    private boolean isLoading = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);
        adapter = new ProductAdapter(this);

        setupRecyclerView();
        setupSwipeRefresh();
        loadProducts();
    }

    private void setupRecyclerView() {
        binding.productsRecyclerView.setAdapter(adapter);
        binding.productsRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        // Load more when scrolling to bottom
        binding.productsRecyclerView.addOnScrollListener(new androidx.recyclerview.widget.RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(androidx.recyclerview.widget.RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null) {
                    int lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                    int totalItemCount = layoutManager.getItemCount();

                    if (!isLoading && lastVisibleItem >= totalItemCount - 5) {
                        currentPage++;
                        loadProducts();
                    }
                }
            }
        });
    }

    private void setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener(() -> {
            currentPage = 1;
            adapter.setProducts(java.util.Collections.emptyList());
            loadProducts();
        });
    }

    private void loadProducts() {
        isLoading = true;
        viewModel.loadProducts(currentPage, 10);
        viewModel.getProducts(currentPage, 10).observe(this, products -> {
            isLoading = false;
            binding.swipeRefreshLayout.setRefreshing(false);
            if (products != null && !products.isEmpty()) {
                if (currentPage == 1) {
                    adapter.setProducts(products);
                } else {
                    adapter.addProducts(products);
                }
            }
        });
    }

    @Override
    public void onProductClick(Product product) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("productId", product.getId());
        intent.putExtra("productName", product.getName());
        startActivity(intent);
    }
}
