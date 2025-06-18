package com.example.qlkho.utils;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CalendarUtils {
    public static Calendar convertStringToCalendar(String date) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        try {
            cal.setTime(sdf.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return cal;
    }
    public static void disableDateChooserTextEditing(JDateChooser dateChooser) {
        JTextField dateEditor = (JTextField) dateChooser.getDateEditor().getUiComponent();
        dateEditor.setEditable(false);
    }
}
