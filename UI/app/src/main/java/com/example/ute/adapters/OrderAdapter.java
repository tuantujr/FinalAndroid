package com.example.ute.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.ute.R;
import com.example.ute.models.Order;
import com.example.ute.utils.PriceFormatter;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    
    private List<Order> orders;
    private OnOrderClickListener listener;
    private Context context;
    
    public interface OnOrderClickListener {
        void onOrderClick(Order order);
    }
    
    public OrderAdapter(List<Order> orders, OnOrderClickListener listener) {
        this.orders = orders;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        Order order = orders.get(position);
        holder.bind(order);
    }
    
    @Override
    public int getItemCount() {
        return orders.size();
    }
    
    public void updateData(List<Order> newOrders) {
        this.orders = newOrders;
        notifyDataSetChanged();
    }
    
    public class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvOrderCode;
        private TextView tvOrderStatus;
        private TextView tvOrderDate;
        private TextView tvOrderTotal;
        private Button btnViewDetail;
        
        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOrderCode = itemView.findViewById(R.id.tvOrderCode);
            tvOrderStatus = itemView.findViewById(R.id.tvOrderStatus);
            tvOrderDate = itemView.findViewById(R.id.tvOrderDate);
            tvOrderTotal = itemView.findViewById(R.id.tvOrderTotal);
            btnViewDetail = itemView.findViewById(R.id.btnViewDetail);
        }
        
        public void bind(Order order) {
            tvOrderCode.setText("Mã đơn: " + order.getOrderCode());
            tvOrderStatus.setText(getStatusText(order.getStatus()));
            tvOrderStatus.setTextColor(getStatusColor(order.getStatus()));
            
            // Format date
            String dateStr = "";
            if (order.getCreatedAt() != null && !order.getCreatedAt().isEmpty()) {
                try {
                    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
                    SimpleDateFormat outputFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
                    dateStr = outputFormat.format(inputFormat.parse(order.getCreatedAt()));
                } catch (Exception e) {
                    dateStr = order.getCreatedAt();
                }
            }
            tvOrderDate.setText(dateStr);
            
            // Display total amount
            tvOrderTotal.setText(PriceFormatter.format(order.getTotalAmount() != null ? order.getTotalAmount() : 0));
            
            // View detail button click
            btnViewDetail.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onOrderClick(order);
                }
            });
            
            // Card click listener
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onOrderClick(order);
                }
            });
        }
        
        private String getStatusText(String status) {
            if (status == null) return "Không xác định";
            
            switch (status.toUpperCase()) {
                case "PENDING":
                    return "Chờ xác nhận";
                case "CONFIRMED":
                    return "Đã xác nhận";
                case "SHIPPING":
                    return "Đang giao";
                case "DELIVERED":
                    return "Đã giao";
                case "CANCELLED":
                    return "Đã hủy";
                case "RETURNED":
                    return "Đã trả";
                default:
                    return status;
            }
        }
        
        private int getStatusColor(String status) {
            // Return black text color for all statuses (white background for all badges)
            return context.getColor(R.color.text_primary);
        }
    }
}
