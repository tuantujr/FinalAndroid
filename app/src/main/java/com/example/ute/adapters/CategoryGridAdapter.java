package com.example.ute.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ute.R;
import com.example.ute.models.Category;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.CategoryViewHolder> {
    
    private List<Category> categories;
    private OnCategoryClickListener listener;
    
    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }
    
    public CategoryGridAdapter(List<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_grid, parent, false);
        return new CategoryViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.bind(category);
    }
    
    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }
    
    public void updateData(List<Category> newCategories) {
        this.categories = newCategories;
        notifyDataSetChanged();
    }
    
    class CategoryViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView cardCategory;
        private ImageView ivCategoryImage;
        private TextView tvCategoryName;
        private TextView tvProductCount;
        
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            cardCategory = itemView.findViewById(R.id.cardCategory);
            ivCategoryImage = itemView.findViewById(R.id.ivCategoryImage);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
            tvProductCount = itemView.findViewById(R.id.tvProductCount);
        }
        
        void bind(Category category) {
            tvCategoryName.setText(category.getName());
            
            if (category.getProductCount() != null) {
                tvProductCount.setText(category.getProductCount() + " sản phẩm");
                tvProductCount.setVisibility(View.VISIBLE);
            } else {
                tvProductCount.setVisibility(View.GONE);
            }
            
            if (category.getImageUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(category.getImageUrl())
                        .placeholder(R.drawable.placeholder_category)
                        .centerCrop()
                        .into(ivCategoryImage);
            }
            
            cardCategory.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCategoryClick(category);
                }
            });
        }
    }
}
