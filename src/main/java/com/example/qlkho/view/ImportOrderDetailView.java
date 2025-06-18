package com.example.qlkho.view;

import com.example.qlkho.entity.ImportOrderDetail;
import com.example.qlkho.entity.Product;
import com.example.qlkho.utils.IdGenerator;
import com.example.qlkho.utils.CurrencyFormatter;
import com.example.qlkho.view.components.*;

import java.util.List;
import java.util.Objects;
import java.awt.*;
import javax.swing.*;


public class ImportOrderDetailView extends JPanel {
    private JFrame frame;
    private Product currentProduct;

 
    public ImportOrderDetailView() {
        frame = new JFrame("Chi tiết phiếu nhập");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        
        initComponents();
        
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);

        txtImportOrderId.setEditable(false);
        txtId.setEditable(false);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
    }

   
    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout());
        // Đổi màu nền của JPanel chính sang màu tím nhạt
        setBackground(new Color(230, 230, 250)); // Lavender
        
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        // Đổi màu nền của mainPanel sang màu tím nhạt
        mainPanel.setBackground(new Color(230, 230, 250)); // Lavender
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel formPanel = new JPanel(new GridBagLayout());
        // Đổi màu nền của formPanel sang màu tím nhạt
        formPanel.setBackground(new Color(230, 230, 250)); // Lavender
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.anchor = GridBagConstraints.WEST;
        formGbc.insets = new Insets(5, 5, 5, 5);

        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        
        txtId = new ModernTextField("Mã chi tiết");
        txtImportOrderId = new ModernTextField("Mã đơn nhập");
        txtQuantity = new ModernTextField("Nhập số lượng");
        txtUnitPrice = new CurrencyTextField("Nhập đơn giá");
        
        cbProductId = new JComboBox<>();
        cbProductId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbProductId.setPreferredSize(new Dimension(200, 35));
        
        btnAdd = new ModernButton("Thêm");
        btnUpdate = new ModernButton("Sửa");
        btnDelete = new ModernButton("Xóa");
        btnRefresh = new ModernButton("Làm mới");
        
        tbImportOrderDetails = new ModernTable();
        jScrollPane1 = new JScrollPane(tbImportOrderDetails);

        setupLabel(jLabel2, "Mã:");
        setupLabel(jLabel3, "Mã đơn nhập:");
        setupLabel(jLabel4, "Mã sản phẩm:");
        setupLabel(jLabel5, "Số lượng:");
        setupLabel(jLabel6, "Đơn giá:");

        addFormRow(formPanel, formGbc, jLabel2, txtId, 0);
        addFormRow(formPanel, formGbc, jLabel3, txtImportOrderId, 1);
        addFormRow(formPanel, formGbc, jLabel4, cbProductId, 2);
        addFormRow(formPanel, formGbc, jLabel5, txtQuantity, 3);
        addFormRow(formPanel, formGbc, jLabel6, txtUnitPrice, 4);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        // Đổi màu nền của buttonPanel sang màu tím nhạt
        buttonPanel.setBackground(new Color(230, 230, 250)); // Lavender
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(5, 5, 5, 5);

        buttonPanel.add(btnAdd, buttonGbc);
        buttonGbc.gridx = 1;
        buttonPanel.add(btnUpdate, buttonGbc);
        buttonGbc.gridx = 0;
        buttonGbc.gridy = 1;
        buttonPanel.add(btnDelete, buttonGbc);
        buttonGbc.gridx = 1;
        buttonPanel.add(btnRefresh, buttonGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 5;
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
        // Đổi màu nền của tablePanel sang màu tím nhạt
        tablePanel.setBackground(new Color(230, 230, 250)); // Lavender
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

        tbImportOrderDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tbImportOrderDetails.getSelectedRow();
                if (row >= 0) {
                    String id = tbImportOrderDetails.getValueAt(row, 0).toString();
                    String importOrderId = tbImportOrderDetails.getValueAt(row, 1).toString();
                    String productId = tbImportOrderDetails.getValueAt(row, 2).toString();
                    String quantity = tbImportOrderDetails.getValueAt(row, 4).toString();
                    String unitPriceFormatted = tbImportOrderDetails.getValueAt(row, 5).toString();

                    txtId.setText(id);
                    txtImportOrderId.setText(importOrderId);
                    cbProductId.setSelectedItem(productId);
                    txtQuantity.setText(quantity);
                    txtUnitPrice.setText(unitPriceFormatted);
                }
            }
        });
    }

    private JPanel createHeaderPanel() {
        // Giữ màu PRIMARY_COLOR cho header hoặc thay đổi nếu muốn
        final Color PRIMARY_COLOR = new Color(70, 130, 180); 
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Chi tiết phiếu nhập");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);
        
        return panel;
    }

    private void setupLabel(JLabel label, String text) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(new Color(51, 51, 51)); // Giữ màu chữ hoặc thay đổi nếu muốn
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

    public ImportOrderDetail getImportOrderDetailInput() {
        String id = txtId.getText().trim();
        String importOrderId = txtImportOrderId.getText().trim();
        String productId = (String) cbProductId.getSelectedItem();
        String quantity = txtQuantity.getText().trim();

        if (id.isEmpty() || importOrderId.isEmpty() || productId == null || quantity.isEmpty() || txtUnitPrice.getText().isEmpty()) {
            throw new IllegalArgumentException("Vui lòng điền đầy đủ thông tin!");
        }

        try {
            int qty = Integer.parseInt(quantity);
            double price = txtUnitPrice.getCurrencyValue();
            
            if (qty <= 0 || price <= 0) {
                throw new IllegalArgumentException("Số lượng và đơn giá phải lớn hơn 0!");
            }
            
            String productName = currentProduct != null ? currentProduct.getProductName() : "";
            String unitPrice = String.valueOf(price);
            String totalPrice = String.valueOf(qty * price);
            
            return new ImportOrderDetail(id, importOrderId, productId, productName, quantity, unitPrice, totalPrice);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số lượng và đơn giá phải là số!");
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

    public void setCbProductIdActionListener(java.awt.event.ActionListener actionListener) {
        cbProductId.addActionListener(actionListener);
    }

    public void setBtnUpdateActionListener(java.awt.event.ActionListener actionListener) {
        btnUpdate.addActionListener(actionListener);
    }

    public void setCbProductId(List<String> productIdList) {
        cbProductId.removeAllItems();
        cbProductId.addItem("");
        
        for (String productId : productIdList) {
            cbProductId.addItem(productId);
        }
    }

    public void setTxtImportOrderId(String importOrderId) {
        txtImportOrderId.setText(importOrderId);
    }

    public void setDataToTable(List<ImportOrderDetail> importOrderDetailList) {
        String[] columnNames = {"Mã", "Mã đơn nhập", "Mã SP", "Tên SP", "Số lượng", "Đơn giá", "Thành tiền"};
        Object[][] data = new Object[importOrderDetailList.size()][7];

        for (int i = 0; i < importOrderDetailList.size(); i++) {
            ImportOrderDetail detail = importOrderDetailList.get(i);
            data[i][0] = detail.getId();
            data[i][1] = detail.getImportOrderId();
            data[i][2] = detail.getProductId();
            data[i][3] = detail.getProductName();
            data[i][4] = detail.getQuantity();
            data[i][5] = CurrencyFormatter.formatCurrency(detail.getUnitPrice());
            data[i][6] = CurrencyFormatter.formatCurrency(detail.getTotalPrice());
        }

        tbImportOrderDetails.setModel(new javax.swing.table.DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
    }

    public void setTxtId(int id) {
        txtId.setText(IdGenerator.generateId("IMP", id + 1));
    }

    public void clear(int id) {
        txtId.setText(IdGenerator.generateId("IMP", id + 1));
        txtQuantity.setText("");
        txtUnitPrice.setText("");
        cbProductId.setSelectedItem("");
    }

    public String getCbProductId() {
        return (String) cbProductId.getSelectedItem();
    }

    public void setProductDetail(Product p) {
        this.currentProduct = p;
    }

    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbProductId;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private ModernTable tbImportOrderDetails;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtImportOrderId;
    private javax.swing.JTextField txtQuantity;
    private CurrencyTextField txtUnitPrice;
}