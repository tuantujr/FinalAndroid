package com.example.myapplication.api;

import com.example.myapplication.model.LoginRequest;
import com.example.myapplication.model.LoginResponse;
import com.example.myapplication.model.RegisterRequest;
import com.example.myapplication.model.Category;
import com.example.myapplication.model.Product;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("api/auth/register")
    Call<LoginResponse> register(@Body RegisterRequest request);

    @POST("api/auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @GET("api/categories")
    Call<List<Category>> getCategories();

    @GET("api/products")
    Call<List<Product>> getProducts();
    
    @GET("api/products/category/{categoryId}")
    Call<List<Product>> getProductsByCategory(
        @Path("categoryId") String categoryId,
        @retrofit2.http.Query("page") int page,
        @retrofit2.http.Query("limit") int limit
    );

    @GET("api/auth/profile")
    Call<com.example.myapplication.model.LoginResponse> getProfile(@retrofit2.http.Query("userId") String userId);
}
