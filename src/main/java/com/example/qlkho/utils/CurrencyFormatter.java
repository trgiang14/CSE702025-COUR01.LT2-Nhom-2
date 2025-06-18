package com.example.qlkho.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class CurrencyFormatter {
    private static final DecimalFormat CURRENCY_FORMAT;
    
    static {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault());
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        
        CURRENCY_FORMAT = new DecimalFormat("#,##0", symbols);
    }
    

    public static String formatCurrency(double amount) {
        return CURRENCY_FORMAT.format(amount);
    }
    

    public static String formatCurrency(String amountStr) {
        try {
            double amount = Double.parseDouble(amountStr.replace(".", "").replace(",", "."));
            return formatCurrency(amount);
        } catch (NumberFormatException e) {
            return amountStr; 
        }
    }
    

    public static double parseCurrency(String formattedAmount) {
        try {
            return Double.parseDouble(formattedAmount.replace(".", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    

    public static boolean isValidCurrency(String amount) {
        try {
            parseCurrency(amount);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
} 