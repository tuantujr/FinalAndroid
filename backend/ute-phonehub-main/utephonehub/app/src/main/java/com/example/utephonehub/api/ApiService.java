package com.example.utephonehub.api;

import com.example.utephonehub.model.ApiResponse;
import com.example.utephonehub.model.Product;
import com.example.utephonehub.model.ProductListResponse;
import com.example.utephonehub.model.Category;
import com.example.utephonehub.model.Brand;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    // Product endpoints
    @GET("products")
    Call<ApiResponse<ProductListResponse.ProductPageData>> getProducts(
        @Query("page") int page,
        @Query("size") int size
    );

    @GET("products/{id}")
    Call<ApiResponse<Product>> getProductById(@Path("id") Long productId);

    @GET("products/search")
    Call<ApiResponse<ProductListResponse.ProductPageData>> searchProducts(
        @Query("keyword") String keyword,
        @Query("page") int page,
        @Query("size") int size
    );

    // Category endpoints
    @GET("categories")
    Call<ApiResponse<Object>> getCategories();

    // Brand endpoints
    @GET("brands")
    Call<ApiResponse<Object>> getBrands();

    // Health check
    @GET("health")
    Call<String> checkHealth();
}
