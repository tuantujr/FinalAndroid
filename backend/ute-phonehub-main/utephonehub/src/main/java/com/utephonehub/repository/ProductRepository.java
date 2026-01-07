package com.utephonehub.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.utephonehub.api.ApiService;
import com.utephonehub.api.RetrofitClient;
import com.utephonehub.model.Product;
import com.utephonehub.model.ProductListResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class ProductRepository {
    private ApiService apiService;

    public ProductRepository() {
        this.apiService = RetrofitClient.getApiService();
    }

    public LiveData<List<Product>> getProducts(int page, int size) {
        MutableLiveData<List<Product>> productsLiveData = new MutableLiveData<>();

        apiService.getProducts(page, size).enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductListResponse productResponse = response.body();
                    if (productResponse.getData() != null && productResponse.getData().getContent() != null) {
                        productsLiveData.setValue(productResponse.getData().getContent());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                productsLiveData.setValue(null);
                t.printStackTrace();
            }
        });

        return productsLiveData;
    }

    public LiveData<Product> getProductById(Long id) {
        MutableLiveData<Product> productLiveData = new MutableLiveData<>();

        apiService.getProductById(id).enqueue(new Callback<com.utephonehub.model.ApiResponse<Product>>() {
            @Override
            public void onResponse(Call<com.utephonehub.model.ApiResponse<Product>> call, Response<com.utephonehub.model.ApiResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productLiveData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<com.utephonehub.model.ApiResponse<Product>> call, Throwable t) {
                productLiveData.setValue(null);
                t.printStackTrace();
            }
        });

        return productLiveData;
    }

    public LiveData<List<Product>> searchProducts(String keyword, int page, int size) {
        MutableLiveData<List<Product>> searchResultsLiveData = new MutableLiveData<>();

        apiService.searchProducts(keyword, page, size).enqueue(new Callback<ProductListResponse>() {
            @Override
            public void onResponse(Call<ProductListResponse> call, Response<ProductListResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    ProductListResponse productResponse = response.body();
                    if (productResponse.getData() != null && productResponse.getData().getContent() != null) {
                        searchResultsLiveData.setValue(productResponse.getData().getContent());
                    }
                }
            }

            @Override
            public void onFailure(Call<ProductListResponse> call, Throwable t) {
                searchResultsLiveData.setValue(null);
                t.printStackTrace();
            }
        });

        return searchResultsLiveData;
    }
}
