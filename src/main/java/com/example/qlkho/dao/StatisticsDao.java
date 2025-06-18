package com.example.qlkho.dao;

import com.example.qlkho.entity.Product;
import com.example.qlkho.entity.Order;
import com.example.qlkho.entity.ImportOrder;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsDao {
    private ProductDao productDao;
    private OrderDao orderDao;
    private ImportOrderDao importOrderDao;
    
    public StatisticsDao() {
        this.productDao = new ProductDao();
        this.orderDao = new OrderDao();
        this.importOrderDao = new ImportOrderDao();
    }
    
    
    public Map<String, Integer> getProductCountByCategory() {
        List<Product> products = productDao.getProducts();
        Map<String, Integer> categoryCount = new HashMap<>();
        
        for (Product product : products) {
            String category = product.getProductCategory();
            categoryCount.put(category, categoryCount.getOrDefault(category, 0) + 
                            Integer.parseInt(product.getQuantity()));
        }
        
        return categoryCount;
    }
    
 
    public Map<String, Integer> getProductCountBySupplier() {
        List<Product> products = productDao.getProducts();
        Map<String, Integer> supplierCount = new HashMap<>();
        
        for (Product product : products) {
            String supplier = product.getProductSupplier();
            supplierCount.put(supplier, supplierCount.getOrDefault(supplier, 0) + 
                            Integer.parseInt(product.getQuantity()));
        }
        
        return supplierCount;
    }
 
    public Map<String, Double> getValueByCategory() {
        List<Product> products = productDao.getProducts();
        Map<String, Double> categoryValue = new HashMap<>();
        
        for (Product product : products) {
            String category = product.getProductCategory();
            double value = Double.parseDouble(product.getPrice()) * 
                          Integer.parseInt(product.getQuantity());
            categoryValue.put(category, categoryValue.getOrDefault(category, 0.0) + value);
        }
        
        return categoryValue;
    }
    
  
    public Map<String, Double> getRevenueByMonth() {
        List<Order> orders = orderDao.getOrders();
        Map<String, Double> monthlyRevenue = new HashMap<>();
        
        for (Order order : orders) {
            String dateStr = order.getOrderDate();
            String[] dateParts = dateStr.split("-");
            if (dateParts.length >= 3) {
                String monthYear = dateParts[1] + "-" + dateParts[2]; // MM-yyyy
                double revenue = Double.parseDouble(order.getTotalMoney());
                monthlyRevenue.put(monthYear, monthlyRevenue.getOrDefault(monthYear, 0.0) + revenue);
            }
        }
        
        return monthlyRevenue;
    }
    
   
    public Map<String, Double> getImportCostByMonth() {
        List<ImportOrder> importOrders = importOrderDao.getImportOrders();
        Map<String, Double> monthlyCost = new HashMap<>();
        
        for (ImportOrder importOrder : importOrders) {
            String dateStr = importOrder.getImportDate();
            String[] dateParts = dateStr.split("-");
            if (dateParts.length >= 3) {
                String monthYear = dateParts[1] + "-" + dateParts[2]; // MM-yyyy
                double cost = Double.parseDouble(importOrder.getTotalMoney());
                monthlyCost.put(monthYear, monthlyCost.getOrDefault(monthYear, 0.0) + cost);
            }
        }
        
        return monthlyCost;
    }
    

    public Map<String, Integer> getTop5ProductsByQuantity() {
        List<Product> products = productDao.getProducts();
        
        return products.stream()
                .sorted((p1, p2) -> Integer.compare(
                    Integer.parseInt(p2.getQuantity()),
                    Integer.parseInt(p1.getQuantity())
                ))
                .limit(5)
                .collect(Collectors.toMap(
                    Product::getProductName,
                    p -> Integer.parseInt(p.getQuantity()),
                    (e1, e2) -> e1,
                    LinkedHashMap::new
                ));
    }
    

    public Map<String, Object> getOverviewStatistics() {
        Map<String, Object> overview = new HashMap<>();
        
        List<Product> products = productDao.getProducts();
        List<Order> orders = orderDao.getOrders();
        List<ImportOrder> importOrders = importOrderDao.getImportOrders();
        
        overview.put("totalProducts", products.size());
        
        int totalQuantity = products.stream()
                .mapToInt(p -> Integer.parseInt(p.getQuantity()))
                .sum();
        overview.put("totalQuantity", totalQuantity);
        
        double totalValue = products.stream()
                .mapToDouble(p -> Double.parseDouble(p.getPrice()) * Integer.parseInt(p.getQuantity()))
                .sum();
        overview.put("totalValue", totalValue);
        
        overview.put("totalOrders", orders.size());
        
        overview.put("totalImportOrders", importOrders.size());
        
        double totalRevenue = orders.stream()
                .mapToDouble(o -> Double.parseDouble(o.getTotalMoney()))
                .sum();
        overview.put("totalRevenue", totalRevenue);
        
        double totalImportCost = importOrders.stream()
                .mapToDouble(io -> Double.parseDouble(io.getTotalMoney()))
                .sum();
        overview.put("totalImportCost", totalImportCost);
        
        return overview;
    }
    

    public List<String> getAvailableYears() {
        Set<String> years = new HashSet<>();
        
        orderDao.getOrders().forEach(order -> {
            String[] dateParts = order.getOrderDate().split("-");
            if (dateParts.length >= 3) {
                years.add(dateParts[2]);
            }
        });
        
        importOrderDao.getImportOrders().forEach(importOrder -> {
            String[] dateParts = importOrder.getImportDate().split("-");
            if (dateParts.length >= 3) {
                years.add(dateParts[2]);
            }
        });
        
        return years.stream()
                   .sorted(Comparator.reverseOrder())
                   .collect(Collectors.toList());
    }

   
    public Map<String, Double> getRevenueByMonth(String selectedYear) {
        List<Order> orders = orderDao.getOrders();
        Map<String, Double> monthlyRevenue = new TreeMap<>();
        
        for (int i = 1; i <= 12; i++) {
            monthlyRevenue.put(String.format("%02d", i), 0.0);
        }
        
        for (Order order : orders) {
            String[] dateParts = order.getOrderDate().split("-");
            if (dateParts.length >= 3 && dateParts[2].equals(selectedYear)) {
                String month = dateParts[1]; // MM
                double revenue = Double.parseDouble(order.getTotalMoney());
                monthlyRevenue.put(month, monthlyRevenue.getOrDefault(month, 0.0) + revenue);
            }
        }
        
        return monthlyRevenue;
    }
    
  
    public Map<String, Double> getImportCostByMonth(String selectedYear) {
        List<ImportOrder> importOrders = importOrderDao.getImportOrders();
        Map<String, Double> monthlyCost = new TreeMap<>();
        
        for (int i = 1; i <= 12; i++) {
            monthlyCost.put(String.format("%02d", i), 0.0);
        }
        
        for (ImportOrder importOrder : importOrders) {
            String[] dateParts = importOrder.getImportDate().split("-");
            if (dateParts.length >= 3 && dateParts[2].equals(selectedYear)) {
                String month = dateParts[1]; // MM
                double cost = Double.parseDouble(importOrder.getTotalMoney());
                monthlyCost.put(month, monthlyCost.getOrDefault(month, 0.0) + cost);
            }
        }
        
        return monthlyCost;
    }
    
 
    public Map<String, Object> getOverviewStatistics(String selectedYear) {
        Map<String, Object> overview = new HashMap<>();
        
        List<Product> products = productDao.getProducts();
        List<Order> orders = orderDao.getOrders().stream()
            .filter(order -> order.getOrderDate().split("-")[2].equals(selectedYear))
            .collect(Collectors.toList());
        List<ImportOrder> importOrders = importOrderDao.getImportOrders().stream()
            .filter(importOrder -> importOrder.getImportDate().split("-")[2].equals(selectedYear))
            .collect(Collectors.toList());
        
        overview.put("totalProducts", products.size());
        
        int totalQuantity = products.stream()
                .mapToInt(p -> Integer.parseInt(p.getQuantity()))
                .sum();
        overview.put("totalQuantity", totalQuantity);
        
        double totalValue = products.stream()
                .mapToDouble(p -> Double.parseDouble(p.getPrice()) * Integer.parseInt(p.getQuantity()))
                .sum();
        overview.put("totalValue", totalValue);
        
        overview.put("totalOrders", orders.size());
        
        overview.put("totalImportOrders", importOrders.size());
        
        double totalRevenue = orders.stream()
                .mapToDouble(o -> Double.parseDouble(o.getTotalMoney()))
                .sum();
        overview.put("totalRevenue", totalRevenue);
        
        double totalImportCost = importOrders.stream()
                .mapToDouble(io -> Double.parseDouble(io.getTotalMoney()))
                .sum();
        overview.put("totalImportCost", totalImportCost);
        
        return overview;
    }
} 