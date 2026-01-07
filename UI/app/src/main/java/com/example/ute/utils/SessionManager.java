package com.example.ute.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.ute.models.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SessionManager {
    
    private static final String TAG = "SessionManager";
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
        gson = createGson();
    }
    
    private Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeSerializer())
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeDeserializer())
                .setLenient()
                .create();
    }
    
    private static class LocalDateTimeSerializer implements JsonSerializer<LocalDateTime> {
        @Override
        public JsonElement serialize(LocalDateTime src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        }
    }
    
    private static class LocalDateTimeDeserializer implements JsonDeserializer<LocalDateTime> {
        private static final DateTimeFormatter[] formatters = {
                DateTimeFormatter.ISO_LOCAL_DATE_TIME,
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
        };
        
        @Override
        public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) 
                throws JsonParseException {
            String dateString = json.getAsString();
            if (dateString == null || dateString.isEmpty()) {
                return null;
            }
            
            for (DateTimeFormatter formatter : formatters) {
                try {
                    return LocalDateTime.parse(dateString, formatter);
                } catch (Exception e) {
                    // Try next formatter
                }
            }
            
            try {
                return LocalDateTime.parse(dateString);
            } catch (Exception e) {
                Log.w(TAG, "Unable to parse date: " + dateString);
                return null;
            }
        }
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
        try {
            if (user != null) {
                String userJson = gson.toJson(user);
                editor.putString(KEY_USER, userJson);
                editor.apply();
                Log.d(TAG, "User saved successfully");
            }
        } catch (Exception e) {
            Log.e(TAG, "Error saving user: " + e.getMessage());
        }
    }
    
    public User getUser() {
        try {
            String userJson = prefs.getString(KEY_USER, null);
            if (userJson != null) {
                return gson.fromJson(userJson, User.class);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error getting user: " + e.getMessage());
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
    
    public String getUserFullName() {
        User user = getUser();
        return user != null ? user.getFullName() : null;
    }
    
    public String getUserEmail() {
        User user = getUser();
        return user != null ? user.getEmail() : null;
    }
    
    public String getUserPhone() {
        User user = getUser();
        return user != null ? user.getPhoneNumber() : null;
    }
    
    public void setUserFullName(String fullName) {
        User user = getUser();
        if (user != null) {
            user.setFullName(fullName);
            saveUser(user);
        }
    }
    
    public void setUserPhone(String phone) {
        User user = getUser();
        if (user != null) {
            user.setPhoneNumber(phone);
            saveUser(user);
        }
    }
}
