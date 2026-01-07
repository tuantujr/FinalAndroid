package com.example.ute.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

public class PriceFormatter {
    
    private static final DecimalFormat formatter = new DecimalFormat("#,###");
    
    public static String format(Double price) {
        if (price == null) {
            return "0 ₫";
        }
        return formatter.format(price) + " ₫";
    }
    
    public static String format(double price) {
        return formatter.format(price) + " ₫";
    }
    
    public static String formatWithoutCurrency(Double price) {
        if (price == null) {
            return "0";
        }
        return formatter.format(price);
    }
    
    public static String formatCompact(Double price) {
        if (price == null) {
            return "0";
        }
        
        if (price >= 1000000000) {
            return String.format(Locale.getDefault(), "%.1fB ₫", price / 1000000000);
        } else if (price >= 1000000) {
            return String.format(Locale.getDefault(), "%.1fM ₫", price / 1000000);
        } else if (price >= 1000) {
            return String.format(Locale.getDefault(), "%.1fK ₫", price / 1000);
        } else {
            return formatter.format(price) + " ₫";
        }
    }
}
