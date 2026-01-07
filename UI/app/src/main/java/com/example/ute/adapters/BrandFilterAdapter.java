package com.example.ute.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ute.R;
import com.example.ute.models.Brand;
import java.util.List;

public class BrandFilterAdapter extends RecyclerView.Adapter<BrandFilterAdapter.BrandViewHolder> {
    
    private List<Brand> brands;
    private OnBrandSelectedListener listener;
    private int selectedPosition = -1;
    
    public interface OnBrandSelectedListener {
        void onBrandSelected(Brand brand);
    }
    
    public BrandFilterAdapter(List<Brand> brands, OnBrandSelectedListener listener) {
        this.brands = brands;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public BrandViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_brand_filter, parent, false);
        return new BrandViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull BrandViewHolder holder, int position) {
        Brand brand = brands.get(position);
        holder.bind(brand, position == selectedPosition);
    }
    
    @Override
    public int getItemCount() {
        return brands != null ? brands.size() : 0;
    }
    
    public void setSelectedPosition(int position) {
        int oldPosition = selectedPosition;
        selectedPosition = position;
        if (oldPosition != -1) {
            notifyItemChanged(oldPosition);
        }
        if (position != -1) {
            notifyItemChanged(position);
        }
    }
    
    class BrandViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBrandName;
        
        BrandViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBrandName = itemView.findViewById(R.id.tvBrandName);
        }
        
        void bind(Brand brand, boolean isSelected) {
            tvBrandName.setText(brand.getName());
            
            if (isSelected) {
                tvBrandName.setBackgroundResource(R.drawable.bg_brand_selected);
                tvBrandName.setTextColor(itemView.getContext().getColor(R.color.white));
            } else {
                tvBrandName.setBackgroundResource(R.drawable.bg_brand_unselected);
                tvBrandName.setTextColor(itemView.getContext().getColor(R.color.text_primary));
            }
            
            itemView.setOnClickListener(v -> {
                setSelectedPosition(getAdapterPosition());
                if (listener != null) {
                    listener.onBrandSelected(brand);
                }
            });
        }
    }
}
