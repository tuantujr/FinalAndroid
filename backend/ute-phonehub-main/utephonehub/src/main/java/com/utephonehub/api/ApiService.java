package com.utephonehub.api;

import com.utephonehub.model.ApiResponse;
import com.utephonehub.model.Product;
import com.utephonehub.model.ProductListResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    String BASE_URL = "http://localhost:8080/api/v1/";

    @GET("health")
    Call<ApiResponse<String>> checkHealth();

    @GET("products")
    Call<ProductListResponse> getProducts(
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("products/{id}")
    Call<ApiResponse<Product>> getProductById(@Path("id") Long id);

    @GET("products/search")
    Call<ProductListResponse> searchProducts(
            @Query("keyword") String keyword,
            @Query("page") int page,
            @Query("size") int size
    );

    @GET("categories")
    Call<ApiResponse<Object>> getCategories();

    @GET("brands")
    Call<ApiResponse<Object>> getBrands();
}
