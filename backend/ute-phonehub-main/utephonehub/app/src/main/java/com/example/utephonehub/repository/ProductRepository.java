package com.example.utephonehub.repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.utephonehub.api.ApiService;
import com.example.utephonehub.api.RetrofitClient;
import com.example.utephonehub.model.ApiResponse;
import com.example.utephonehub.model.Product;
import com.example.utephonehub.model.ProductListResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {
    private ApiService apiService;

    public ProductRepository() {
        apiService = RetrofitClient.getApiService();
    }

    public LiveData<ProductListResponse.ProductPageData> getProducts(int page, int size) {
        MutableLiveData<ProductListResponse.ProductPageData> productLiveData = new MutableLiveData<>();

        apiService.getProducts(page, size).enqueue(new Callback<ApiResponse<ProductListResponse.ProductPageData>>() {
            @Override
            public void onResponse(Call<ApiResponse<ProductListResponse.ProductPageData>> call, Response<ApiResponse<ProductListResponse.ProductPageData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productLiveData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ProductListResponse.ProductPageData>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return productLiveData;
    }

    public LiveData<Product> getProductById(Long productId) {
        MutableLiveData<Product> productLiveData = new MutableLiveData<>();

        apiService.getProductById(productId).enqueue(new Callback<ApiResponse<Product>>() {
            @Override
            public void onResponse(Call<ApiResponse<Product>> call, Response<ApiResponse<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productLiveData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<Product>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return productLiveData;
    }

    public LiveData<ProductListResponse.ProductPageData> searchProducts(String keyword, int page, int size) {
        MutableLiveData<ProductListResponse.ProductPageData> productLiveData = new MutableLiveData<>();

        if (keyword == null || keyword.isEmpty()) {
            return getProducts(page, size);
        }

        apiService.searchProducts(keyword, page, size).enqueue(new Callback<ApiResponse<ProductListResponse.ProductPageData>>() {
            @Override
            public void onResponse(Call<ApiResponse<ProductListResponse.ProductPageData>> call, Response<ApiResponse<ProductListResponse.ProductPageData>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    productLiveData.setValue(response.body().getData());
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<ProductListResponse.ProductPageData>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        return productLiveData;
    }
}
