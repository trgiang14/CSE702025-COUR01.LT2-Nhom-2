package com.example.qlkho.entity;

import com.example.qlkho.utils.CalendarAdapter;

import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ImportOrder {
    private String id;
    @XmlJavaTypeAdapter(CalendarAdapter.class)
    private Calendar importDate;
    private String totalMoney;

    public ImportOrder() {
    }

    public ImportOrder(String id, Calendar importDate, String totalMoney) {
        this.id = id;
        this.importDate = importDate;
        this.totalMoney = totalMoney;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImportDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        return sdf.format(importDate.getTime());
    }

    public void setImportDate(Calendar importDate) {
        this.importDate = importDate;
    }

    public String getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(String totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Override
    public String toString() {
        return "ImportOrder{" +
                "id='" + id + '\'' +
                ", importDate=" + getImportDate() +
                ", totalMoney='" + totalMoney + '\'' +
                '}';
    }
} 