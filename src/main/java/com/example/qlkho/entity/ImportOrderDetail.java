package com.example.qlkho.entity;

public class ImportOrderDetail {
    private String id;
    private String importOrderId;
    private String productId;
    private String productName;
    private String quantity;
    private String unitPrice;
    private String totalPrice;

    public ImportOrderDetail() {
    }

    public ImportOrderDetail(String id, String importOrderId, String productId, String productName, String quantity, String unitPrice, String totalPrice) {
        this.id = id;
        this.importOrderId = importOrderId;
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImportOrderId() {
        return importOrderId;
    }

    public void setImportOrderId(String importOrderId) {
        this.importOrderId = importOrderId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ImportOrderDetail{" +
                "id='" + id + '\'' +
                ", importOrderId='" + importOrderId + '\'' +
                ", productId='" + productId + '\'' +
                ", productName='" + productName + '\'' +
                ", quantity='" + quantity + '\'' +
                ", unitPrice='" + unitPrice + '\'' +
                ", totalPrice='" + totalPrice + '\'' +
                '}';
    }
} 