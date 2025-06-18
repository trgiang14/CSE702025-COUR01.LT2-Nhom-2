package com.example.qlkho.view;

import com.example.qlkho.entity.ImportOrder;
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


public class ImportOrderView extends JPanel {
    private final SimpleDateFormat sdf;

    public ImportOrderView() {
        initComponents();
        sdf = new SimpleDateFormat("dd-MM-yyyy");

        dpImportDate.setDateFormatString("dd-MM-yyyy");
        txtId.setEditable(false);
        txtTotalMoney.setCurrencyValue(0);
        txtTotalMoney.setEditable(false);

        CalendarUtils.disableDateChooserTextEditing(dpImportDate);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(230, 230, 250));
        
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(230, 230, 250));
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(230, 230, 250));
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.anchor = GridBagConstraints.WEST;
        formGbc.insets = new Insets(5, 5, 5, 5);

        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        
        txtId = new ModernTextField();
        txtId.setBackground(new Color(245, 245, 255));
        
        txtTotalMoney = new CurrencyTextField();
        txtTotalMoney.setBackground(new Color(245, 245, 255));
        
        dpImportDate = new JDateChooser();
        dpImportDate.setPreferredSize(new Dimension(200, 35));
        dpImportDate.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        dpImportDate.setBackground(new Color(245, 245, 255));
        dpImportDate.getCalendarButton().setBackground(new Color(200, 180, 230)); 
        dpImportDate.getCalendarButton().setForeground(Color.BLACK); 
        
        btnAdd = new ModernButton("Thêm phiếu nhập", true);
        btnAdd.setBackground(new Color(180, 160, 220));
        btnAdd.setForeground(Color.WHITE);
        
        btnDelete = new ModernButton("Xoá phiếu nhập");
        btnDelete.setBackground(new Color(200, 180, 230));
        btnDelete.setForeground(Color.WHITE);

        btnRefresh = new ModernButton("Làm mới");
        btnRefresh.setBackground(new Color(200, 180, 230));
        btnRefresh.setForeground(Color.WHITE);
        
        btnDetail = new ModernButton("Chi tiết phiếu");
        btnDetail.setBackground(new Color(200, 180, 230));
        btnDetail.setForeground(Color.WHITE);
        
        btnImport = new ModernButton("Tạo báo cáo");
        btnImport.setBackground(new Color(200, 180, 230));
        btnImport.setForeground(Color.WHITE);
        
        tbImport = new ModernTable();
        tbImport.setBackground(new Color(245, 245, 255));
        tbImport.getTableHeader().setBackground(new Color(200, 180, 230));
        tbImport.getTableHeader().setForeground(Color.BLACK);
        
        jScrollPane1 = new JScrollPane(tbImport);
        jScrollPane1.setBackground(new Color(230, 230, 250));
        jScrollPane1.getViewport().setBackground(new Color(245, 245, 255));

        setupLabel(jLabel2, "Mã phiếu nhập:");
        setupLabel(jLabel3, "Ngày nhập:");
        setupLabel(jLabel4, "Tổng tiền:");

        addFormRow(formPanel, formGbc, jLabel2, txtId, 0);
        addFormRow(formPanel, formGbc, jLabel3, dpImportDate, 1);
        addFormRow(formPanel, formGbc, jLabel4, txtTotalMoney, 2);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(230, 230, 250));
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(5, 5, 5, 5);

        buttonPanel.add(btnAdd, buttonGbc);
        buttonGbc.gridx = 1;
        buttonPanel.add(btnDelete, buttonGbc);
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 1;
        buttonPanel.add(btnRefresh, buttonGbc);
        buttonGbc.gridx = 1;
        buttonGbc.gridy = 1;
        buttonPanel.add(btnDetail, buttonGbc);
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 2;
        buttonGbc.gridwidth = 2;
        buttonPanel.add(btnImport, buttonGbc);

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
        tablePanel.setBackground(new Color(230, 230, 250));
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

        tbImport.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tbImport.getSelectedRow();
                if (row >= 0) {
                    String id = tbImport.getValueAt(row, 0).toString();
                    String importDate = tbImport.getValueAt(row, 1).toString();
                    String totalMoneyFormatted = tbImport.getValueAt(row, 2).toString();

                    txtId.setText(id);
                    txtTotalMoney.setCurrencyValue(CurrencyFormatter.parseCurrency(totalMoneyFormatted));
                    try {
                        java.util.Date date = new java.text.SimpleDateFormat("dd-MM-yyyy").parse(importDate);
                        dpImportDate.setDate(date);
                    } catch (Exception e) {
                        dpImportDate.setDate(null);
                    }
                }
            }
        });
    }

    private JPanel createHeaderPanel() {
        final Color PRIMARY_COLOR = new Color(150, 120, 190);
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Nhập sản phẩm");
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

    private void tbImportMouseClicked(java.awt.event.MouseEvent evt) {
        if (tbImport.getSelectedRow() != -1) {
            String id = (String) tbImport.getValueAt(tbImport.getSelectedRow(), 0);
            String importDate = (String) tbImport.getValueAt(tbImport.getSelectedRow(), 1);
            String totalMoneyFormatted = (String) tbImport.getValueAt(tbImport.getSelectedRow(), 2);

            txtId.setText(id);
            txtTotalMoney.setCurrencyValue(CurrencyFormatter.parseCurrency(totalMoneyFormatted));

            try {
                if (importDate != null && !importDate.isEmpty()) {
                    dpImportDate.setDate(sdf.parse(importDate));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

    public void setBtnImportActionListener(java.awt.event.ActionListener actionListener) {
        btnImport.addActionListener(actionListener);
    }

    public void setDetailActionListener(java.awt.event.ActionListener actionListener) {
        btnDetail.addActionListener(actionListener);
    }

    public void clear(int id) {
        txtId.setText(IdGenerator.generateId("PN", id + 1));
        dpImportDate.setDate(Calendar.getInstance().getTime());
        txtTotalMoney.setCurrencyValue(0);
    }

    public void setId(int id) {
        txtId.setText(IdGenerator.generateId("PN", id + 1));
    }

    public ImportOrder getImportOrderInput() {
        String id = txtId.getText().trim();

        if (id.isEmpty()) {
            throw new IllegalArgumentException("Mã phiếu nhập không được để trống!");
        }

        if (dpImportDate.getDate() == null) {
            throw new IllegalArgumentException("Ngày nhập không được để trống!");
        }

        double totalMoneyValue = txtTotalMoney.getCurrencyValue();

        try {
            if (totalMoneyValue < 0) {
                throw new IllegalArgumentException("Tổng tiền phải lớn hơn hoặc bằng 0!");
            }
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Tổng tiền phải là số!");
        }

        String totalMoney = String.valueOf(totalMoneyValue);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dpImportDate.getDate());

        return new ImportOrder(id, calendar, totalMoney);
    }

    public void setDataToTable(List<ImportOrder> importOrderList) {
        String[] columnNames = {"Mã phiếu nhập", "Ngày nhập", "Tổng tiền"};
        Object[][] data = new Object[importOrderList.size()][3];

        for (int i = 0; i < importOrderList.size(); i++) {
            ImportOrder importOrder = importOrderList.get(i);
            data[i][0] = importOrder.getId();
            data[i][1] = importOrder.getImportDate();
            data[i][2] = CurrencyFormatter.formatCurrency(importOrder.getTotalMoney());
        }

        tbImport.setModel(new javax.swing.table.DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnDetail;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnRefresh;
    private com.toedter.calendar.JDateChooser dpImportDate;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private ModernTable tbImport;
    private javax.swing.JTextField txtId;
    private CurrencyTextField txtTotalMoney;
}