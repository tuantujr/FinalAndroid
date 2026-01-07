package com.example.ute.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.ute.models.User;
import com.google.gson.Gson;

public class SessionManager {
    
    private static final String PREF_NAME = "UTEPhoneHubSession";
    private static final String KEY_AUTH_TOKEN = "auth_token";
    private static final String KEY_REFRESH_TOKEN = "refresh_token";
    private static final String KEY_USER = "user";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Gson gson;
    
    public SessionManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
        gson = new Gson();
    }
    
    public void saveAuthToken(String token) {
        editor.putString(KEY_AUTH_TOKEN, token);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.apply();
    }
    
    public String getAuthToken() {
        return prefs.getString(KEY_AUTH_TOKEN, null);
    }
    
    public void saveRefreshToken(String token) {
        editor.putString(KEY_REFRESH_TOKEN, token);
        editor.apply();
    }
    
    public String getRefreshToken() {
        return prefs.getString(KEY_REFRESH_TOKEN, null);
    }
    
    public void saveUser(User user) {
        String userJson = gson.toJson(user);
        editor.putString(KEY_USER, userJson);
        editor.apply();
    }
    
    public User getUser() {
        String userJson = prefs.getString(KEY_USER, null);
        if (userJson != null) {
            return gson.fromJson(userJson, User.class);
        }
        return null;
    }
    
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false) && getAuthToken() != null;
    }
    
    public void logout() {
        editor.clear();
        editor.apply();
    }
    
    public void updateUser(User user) {
        saveUser(user);
    }
}
