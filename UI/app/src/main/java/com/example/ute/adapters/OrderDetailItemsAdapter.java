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
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.OrderItem;
import com.example.ute.models.response.ProductDetailResponse;
import com.example.ute.utils.PriceFormatter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderDetailItemsAdapter extends RecyclerView.Adapter<OrderDetailItemsAdapter.ItemViewHolder> {
    
    private List<OrderItem> items = new ArrayList<>();
    private ApiService apiService;
    
    public OrderDetailItemsAdapter() {
        this.apiService = ApiClient.getApiService();
    }
    
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_detail_product, parent, false);
        return new ItemViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.bind(items.get(position));
    }
    
    @Override
    public int getItemCount() {
        return items.size();
    }
    
    public void updateData(List<OrderItem> newItems) {
        this.items = newItems;
        notifyDataSetChanged();
    }
    
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivProductImage;
        private TextView tvProductName;
        private TextView tvProductQuantity;
        private TextView tvProductPrice;
        
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProductImage = itemView.findViewById(R.id.ivProductImage);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvProductPrice = itemView.findViewById(R.id.tvProductPrice);
        }
        
        public void bind(OrderItem item) {
            tvProductName.setText(item.getProductName());
            tvProductQuantity.setText("Số lượng: " + item.getQuantity());
            
            Double itemPrice = (item.getPrice() != null ? item.getPrice() : 0) * item.getQuantity();
            tvProductPrice.setText(PriceFormatter.format(itemPrice));
            
            // Load product image from API using productId
            if (item.getProductId() != null) {
                apiService.getProductDetail(item.getProductId()).enqueue(new Callback<ProductDetailResponse>() {
                    @Override
                    public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null) {
                            String imageUrl = response.body().getData().getThumbnailUrl();
                            if (imageUrl != null && !imageUrl.isEmpty()) {
                                Glide.with(itemView.getContext())
                                        .load(imageUrl)
                                        .placeholder(R.drawable.banner_placeholder)
                                        .error(R.drawable.banner_placeholder)
                                        .into(ivProductImage);
                            }
                        }
                    }
                    
                    @Override
                    public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                        // Load placeholder on failure
                    }
                });
            }
        }
    }
}
