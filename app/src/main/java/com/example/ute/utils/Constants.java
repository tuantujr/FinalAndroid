package com.example.ute.utils;

public class Constants {
    
    // API Base URL
    public static final String BASE_URL = "http://10.0.2.2:8080/"; // For emulator
    // public static final String BASE_URL = "http://192.168.1.x:8080/"; // For physical device
    
    // Intent Extra Keys
    public static final String EXTRA_PRODUCT_ID = "product_id";
    public static final String EXTRA_CATEGORY_ID = "category_id";
    public static final String EXTRA_CATEGORY_NAME = "category_name";
    public static final String EXTRA_BRAND_ID = "brand_id";
    public static final String EXTRA_ORDER_ID = "order_id";
    public static final String EXTRA_ORDER_CODE = "order_code";
    public static final String EXTRA_FILTER_TYPE = "filter_type";
    
    // Pagination
    public static final int DEFAULT_PAGE_SIZE = 10;
    public static final int FIRST_PAGE = 1;
    
    // Order Status
    public static final String ORDER_STATUS_PENDING = "PENDING";
    public static final String ORDER_STATUS_PROCESSING = "PROCESSING";
    public static final String ORDER_STATUS_SHIPPED = "SHIPPED";
    public static final String ORDER_STATUS_DELIVERED = "DELIVERED";
    public static final String ORDER_STATUS_CANCELLED = "CANCELLED";
    
    // Payment Methods
    public static final String PAYMENT_COD = "COD";
    public static final String PAYMENT_BANK_TRANSFER = "BANK_TRANSFER";
    public static final String PAYMENT_STORE_PICKUP = "STORE_PICKUP";
    
    // User Roles
    public static final String ROLE_CUSTOMER = "customer";
    public static final String ROLE_ADMIN = "admin";
    
    // Shared Preferences
    public static final String PREF_NAME = "UTEPhoneHubPrefs";
    public static final String PREF_FIRST_LAUNCH = "first_launch";
    public static final String PREF_DARK_MODE = "dark_mode";
    public static final String PREF_NOTIFICATION_ENABLED = "notification_enabled";
    
    // Request Codes
    public static final int REQUEST_CODE_LOGIN = 1001;
    public static final int REQUEST_CODE_ADDRESS = 1002;
    public static final int REQUEST_CODE_CHECKOUT = 1003;
    
    // Timeout Values (in seconds)
    public static final int CONNECT_TIMEOUT = 30;
    public static final int READ_TIMEOUT = 30;
    public static final int WRITE_TIMEOUT = 30;
}
