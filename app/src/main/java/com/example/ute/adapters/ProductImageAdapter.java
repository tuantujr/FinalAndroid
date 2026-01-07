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

import java.util.List;

public class ProductImageAdapter extends RecyclerView.Adapter<ProductImageAdapter.ImageViewHolder> {
    
    private List<ProductImage> images;
    
    public ProductImageAdapter(List<ProductImage> images) {
        this.images = images;
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
        ProductImage image = images.get(position);
        holder.bind(image);
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
        
        void bind(ProductImage image) {
            Glide.with(itemView.getContext())
                    .load(image.getImageUrl())
                    .placeholder(R.drawable.placeholder_product)
                    .into(ivProductImage);
        }
    }
}
