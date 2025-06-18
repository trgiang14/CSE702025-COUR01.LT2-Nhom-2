package com.example.qlkho.entity;

import com.example.qlkho.utils.CalendarAdapter;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Order {
    private String id;
    @XmlJavaTypeAdapter(CalendarAdapter.class)
    private Calendar orderDate;
    private String totalMoney;

    public Order() {
    }

    public Order(String id, Calendar orderDate, String totalMoney) {
        this.id = id;
        this.orderDate = orderDate;
        this.totalMoney = totalMoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrderDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(orderDate.getTime());
    }

    public void setOrderDate(Calendar orderDate) {
        this.orderDate = orderDate;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id='" + id + '\'' +
                ", orderDate=" + getOrderDate() +
                ", totalMoney='" + totalMoney + '\'' +
                '}';
    }
}
