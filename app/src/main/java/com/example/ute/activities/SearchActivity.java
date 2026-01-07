package com.example.ute.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ute.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;

public class SearchActivity extends AppCompatActivity {
    
    private TextInputEditText etSearch;
    private ChipGroup chipGroupHistory;
    private ChipGroup chipGroupSuggestions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        
        initViews();
        setupToolbar();
        setupSearchHistory();
        setupSuggestions();
    }
    
    private void initViews() {
        etSearch = findViewById(R.id.etSearch);
        chipGroupHistory = findViewById(R.id.chipGroupHistory);
        chipGroupSuggestions = findViewById(R.id.chipGroupPopular);
        
        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                performSearch(etSearch.getText().toString().trim());
                return true;
            }
            return false;
        });
        
        // Request focus and show keyboard
        etSearch.requestFocus();
    }
    
    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }
    
    private void setupSearchHistory() {
        if (chipGroupHistory == null) return;
        
        String[] history = {"iPhone 15", "Samsung Galaxy", "MacBook", "AirPods"};
        for (String item : history) {
            Chip chip = new Chip(this);
            chip.setText(item);
            chip.setCloseIconVisible(true);
            chip.setOnClickListener(v -> performSearch(item));
            chip.setOnCloseIconClickListener(v -> chipGroupHistory.removeView(chip));
            chipGroupHistory.addView(chip);
        }
    }
    
    private void setupSuggestions() {
        if (chipGroupSuggestions == null) return;
        
        String[] suggestions = {"Điện thoại", "Laptop", "Tablet", "Tai nghe", "Đồng hồ", "Phụ kiện"};
        for (String item : suggestions) {
            Chip chip = new Chip(this);
            chip.setText(item);
            chip.setOnClickListener(v -> performSearch(item));
            chipGroupSuggestions.addView(chip);
        }
    }
    
    private void performSearch(String query) {
        if (query.isEmpty()) return;
        
        Intent intent = new Intent(this, ProductListActivity.class);
        intent.putExtra(ProductListActivity.EXTRA_SEARCH_QUERY, query);
        intent.putExtra(ProductListActivity.EXTRA_CATEGORY_NAME, "Kết quả: " + query);
        startActivity(intent);
    }
}
