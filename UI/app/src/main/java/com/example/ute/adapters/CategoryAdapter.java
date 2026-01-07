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

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    
    private List<Category> categories;
    private OnCategoryClickListener listener;
    
    public interface OnCategoryClickListener {
        void onCategoryClick(Category category);
    }
    
    public CategoryAdapter(List<Category> categories, OnCategoryClickListener listener) {
        this.categories = categories;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_horizontal, parent, false);
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
        private ImageView ivCategoryIcon;
        private TextView tvCategoryName;
        
        CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategoryIcon = itemView.findViewById(R.id.ivCategoryIcon);
            tvCategoryName = itemView.findViewById(R.id.tvCategoryName);
        }
        
        void bind(Category category) {
            tvCategoryName.setText(category.getName());
            
            if (category.getIconUrl() != null) {
                Glide.with(itemView.getContext())
                        .load(category.getIconUrl())
                        .placeholder(R.drawable.ic_category)
                        .into(ivCategoryIcon);
            }
            
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onCategoryClick(category);
                }
            });
        }
    }
}
