package com.example.ute.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.models.Address;

import java.util.List;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.AddressViewHolder> {
    
    private List<Address> addresses;
    private OnAddressClickListener clickListener;
    private OnAddressDeleteListener deleteListener;
    
    public interface OnAddressClickListener {
        void onAddressClick(Address address);
    }
    
    public interface OnAddressDeleteListener {
        void onAddressDelete(Address address);
    }
    
    public AddressAdapter(List<Address> addresses, OnAddressClickListener clickListener, OnAddressDeleteListener deleteListener) {
        this.addresses = addresses;
        this.clickListener = clickListener;
        this.deleteListener = deleteListener;
    }
    
    @NonNull
    @Override
    public AddressViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_address, parent, false);
        return new AddressViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull AddressViewHolder holder, int position) {
        Address address = addresses.get(position);
        holder.bind(address, clickListener, deleteListener);
    }
    
    @Override
    public int getItemCount() {
        return addresses.size();
    }
    
    static class AddressViewHolder extends RecyclerView.ViewHolder {
        private TextView tvRecipientName;
        private TextView tvPhoneNumber;
        private TextView tvFullAddress;
        private TextView tvDefaultBadge;
        private Button btnEdit;
        private ImageButton btnDelete;
        
        public AddressViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRecipientName = itemView.findViewById(R.id.tvRecipientName);
            tvPhoneNumber = itemView.findViewById(R.id.tvPhoneNumber);
            tvFullAddress = itemView.findViewById(R.id.tvFullAddress);
            tvDefaultBadge = itemView.findViewById(R.id.tvDefaultBadge);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
        
        public void bind(Address address, OnAddressClickListener clickListener, OnAddressDeleteListener deleteListener) {
            tvRecipientName.setText(address.getRecipientName());
            tvPhoneNumber.setText(address.getPhoneNumber());
            tvFullAddress.setText(address.getFullAddress());
            
            // Show default badge
            if (address.getIsDefault() != null && address.getIsDefault()) {
                tvDefaultBadge.setVisibility(View.VISIBLE);
                tvDefaultBadge.setText("Địa chỉ mặc định");
            } else {
                tvDefaultBadge.setVisibility(View.GONE);
            }
            
            btnEdit.setOnClickListener(v -> {
                if (clickListener != null) {
                    clickListener.onAddressClick(address);
                }
            });
            
            btnDelete.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onAddressDelete(address);
                }
            });
        }
    }
}
