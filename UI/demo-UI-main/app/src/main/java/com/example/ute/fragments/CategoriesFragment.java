package com.example.ute.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ute.R;
import com.example.ute.activities.ProductListActivity;
import com.example.ute.adapters.CategoryGridAdapter;
import com.example.ute.api.ApiClient;
import com.example.ute.api.ApiService;
import com.example.ute.models.Category;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesFragment extends Fragment {
    
    private RecyclerView rvCategories;
    private CategoryGridAdapter adapter;
    private ApiService apiService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_categories, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        apiService = ApiClient.getApiService();
        
        initViews(view);
        loadCategories();
    }
    
    private void initViews(View view) {
        rvCategories = view.findViewById(R.id.rvCategories);
        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        
        adapter = new CategoryGridAdapter(new ArrayList<>(), category -> {
            Intent intent = new Intent(getActivity(), ProductListActivity.class);
            intent.putExtra(ProductListActivity.EXTRA_CATEGORY_ID, category.getId());
            intent.putExtra(ProductListActivity.EXTRA_CATEGORY_NAME, category.getName());
            startActivity(intent);
        });
        
        rvCategories.setAdapter(adapter);
    }
    
    private void loadCategories() {
        apiService.getCategories().enqueue(new Callback<List<Category>>() {
            @Override
            public void onResponse(Call<List<Category>> call, Response<List<Category>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    adapter.updateData(response.body());
                }
            }
            
            @Override
            public void onFailure(Call<List<Category>> call, Throwable t) {
                // Handle error
            }
        });
    }
}
