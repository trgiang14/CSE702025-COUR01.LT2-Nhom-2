package com.example.qlkho.view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class ModernTable extends JTable {
    private Color headerBackground = new Color(66, 139, 202);
    private Color headerForeground = Color.WHITE;
    private Color selectionBackground = new Color(66, 139, 202, 50);
    private Color alternateRowColor = new Color(240, 240, 240);

    public ModernTable() {
        setupTable();
    }

    private void setupTable() {
        setFont(new Font("Segoe UI", Font.PLAIN, 13));
        setRowHeight(30);
        setShowGrid(false);
        setIntercellSpacing(new Dimension(0, 0));
        setSelectionBackground(selectionBackground);
        setSelectionForeground(Color.BLACK);
        
        JTableHeader header = getTableHeader();
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value,
                        isSelected, hasFocus, row, column);
                label.setFont(new Font("Segoe UI", Font.BOLD, 13));
                label.setBackground(headerBackground);
                label.setForeground(headerForeground);
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
                    label.setBackground(row % 2 == 0 ? Color.WHITE : alternateRowColor);
                }
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