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
import com.example.ute.models.response.CategoryListResponse;

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
        apiService.getCategories().enqueue(new Callback<CategoryListResponse>() {
            @Override
            public void onResponse(Call<CategoryListResponse> call, Response<CategoryListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryListResponse categoryResponse = response.body();
                    if (categoryResponse.isSuccess() && categoryResponse.getData() != null) {
                        adapter.updateData(categoryResponse.getData());
                    } else {
                        adapter.updateData(new ArrayList<>());
                    }
                } else {
                    adapter.updateData(new ArrayList<>());
                }
            }
            
            @Override
            public void onFailure(Call<CategoryListResponse> call, Throwable t) {
                adapter.updateData(new ArrayList<>());
            }
        });
    }
}
