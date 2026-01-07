package com.utephonehub;

import android.app.Application;

/**
 * Application class for UTE Phone Hub
 * Used for global initialization and configuration
 */
public class UTEPhoneHubApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // Initialize global configurations here
        // e.g., Analytics, Crashlytics, etc.
    }
}
