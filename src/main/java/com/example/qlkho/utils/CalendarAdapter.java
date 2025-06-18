package com.example.qlkho.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

public class CalendarAdapter extends XmlAdapter<String, Calendar> {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Override
    public Calendar unmarshal(String v) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateFormat.parse(v));
        return cal;
    }

    @Override
    public String marshal(Calendar v) throws Exception {
        if (v == null) {
            return null;
        }
        return dateFormat.format(v.getTime());
    }




}
