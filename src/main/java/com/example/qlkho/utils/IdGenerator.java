package com.example.qlkho.utils;

import java.util.Random;

public class IdGenerator {
    private static final Random random = new Random();
    
    public static String generateProductId() {
        return "PRD" + String.format("%05d", random.nextInt(100000));
    }
    
    public static String generateOrderId() {
        return "ORD" + String.format("%05d", random.nextInt(100000));
    }
    
    public static String generateOrderDetailId() {
        return "ODD" + String.format("%05d", random.nextInt(100000));
    }
    
    public static String generateId(String prefix, int number) {
        return prefix + String.format("%05d", number);
    }
} 