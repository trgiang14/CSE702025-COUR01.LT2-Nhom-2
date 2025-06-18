package com.example.qlkho.view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ModernTable extends JTable {
    // Đổi màu nền của tiêu đề bảng từ xanh sang tím đậm hơn một chút
    private Color headerBackground = new Color(150, 120, 190); // Tím trung bình
    private Color headerForeground = Color.WHITE; // Giữ màu chữ trắng cho tiêu đề
    // Đổi màu nền lựa chọn sang tím nhạt có độ trong suốt
    private Color selectionBackground = new Color(180, 160, 220, 80); // Tím nhạt hơn, hơi trong suốt
    // Đổi màu hàng xen kẽ từ xám nhạt sang màu tím nhạt
    private Color alternateRowColor = new Color(240, 240, 255); // Lavender Blush hoặc gần đó

    public ModernTable() {
        setupTable();
    }

    private void setupTable() {
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setRowHeight(30);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
        setSelectionBackground(selectionBackground);
        setSelectionForeground(Color.BLACK); // Giữ màu chữ đen khi chọn
        
        // Đặt màu nền mặc định của bảng (nếu không được ghi đè bởi renderer)
        setBackground(new Color(245, 245, 255)); // Light Lavender/Ghost White cho nền bảng chung
        
        JTableHeader header = getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                   boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                                        isSelected, hasFocus, row, column);
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setBackground(headerBackground); // Sử dụng màu headerBackground đã đổi
                label.setForeground(headerForeground); // Sử dụng màu headerForeground
                label.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));
                label.setHorizontalAlignment(JLabel.LEFT);
                return label;
            }
        });
        header.setResizingAllowed(true);
        header.setReorderingAllowed(false);

        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                                   boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                                        isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    // Hàng chẵn là màu trắng tinh, hàng lẻ là alternateRowColor (tím nhạt)
                    label.setBackground(row % 2 == 0 ? Color.WHITE : alternateRowColor); 
                }
                // Giữ màu chữ đen cho nội dung bảng
                label.setForeground(Color.BLACK); 
                label.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
                return label;
            }
        });
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}