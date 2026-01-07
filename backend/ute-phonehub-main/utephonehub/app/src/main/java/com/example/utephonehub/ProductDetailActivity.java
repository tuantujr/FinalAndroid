package com.example.utephonehub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.utephonehub.model.Product;
import com.example.utephonehub.viewmodel.ProductViewModel;

public class ProductDetailActivity extends AppCompatActivity {
    private ProductViewModel productViewModel;
    private ImageView productImage;
    private TextView productName;
    private TextView productPrice;
    private TextView productDescription;
    private TextView productCategory;
    private TextView productBrand;
    private TextView productStock;
    private Button addToCartButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Get product ID from intent
        Long productId = getIntent().getLongExtra("productId", -1);

        if (productId == -1) {
            Toast.makeText(this, "Invalid product ID", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize views
        productImage = findViewById(R.id.productImage);
        productName = findViewById(R.id.productName);
        productPrice = findViewById(R.id.productPrice);
        productDescription = findViewById(R.id.productDescription);
        productCategory = findViewById(R.id.productCategory);
        productBrand = findViewById(R.id.productBrand);
        productStock = findViewById(R.id.productStock);
        addToCartButton = findViewById(R.id.addToCartButton);

        // Initialize ViewModel
        productViewModel = new ViewModelProvider(this).get(ProductViewModel.class);

        // Load product detail
        productViewModel.loadProductDetail(productId);

        // Observe product detail
        productViewModel.getProductDetail().observe(this, product -> {
            if (product != null) {
                displayProduct(product);
            }
        });

        // Setup Add to Cart button
        addToCartButton.setOnClickListener(v -> {
            Toast.makeText(this, "Added to cart!", Toast.LENGTH_SHORT).show();
        });
    }

    private void displayProduct(Product product) {
        productName.setText(product.getName());
        productPrice.setText(String.format("$%.2f", product.getPrice()));
        productDescription.setText(product.getDescription());
        productStock.setText(String.format("Stock: %d", product.getStockQuantity()));

        if (product.getCategory() != null) {
            productCategory.setText(String.format("Category: %s", product.getCategory().getName()));
        }

        if (product.getBrand() != null) {
            productBrand.setText(String.format("Brand: %s", product.getBrand().getName()));
        }

        Glide.with(this)
            .load(product.getThumbnailUrl())
            .into(productImage);
    }
}
