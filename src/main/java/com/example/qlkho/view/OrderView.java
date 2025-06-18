/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.example.qlkho.view;

import com.example.qlkho.entity.Order;
import com.example.qlkho.utils.CalendarUtils;
import com.example.qlkho.utils.IdGenerator;
import com.example.qlkho.utils.CurrencyFormatter;
import com.example.qlkho.view.components.*;
import com.toedter.calendar.JDateChooser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.awt.*;
import javax.swing.*;

public class OrderView extends JPanel {
    private final SimpleDateFormat sdf;

    public OrderView() {
        initComponents();
        sdf = new SimpleDateFormat("dd-MM-yyyy");

        dpOrderDate.setDateFormatString("dd-MM-yyyy");
        txtId.setEditable(false);
        txtTotalMoney.setCurrencyValue(0);
        txtTotalMoney.setEditable(false);

        CalendarUtils.disableDateChooserTextEditing(dpOrderDate);
    }


    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.anchor = GridBagConstraints.WEST;
        formGbc.insets = new Insets(5, 5, 5, 5);

        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        
        txtId = new ModernTextField("Mã phiếu xuất");
        txtTotalMoney = new CurrencyTextField();
        
        dpOrderDate = new JDateChooser();
        dpOrderDate.setPreferredSize(new Dimension(200, 35));
        dpOrderDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        btnAdd = new ModernButton("Thêm", true);
        btnDelete = new ModernButton("Xóa");
        btnRefresh = new ModernButton("Làm mới");
        btnDetail = new ModernButton("Chi tiết");
        btnExport = new ModernButton("Xuất Excel");
        
        String[] columnNames = {"Mã phiếu xuất", "Ngày xuất", "Tổng tiền"};
        tbExport = new ModernTable();
        tbExport.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            columnNames
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        
        jScrollPane1 = new javax.swing.JScrollPane(tbExport);

