package com.example.ute.api;

import com.example.ute.models.Cart;
import com.example.ute.models.Category;
import com.example.ute.models.Order;
import com.example.ute.models.Product;
import com.example.ute.models.User;
import com.example.ute.models.request.AddToCartRequest;
import com.example.ute.models.request.LoginRequest;
import com.example.ute.models.request.RegisterRequest;
import com.example.ute.models.request.UpdateCartRequest;
import com.example.ute.models.response.ApiResponse;
import com.example.ute.models.response.AuthResponse;
import com.example.ute.models.response.ProductListResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    
    // ============ Authentication APIs ============
    
    @POST("api/v1/auth/register")
    Call<AuthResponse> register(@Body RegisterRequest request);
    
    @POST("api/v1/auth/login")
    Call<AuthResponse> login(@Body LoginRequest request);
    
    @POST("api/v1/auth/refresh")
    Call<AuthResponse> refreshToken(@Header("Authorization") String refreshToken);
    
    @POST("api/v1/auth/logout")
    Call<ApiResponse> logout(@Header("Authorization") String token);
    
    // ============ User APIs ============
    
    @GET("api/v1/user/me")
    Call<User> getCurrentUser(@Header("Authorization") String token);
    
    @POST("api/v1/user/profile")
    Call<User> updateProfile(@Header("Authorization") String token, @Body User user);
    
    // ============ Product APIs ============
    
    @GET("api/v1/products")
    Call<ProductListResponse> getProducts(
            @Query("page") Integer page,
            @Query("size") Integer size,
            @Query("categoryId") Long categoryId,
            @Query("brandId") Long brandId,
            @Query("sort") String sort
    );
    
    @GET("api/v1/products/{id}")
    Call<Product> getProductDetail(@Path("id") Long productId);
    
    @GET("api/v1/products/search")
    Call<ProductListResponse> searchProducts(
            @Query("keyword") String keyword,
            @Query("page") Integer page,
            @Query("size") Integer size
    );
    
    // ============ Category APIs ============
    
    @GET("api/v1/categories")
    Call<List<Category>> getCategories();
    
    @GET("api/v1/categories/{id}")
    Call<Category> getCategoryDetail(@Path("id") Long categoryId);
    
    // ============ Cart APIs ============
    
    @GET("api/v1/cart")
    Call<Cart> getCart(@Header("Authorization") String token);
    
    @POST("api/v1/cart/items")
    Call<ApiResponse> addToCart(
            @Header("Authorization") String token,
            @Body AddToCartRequest request
    );
    
    @PUT("api/v1/cart/items/{id}")
    Call<ApiResponse> updateCartItem(
            @Header("Authorization") String token,
            @Path("id") Long itemId,
            @Body UpdateCartRequest request
    );
    
    @DELETE("api/v1/cart/items/{id}")
    Call<ApiResponse> removeCartItem(
            @Header("Authorization") String token,
            @Path("id") Long itemId
    );
    
    @DELETE("api/v1/cart/clear")
    Call<ApiResponse> clearCart(@Header("Authorization") String token);
    
    // ============ Order APIs ============
    
    @GET("api/v1/orders")
    Call<List<Order>> getOrders(@Header("Authorization") String token);
    
    @GET("api/v1/orders/{id}")
    Call<Order> getOrderDetail(
            @Header("Authorization") String token,
            @Path("id") Long orderId
    );
    
    @POST("api/v1/checkout")
    Call<Order> checkout(
            @Header("Authorization") String token,
            @Body Order order
    );
    
    // ============ Voucher APIs ============
    
    @POST("api/v1/vouchers/validate")
    Call<ApiResponse> validateVoucher(
            @Query("code") String code,
            @Query("totalAmount") Double totalAmount
    );
}
