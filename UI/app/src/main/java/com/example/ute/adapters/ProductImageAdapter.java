package com.example.ute.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ute.R;
import com.example.ute.models.ProductImage;

import java.util.ArrayList;
import java.util.List;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ImageViewHolder> {
    
    private List<?> images; // Can be List<ProductImage> or List<String>
    private String baseUrl;
    
    public ProductImageAdapter(List<?> images) {
        this.images = images;
        this.baseUrl = "http://10.0.2.2:8080/"; // Default emulator URL
    }
    
    public ProductImageAdapter(List<?> images, String baseUrl) {
        this.images = images;
        this.baseUrl = baseUrl != null ? baseUrl : "http://10.0.2.2:8080/";
    }
    
    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product_image, parent, false);
        return new ImageViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Object imageObj = images.get(position);
        holder.bind(imageObj, baseUrl);
    }
    
    @Override
    public int getItemCount() {
        return images != null ? images.size() : 0;
    }
    
    static class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProductImage;
        
        ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
        }
        
        void bind(Object imageObj, String baseUrl) {
            String imageUrl = null;
            
            // Handle both ProductImage objects and String URLs
            if (imageObj instanceof ProductImage) {
                imageUrl = ((ProductImage) imageObj).getImageUrl();
            } else if (imageObj instanceof String) {
                imageUrl = (String) imageObj;
            }
            
            if (imageUrl == null) {
                return;
            }
            
            // If URL is relative, prepend base URL
            if (!imageUrl.startsWith("http")) {
                if (!imageUrl.startsWith("/")) {
                    imageUrl = "/" + imageUrl;
                }
                imageUrl = baseUrl.replaceAll("/$", "") + imageUrl;
            }
            
            Glide.with(itemView.getContext())
                    .load(imageUrl)
                    .placeholder(R.drawable.placeholder_product)
                    .error(R.drawable.placeholder_product)
                    .centerInside()
                    .into(ivProductImage);
            
            // Add click listener for full-screen image view
            ivProductImage.setOnClickListener(v -> {
                // TODO: Show full-screen image viewer
            });
        }
    }
}