        tbExport.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tbExport.getSelectedRow();
                if (row >= 0) {
                    String id = tbExport.getValueAt(row, 0).toString();
                    String orderDate = tbExport.getValueAt(row, 1).toString();
                    String totalMoneyFormatted = tbExport.getValueAt(row, 2).toString();

                    txtId.setText(id);
                    txtTotalMoney.setText(totalMoneyFormatted);
                    try {
                        java.util.Date date = new java.text.SimpleDateFormat("dd-MM-yyyy").parse(orderDate);
                        dpOrderDate.setDate(date);
                    } catch (Exception e) {
                        dpOrderDate.setDate(null);
                    }
                }
            }
        });

        setupLabel(jLabel2, "Mã phiếu xuất:");
        setupLabel(jLabel3, "Ngày xuất:");
        setupLabel(jLabel4, "Tổng tiền:");

        addFormRow(formPanel, formGbc, jLabel2, txtId, 0);
        addFormRow(formPanel, formGbc, jLabel3, dpOrderDate, 1);
        addFormRow(formPanel, formGbc, jLabel4, txtTotalMoney, 2);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(5, 5, 5, 5);

        buttonPanel.add(btnAdd, buttonGbc);
        buttonGbc.gridx = 1;
        buttonPanel.add(btnDelete, buttonGbc);
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 1;
        buttonPanel.add(btnRefresh, buttonGbc);
        buttonGbc.gridx = 1;
        buttonPanel.add(btnDetail, buttonGbc);
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 2;
        buttonGbc.gridwidth = 2;
        buttonPanel.add(btnExport, buttonGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 3;
        formGbc.gridwidth = 2;
        formGbc.insets = new Insets(20, 0, 0, 0);
        formPanel.add(buttonPanel, formGbc);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.4;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 20, 20, 10);
        mainPanel.add(formPanel, gbc);

        JPanel tablePanel = new JPanel(new GridBagLayout());
        tablePanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints tableGbc = new GridBagConstraints();

        jScrollPane1.setPreferredSize(new Dimension(600, 500));
        tableGbc.gridx = 0;
        tableGbc.gridy = 0;
        tableGbc.weighty = 1.0;
        tableGbc.fill = GridBagConstraints.BOTH;
        tablePanel.add(jScrollPane1, tableGbc);

        gbc.gridx = 1;
        gbc.weightx = 0.6;
        gbc.insets = new Insets(20, 10, 20, 20);
        mainPanel.add(tablePanel, gbc);
        
        add(mainPanel, BorderLayout.CENTER);
    }

    private JPanel createHeaderPanel() {
        final Color PRIMARY_COLOR = new Color(70, 130, 180);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Xuất sản phẩm");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);
        
        return panel;
    }

    private void setupLabel(JLabel label, String text) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(51, 51, 51));
        label.setText(text);
    }

    private void addFormRow(JPanel panel, GridBagConstraints gbc, JLabel label, JComponent field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(field, gbc);
    }

    private void tbExportMouseClicked(java.awt.event.MouseEvent evt) {
        int selectedRow = tbExport.getSelectedRow();
        txtId.setText(tbExport.getValueAt(selectedRow, 0).toString());
        dpOrderDate.setDate(CalendarUtils.convertStringToCalendar(tbExport.getValueAt(selectedRow, 1).toString()).getTime());
        txtTotalMoney.setText(tbExport.getValueAt(selectedRow, 2).toString());
    }

    public void setBtnAddActionListener(java.awt.event.ActionListener actionListener) {
        btnAdd.addActionListener(actionListener);
    }

    public void setBtnDeleteActionListener(java.awt.event.ActionListener actionListener) {
        btnDelete.addActionListener(actionListener);
    }

    public void setBtnRefreshActionListener(java.awt.event.ActionListener actionListener) {
        btnRefresh.addActionListener(actionListener);
    }
    public void setBtnExportActionListener(java.awt.event.ActionListener actionListener) {
        btnExport.addActionListener(actionListener);
    }

    public void setDetailActionListener(java.awt.event.ActionListener actionListener) {
        btnDetail.addActionListener(actionListener);
    }

    public void clear(int id) {
        txtTotalMoney.setCurrencyValue(0);
        txtId.setText(IdGenerator.generateOrderId());
        dpOrderDate.setDate(null);
        tbExport.clearSelection();
    }

    public void setId(int id) {
        txtId.setText(IdGenerator.generateOrderId());
    }

    public Order getOrderInput() {
        String id = txtId.getText();
        
        if (id.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập mã phiếu xuất");
        }

        if (txtTotalMoney.getText().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập tổng tiền");
        }

        try {
            double money = txtTotalMoney.getCurrencyValue();
            if (money < 0) {
                throw new IllegalArgumentException("Tổng tiền phải lớn hơn hoặc bằng 0");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Tổng tiền phải là số");
        }

        String orderDate;
        try {
            orderDate = sdf.format(dpOrderDate.getDate());
        } catch (Exception e) {
            throw new IllegalArgumentException("Vui lòng chọn ngày xuất");
        }

        String totalMoney = String.valueOf(txtTotalMoney.getCurrencyValue());
        Calendar orderDateCal = CalendarUtils.convertStringToCalendar(orderDate);
        return new Order(id, orderDateCal, totalMoney);
    }

    public void setDataToTable(List<Order> orderList) {
        while (tbExport.getRowCount() > 0) {
            ((javax.swing.table.DefaultTableModel) tbExport.getModel()).removeRow(0);
        }

        if(orderList == null || orderList.isEmpty()){
            return;
        }

        String[][] data = new String[orderList.size()][3];
        for (int i = 0; i < orderList.size(); i++) {
            Order order = orderList.get(i);
            data[i][0] = order.getId();
            data[i][1] = order.getOrderDate();
            data[i][2] = CurrencyFormatter.formatCurrency(order.getTotalMoney());
        }
        tbExport.setModel(new javax.swing.table.DefaultTableModel(
                data,
                new String[]{
                        "Mã phiếu xuất", "Ngày xuất", "Tổng tiền"
                }
        ));
    }

    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnRefresh;
    private com.toedter.calendar.JDateChooser dpOrderDate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private ModernTable tbExport;
    private javax.swing.JTextField txtId;
    private CurrencyTextField txtTotalMoney;
}
