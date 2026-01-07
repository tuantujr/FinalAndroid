package com.example.ute.api;

import com.example.ute.models.Cart;
import com.example.ute.models.Category;
import com.example.ute.models.Order;
import com.example.ute.models.Product;
import com.example.ute.models.Review;
import com.example.ute.models.User;
import com.example.ute.models.request.AddToCartRequest;
import com.example.ute.models.request.AddressRequest;
import com.example.ute.models.request.ChangePasswordRequest;
import com.example.ute.models.request.ForgotPasswordRequest;
import com.example.ute.models.request.LoginRequest;
import com.example.ute.models.request.RegisterRequest;
import com.example.ute.models.request.UpdateCartRequest;
import com.example.ute.models.request.CheckoutRequest;
import com.example.ute.models.request.UpdateProfileRequest;
import com.example.ute.models.response.AddressListResponse;
import com.example.ute.models.response.AddressResponse;
import com.example.ute.models.response.ApiResponse;
import com.example.ute.models.response.AuthResponse;
import com.example.ute.models.response.CartResponse;
import com.example.ute.models.response.CategoryListResponse;
import com.example.ute.models.response.OrderListResponse;
import com.example.ute.models.response.OrderResponse;
import com.example.ute.models.response.ProductDetailResponse;
import com.example.ute.models.response.ProductListResponse;
import com.example.ute.models.response.ReviewListResponse;
import com.example.ute.models.response.ReviewResponse;
import com.example.ute.models.response.UserDataResponse;
import com.example.ute.models.response.VoucherResponse;
import com.example.ute.models.response.VoucherValidationResponse;
import com.example.ute.models.Province;
import com.example.ute.models.Ward;
import com.example.ute.models.response.ProvinceListResponse;
import com.example.ute.models.response.WardListResponse;
import com.example.ute.models.response.BrandResponse;

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
    
    // ============ Forgot Password APIs ============
    
    @POST("api/v1/auth/forgot-password/request")
    Call<ApiResponse> requestForgotPasswordOTP(@Body ForgotPasswordRequest request);
    
    @POST("api/v1/auth/forgot-password/verify")
    Call<ApiResponse> verifyForgotPasswordOTP(@Body ForgotPasswordRequest request);
    
    @POST("api/v1/auth/forgot-password/reset")
    Call<ApiResponse> resetForgotPassword(@Body ForgotPasswordRequest request);
    
    // ============ User APIs ============
    
    @GET("api/v1/user/me")
    Call<UserDataResponse> getCurrentUser(@Header("Authorization") String token);
    
    @POST("api/v1/user/profile")
    Call<UserDataResponse> updateProfile(@Header("Authorization") String token, @Body UpdateProfileRequest request);
    
    @POST("api/v1/user/password")
    Call<ApiResponse> changePassword(
            @Header("Authorization") String token,
            @Body ChangePasswordRequest request
    );
    
    @GET("api/v1/user/addresses")
    Call<AddressListResponse> getAddresses(@Header("Authorization") String token);
    
    @POST("api/v1/user/addresses")
    Call<AddressResponse> addAddress(
            @Header("Authorization") String token,
            @Body AddressRequest request
    );
    
    @PUT("api/v1/user/addresses/{id}")
    Call<AddressResponse> updateAddress(
            @Header("Authorization") String token,
            @Path("id") Long addressId,
            @Body AddressRequest request
    );
    
    @DELETE("api/v1/user/addresses/{id}")
    Call<ApiResponse> deleteAddress(
            @Header("Authorization") String token,
            @Path("id") Long addressId
    );
    
    // ============ Product APIs ============
    
    @GET("api/v1/products")
    Call<ProductListResponse> getProducts(
            @Query("page") Integer page,
            @Query("limit") Integer limit,
            @Query("categoryId") Long categoryId,
            @Query("brandId") Long brandId,
            @Query("sortBy") String sortBy
    );
    
    @GET("api/v1/products/{id}")
    Call<ProductDetailResponse> getProductDetail(@Path("id") Long productId);
    
    @GET("api/v1/products")
    Call<ProductListResponse> searchProducts(
            @Query("keyword") String keyword,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );
    
    // ============ Category APIs ============
    
    @GET("api/v1/categories")
    Call<CategoryListResponse> getCategories();
    
    @GET("api/v1/categories/{id}")
    Call<Category> getCategoryDetail(@Path("id") Long categoryId);
    
    // ============ Brand APIs ============
    
    @GET("api/v1/brands")
    Call<BrandResponse> getBrands();
    
    // ============ Cart APIs ============
    
    @GET("api/v1/cart")
    Call<CartResponse> getCart(@Header("Authorization") String token);
    
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
    Call<OrderListResponse> getOrders(@Header("Authorization") String token);
    
    @GET("api/v1/orders/{id}")
    Call<OrderResponse> getOrderDetail(
            @Header("Authorization") String token,
            @Path("id") Long orderId
    );
    
    @POST("api/v1/checkout")
    Call<OrderResponse> checkout(
            @Header("Authorization") String token,
            @Body CheckoutRequest checkoutRequest
    );
    
    // ============ Review APIs ============
    
    @GET("api/v1/reviews")
    Call<ReviewListResponse> getProductReviews(
            @Header("Authorization") String token,
            @Query("productId") Long productId,
            @Query("page") Integer page,
            @Query("limit") Integer limit
    );
    
    @GET("api/v1/reviews/{id}")
    Call<ReviewResponse> getReviewDetail(@Path("id") Long reviewId);
    
    @POST("api/v1/reviews")
    Call<ReviewResponse> submitReview(
            @Header("Authorization") String token,
            @Query("productId") Long productId,
            @Body Review review
    );
    
    @POST("api/v1/reviews/{id}/like")
    Call<ApiResponse> likeReview(
            @Header("Authorization") String token,
            @Path("id") Long reviewId
    );
    
    @DELETE("api/v1/reviews/{id}/like")
    Call<ApiResponse> unlikeReview(
            @Header("Authorization") String token,
            @Path("id") Long reviewId
    );
    
    // ============ Voucher APIs ============
    
    @GET("api/v1/vouchers")
    Call<com.example.ute.models.response.VoucherListResponse> getVouchersWithAuth(
            @Header("Authorization") String token,
            @Query("page") int page,
            @Query("limit") int limit,
            @Query("status") String status
    );
    
    @POST("api/v1/vouchers/validate")
    Call<VoucherValidationResponse> validateVoucher(
            @Body com.example.ute.models.request.ValidateVoucherRequest request
    );
    
    // ============ Location APIs ============
    
    @GET("api/v1/location/provinces")
    Call<ProvinceListResponse> getProvinces();
    
    @GET("api/v1/location/provinces/{code}/wards")
    Call<WardListResponse> getWardsByProvince(@Path("code") String provinceCode);
}
