package com.example.ute.api;

import android.util.Log;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * OkHttp Interceptor to add Authorization token and Content-Type headers
 */
public class AuthInterceptor implements Interceptor {
    
    private final String accessToken;
    
    public AuthInterceptor(String accessToken) {
        this.accessToken = accessToken;
    }
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        
        Request.Builder requestBuilder = originalRequest.newBuilder();
        
        // ✅ Add Authorization header if token exists
        if (accessToken != null && !accessToken.isEmpty()) {
            requestBuilder.addHeader("Authorization", "Bearer " + accessToken);
            Log.d("AuthInterceptor", "Token added to request");
        }
        
        // ✅ Add Content-Type header
        requestBuilder.addHeader("Content-Type", "application/json");
        
        Log.d("AuthInterceptor", "URL: " + originalRequest.url());
        Log.d("AuthInterceptor", "Method: " + originalRequest.method());
        Log.d("AuthInterceptor", "Token Present: " + (accessToken != null && !accessToken.isEmpty()));
        
        Request newRequest = requestBuilder.build();
        return chain.proceed(newRequest);
    }
}
