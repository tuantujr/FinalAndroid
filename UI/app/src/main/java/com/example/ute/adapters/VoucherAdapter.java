package com.example.ute.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.models.response.VoucherResponse;
import com.google.android.material.card.MaterialCardView;

import java.text.DecimalFormat;
import java.util.List;

public class VoucherAdapter extends RecyclerView.Adapter<VoucherAdapter.VoucherViewHolder> {
    
    private List<VoucherResponse> vouchers;
    private OnVoucherClickListener listener;
    
    public interface OnVoucherClickListener {
        void onVoucherClick(VoucherResponse voucher);
    }
    
    public VoucherAdapter(List<VoucherResponse> vouchers, OnVoucherClickListener listener) {
        this.vouchers = vouchers;
        this.listener = listener;
    }
    
    @NonNull
    @Override
    public VoucherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_voucher, parent, false);
        return new VoucherViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull VoucherViewHolder holder, int position) {
        VoucherResponse voucher = vouchers.get(position);
        holder.bind(voucher);
    }
    
    @Override
    public int getItemCount() {
        return vouchers != null ? vouchers.size() : 0;
    }
    
    public void updateData(List<VoucherResponse> newVouchers) {
        this.vouchers = newVouchers;
        notifyDataSetChanged();
    }
    
    class VoucherViewHolder extends RecyclerView.ViewHolder {
        private MaterialCardView cardVoucher;
        private TextView tvVoucherCode;
        private TextView tvVoucherDescription;
        private TextView tvDiscountAmount;
        private TextView tvVoucherCondition;
        private TextView tvExpiryDate;
        private TextView tvStatus;
        
        VoucherViewHolder(@NonNull View itemView) {
            super(itemView);
            cardVoucher = itemView.findViewById(R.id.cardVoucher);
            tvVoucherCode = itemView.findViewById(R.id.tvVoucherCode);
            tvVoucherDescription = itemView.findViewById(R.id.tvVoucherDescription);
            tvDiscountAmount = itemView.findViewById(R.id.tvDiscountAmount);
            tvVoucherCondition = itemView.findViewById(R.id.tvVoucherCondition);
            tvExpiryDate = itemView.findViewById(R.id.tvExpiryDate);
            tvStatus = itemView.findViewById(R.id.tvStatus);
        }
        
        void bind(VoucherResponse voucher) {
            tvVoucherCode.setText(voucher.getCode());
            
            if (voucher.getDescription() != null && !voucher.getDescription().isEmpty()) {
                tvVoucherDescription.setText(voucher.getDescription());
                tvVoucherDescription.setVisibility(View.VISIBLE);
            } else {
                tvVoucherDescription.setVisibility(View.GONE);
            }
            
            // Format discount display
            String discountText = formatDiscount(voucher);
            tvDiscountAmount.setText(discountText);
            
            // Format condition
            String conditionText = formatCondition(voucher);
            tvVoucherCondition.setText(conditionText);
            
            // Format expiry date
            if (voucher.getEndDate() != null) {
                String expiryText = "Hết hạn: " + voucher.getEndDate();
                tvExpiryDate.setText(expiryText);
                tvExpiryDate.setVisibility(View.VISIBLE);
            } else {
                tvExpiryDate.setVisibility(View.GONE);
            }
            
            // Format status
            if (voucher.getIsActive() != null) {
                if (voucher.getIsActive()) {
                    tvStatus.setText("Có sẵn");
                    tvStatus.setTextColor(itemView.getContext().getColor(R.color.color_success));
                } else {
                    tvStatus.setText("Hết hạn");
                    tvStatus.setTextColor(itemView.getContext().getColor(R.color.color_error));
                }
            }
            
            // Usage info
            if (voucher.getUsageLimit() != null && voucher.getUsedCount() != null) {
                String usageText = "Còn lại: " + (voucher.getUsageLimit() - voucher.getUsedCount()) + "/" + voucher.getUsageLimit();
                tvVoucherCondition.append("\n" + usageText);
            }
            
            cardVoucher.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onVoucherClick(voucher);
                }
            });
        }
        
        private String formatDiscount(VoucherResponse voucher) {
            if (voucher.getDiscountType() == null || voucher.getDiscountValue() == null) {
                return "Khuyến mãi";
            }
            
            DecimalFormat df = new DecimalFormat("#,###");
            
            if ("PERCENTAGE".equalsIgnoreCase(voucher.getDiscountType())) {
                return "Giảm " + voucher.getDiscountValue() + "%";
            } else if ("FIXED_AMOUNT".equalsIgnoreCase(voucher.getDiscountType())) {
                return "Giảm " + df.format(voucher.getDiscountValue().longValue()) + "đ";
            }
            
            return "Khuyến mãi";
        }
        
        private String formatCondition(VoucherResponse voucher) {
            StringBuilder condition = new StringBuilder();
            
            if (voucher.getMinOrderValue() != null && voucher.getMinOrderValue() > 0) {
                DecimalFormat df = new DecimalFormat("#,###");
                condition.append("Tối thiểu: ")
                        .append(df.format(voucher.getMinOrderValue().longValue()))
                        .append("đ");
            } else {
                condition.append("Không có điều kiện");
            }
            
            return condition.toString();
        }
    }
}
