package com.utephonehub.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.bumptech.glide.Glide;
import com.utephonehub.databinding.ActivityProductDetailBinding;
import com.utephonehub.model.Product;
import com.utephonehub.viewmodel.ProductViewModel;

public class ProductDetailActivity extends AppCompatActivity {

    private ActivityProductDetailBinding binding;
    private ProductViewModel viewModel;
    private Long productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProductDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        productId = getIntent().getLongExtra("productId", 0);
        String productName = getIntent().getStringExtra("productName");

        binding.backButton.setOnClickListener(v -> finish());

        loadProductDetails();
        setupAddToCartButton();
    }

    private void loadProductDetails() {
        if (productId > 0) {
            viewModel.loadProductDetail(productId);
            viewModel.getProductDetail(productId).observe(this, product -> {
                if (product != null) {
                    displayProduct(product);
                }
            });
        }
    }

    private void displayProduct(Product product) {
        binding.detailProductName.setText(product.getName());
        binding.detailProductPrice.setText(String.format("$%.2f", product.getPrice()));
        binding.detailProductDescription.setText(product.getDescription());
        binding.detailProductStock.setText(String.valueOf(product.getStockQuantity()) + " items");

        if (product.getBrand() != null) {
            binding.detailProductBrand.setText("Brand: " + product.getBrand().getName());
        }

        if (product.getCategory() != null) {
            binding.detailProductCategory.setText("Category: " + product.getCategory().getName());
        }

        if (product.getThumbnailUrl() != null) {
            Glide.with(this)
                    .load(product.getThumbnailUrl())
                    .centerCrop()
                    .into(binding.detailProductImage);
        }
    }

    private void setupAddToCartButton() {
        binding.addToCartButton.setOnClickListener(v -> {
            // TODO: Implement add to cart functionality
            // This will be implemented in the next phase
            showMessage("Product added to cart!");
        });
    }

    private void showMessage(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }
}
