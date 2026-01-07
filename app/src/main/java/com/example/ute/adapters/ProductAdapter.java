package com.example.ute.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ute.R;
import com.example.ute.models.Product;
import com.example.ute.utils.PriceFormatter;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    
    private List<Product> products;
    private OnProductClickListener listener;
    
    public interface OnProductClickListener {
        void onProductClick(Product product);
        void onAddToCartClick(Product product);
    }
    
    public ProductAdapter(List<Product> products, OnProductClickListener listener) {
        this.products = products;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = products.get(position);
        holder.bind(product);
    }
    
    @Override
    public int getItemCount() {
        return products != null ? products.size() : 0;
    }
    
    public void updateData(List<Product> newProducts) {
        this.products = newProducts;
        notifyDataSetChanged();
    }
    
    public void addData(List<Product> newProducts) {
        int startPosition = products.size();
        products.addAll(newProducts);
        notifyItemRangeInserted(startPosition, newProducts.size());
    }
    
    class ProductViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView cardProduct;
        private ImageView ivProductImage;
        private ImageButton btnFavorite;
        private TextView tvCategory;
        private TextView tvProductName;
        private TextView tvProductPrice;
        private TextView tvStock;
        private MaterialButton btnAddToCart;
        private ImageButton btnQuickView;
        
        ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            cardProduct = itemView.findViewById(R.id.cardProduct);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvStock = itemView.findViewById(R.id.tvStock);
            btnAddToCart = itemView.findViewById(R.id.btnAddToCart);
            btnQuickView = itemView.findViewById(R.id.btnQuickView);
        }
        
        void bind(Product product) {
            tvProductName.setText(product.getName());
            tvProductPrice.setText(PriceFormatter.format(product.getPrice()));
            
            if (product.getCategory() != null) {
                tvCategory.setText(product.getCategory().getName());
                tvCategory.setVisibility(View.VISIBLE);
            } else {
                tvCategory.setVisibility(View.GONE);
            }
            
            if (product.getStockQuantity() != null && product.getStockQuantity() > 0) {
                tvStock.setText(itemView.getContext().getString(R.string.stock_remaining, product.getStockQuantity()));
                tvStock.setTextColor(itemView.getContext().getColor(R.color.success));
            } else {
                tvStock.setText(R.string.out_of_stock);
                tvStock.setTextColor(itemView.getContext().getColor(R.color.error));
            }
            
            // Load image
            if (product.getThumbnailUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(product.getThumbnailUrl())
                        .placeholder(R.drawable.placeholder_product)
                        .error(R.drawable.placeholder_product)
                        .centerCrop()
                        .into(ivProductImage);
            }
            
            // Click listeners
            cardProduct.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProductClick(product);
                }
            });
            
            btnAddToCart.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onAddToCartClick(product);
                }
            });
            
            btnFavorite.setOnClickListener(v -> {
                // Toggle favorite
            });
            
            btnQuickView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onProductClick(product);
                }
            });
        }
    }
}
