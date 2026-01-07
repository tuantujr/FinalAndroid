package com.example.ute.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpecificationAdapter extends RecyclerView.Adapter<SpecificationAdapter.SpecificationViewHolder> {
    
    private List<Map.Entry<String, Object>> specifications = new ArrayList<>();
    
    public SpecificationAdapter(Map<String, Object> specs) {
        if (specs != null) {
            this.specifications.addAll(specs.entrySet());
        }
    }
    
    @NonNull
    @Override
    public SpecificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_specification, parent, false);
        return new SpecificationViewHolder(view);
    }
    
    @Override
    public void onBindViewHolder(@NonNull SpecificationViewHolder holder, int position) {
        Map.Entry<String, Object> entry = specifications.get(position);
        String key = entry.getKey();
        Object value = entry.getValue();
        
        // Format the key (convert camelCase/snake_case to readable format)
        String displayKey = formatKey(key);
        holder.tvSpecKey.setText(displayKey);
        
        // Format the value
        String displayValue = formatValue(value);
        holder.tvSpecValue.setText(displayValue);
    }
    
    @Override
    public int getItemCount() {
        return specifications.size();
    }
    
    private String formatKey(String key) {
        // Convert camelCase to readable text: "cpu" -> "CPU", "camera_front" -> "Front Camera"
        String result = key
                .replaceAll("([a-z])([A-Z])", "$1 $2") // Handle camelCase
                .replaceAll("_", " "); // Replace underscores
        
        // Capitalize first letter of each word
        Pattern pattern = Pattern.compile("\\b([a-z])");
        Matcher matcher = pattern.matcher(result);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        result = sb.toString();
        
        // Replace common abbreviations
        result = result.replaceAll("(?i)\\bos\\b", "OS")
                .replaceAll("(?i)\\bcpu\\b", "CPU")
                .replaceAll("(?i)\\bram\\b", "RAM");
        
        return result;
    }
    
    private String formatValue(Object value) {
        if (value == null) {
            return "N/A";
        }
        
        if (value instanceof java.util.List) {
            // Join list items with comma
            java.util.List<?> list = (java.util.List<?>) value;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < list.size(); i++) {
                if (i > 0) sb.append(", ");
                sb.append(list.get(i).toString());
            }
            return sb.toString();
        }
        
        return value.toString();
    }
    
    static class SpecificationViewHolder extends RecyclerView.ViewHolder {
        private TextView tvSpecKey;
        private TextView tvSpecValue;
        
        public SpecificationViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSpecKey = itemView.findViewById(R.id.tvSpecKey);
            tvSpecValue = itemView.findViewById(R.id.tvSpecValue);
        }
    }
}
