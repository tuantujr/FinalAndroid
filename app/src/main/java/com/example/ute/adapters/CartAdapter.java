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
import com.example.ute.models.CartItem;
import com.example.ute.utils.PriceFormatter;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    
    private List<CartItem> items;
    private OnCartItemListener listener;
    
    public interface OnCartItemListener {
        void onQuantityChanged(CartItem item, int newQuantity);
        void onRemoveItem(CartItem item);
    }
    
    public CartAdapter(List<CartItem> items, OnCartItemListener listener) {
        this.items = items;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = items.get(position);
        holder.bind(item);
    }
    
    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }
    
    public void updateData(List<CartItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }
    
    class CartViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProductImage;
        private TextView tvProductName;
        private TextView tvProductPrice;
        private TextView tvSubtotal;
        private TextView tvQuantity;
        private ImageButton btnDecrease, btnIncrease, btnRemove;
        
        CartViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
            tvSubtotal = itemView.findViewById(R.id.tvSubtotal);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnRemove = itemView.findViewById(R.id.btnRemove);
        }
        
        void bind(CartItem item) {
            if (item.getProduct() != null) {
                tvProductName.setText(item.getProduct().getName());
                tvProductPrice.setText(PriceFormatter.format(item.getProduct().getPrice()));
                
                if (item.getProduct().getThumbnailUrl() != null) {
                    Glide.with(itemView.getContext())
                            .load(item.getProduct().getThumbnailUrl())
                            .placeholder(R.drawable.placeholder_product)
                            .into(ivProductImage);
                }
            }
            
            tvQuantity.setText(String.valueOf(item.getQuantity()));
            tvSubtotal.setText(PriceFormatter.format(item.getSubtotal()));
            
            btnDecrease.setOnClickListener(v -> {
                if (item.getQuantity() > 1) {
                    listener.onQuantityChanged(item, item.getQuantity() - 1);
                }
            });
            
            btnIncrease.setOnClickListener(v -> {
                listener.onQuantityChanged(item, item.getQuantity() + 1);
            });
            
            btnRemove.setOnClickListener(v -> {
                listener.onRemoveItem(item);
            });
        }
    }
}
