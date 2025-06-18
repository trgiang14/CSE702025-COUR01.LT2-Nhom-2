package com.example.qlkho.entity;

import com.example.qlkho.utils.CalendarAdapter;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Product {
    private String id;
    private String productName;
    private String productCategory;
    private String productSupplier;
    @XmlJavaTypeAdapter(CalendarAdapter.class)
    private Calendar productExpiredDate;
    private String quantity;
    private String price;

    public Product() {
    }

    public Product(String id, String productName, String productCategory, String productSupplier, Calendar productExpiredDate, String quantity, String price) {
        this.id = id;
        this.productName = productName;
        this.productCategory = productCategory;
        this.productSupplier = productSupplier;
        this.productExpiredDate = productExpiredDate;
        this.quantity = quantity;
        this.price = price;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductSupplier() {
        return productSupplier;
    }

    public void setProductSupplier(String productSupplier) {
        this.productSupplier = productSupplier;
    }

    public String getProductExpireDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        return dateFormat.format(productExpiredDate.getTime());
    }

    public void setProductExpireDate(Calendar productExpireDate) {
        this.productExpiredDate = productExpireDate;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", productName='" + productName + '\'' +
                ", productSupplier='" + productSupplier + '\'' +
                ", productExpireDate=" + getProductExpireDate() +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
