
package com.example.qlkho.view;

import com.example.qlkho.entity.OrderDetail;
import com.example.qlkho.entity.Product;
import com.example.qlkho.utils.IdGenerator;
import com.example.qlkho.utils.CurrencyFormatter;
import com.example.qlkho.view.components.*;

import java.util.List;
import java.util.Objects;
import java.awt.*;
import javax.swing.*;


public class OrderDetailView extends JPanel {
    private JFrame frame;
    private Product currentProduct;

    public OrderDetailView() {
        frame = new JFrame("Chi tiết phiếu xuất");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        
        initComponents();
        
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);

        txtOrderId.setEditable(false);
        txtId.setEditable(false);
    }

    public void setVisible(boolean visible) {
        frame.setVisible(visible);
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
        jLabel5 = new JLabel();
        
        txtId = new ModernTextField();
        txtOrderId = new ModernTextField();
        txtQuantity = new ModernTextField("Nhập số lượng");
        
        cbProductId = new JComboBox<>();
        cbProductId.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbProductId.setPreferredSize(new Dimension(200, 35));
        
        btnAdd = new ModernButton("Thêm", true);
        btnUpdate = new ModernButton("Cập nhật");
        btnDelete = new ModernButton("Xoá");
        btnRefresh = new ModernButton("Làm mới");
        
        tbOrderDetails = new ModernTable();
        jScrollPane1 = new JScrollPane(tbOrderDetails);

        tbOrderDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tbOrderDetails.getSelectedRow();
                if (row >= 0) {
                    String id = tbOrderDetails.getValueAt(row, 0).toString();
                    String orderId = tbOrderDetails.getValueAt(row, 1).toString();
                    String productId = tbOrderDetails.getValueAt(row, 2).toString();
                    String quantity = tbOrderDetails.getValueAt(row, 5).toString();

                    txtId.setText(id);
                    txtOrderId.setText(orderId);
                    cbProductId.setSelectedItem(productId);
                    txtQuantity.setText(quantity);
                }
            }
        });

        setupLabel(jLabel2, "Mã:");
        setupLabel(jLabel3, "Mã đơn hàng:");
        setupLabel(jLabel4, "Mã sản phẩm:");
        setupLabel(jLabel5, "Số lượng:");

        addFormRow(formPanel, formGbc, jLabel2, txtId, 0);
        addFormRow(formPanel, formGbc, jLabel3, txtOrderId, 1);
        addFormRow(formPanel, formGbc, jLabel4, cbProductId, 2);
        addFormRow(formPanel, formGbc, jLabel5, txtQuantity, 3);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));
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
        formGbc.gridy = 4;
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
        
        JLabel titleLabel = new JLabel("Chi tiết phiếu xuất");
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

    public OrderDetail getOrderDetailInput() {
        String id = txtId.getText();
        String orderId = txtOrderId.getText();
        String productId = Objects.requireNonNull(cbProductId.getSelectedItem()).toString();
        String quantity = txtQuantity.getText();

        if (id.isEmpty() || orderId.isEmpty() || productId.isEmpty() || quantity.isEmpty()) {
            throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin");
        }

        try {
            Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Số lượng xuất phải là số");
        }

        if (currentProduct != null) {
            return new OrderDetail(id, orderId, productId, currentProduct.getProductName(), currentProduct.getPrice(), quantity);
        } else {
            throw new IllegalArgumentException("Vui lòng chọn sản phẩm");
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
        for (String productId : productIdList) {
            cbProductId.addItem(productId);
        }

        cbProductId.setSelectedIndex(-1);

    }

    public void setTxtOrderId(String orderId) {
        txtOrderId.setText(orderId);
    }

    public void setDataToTable(List<OrderDetail> orderDetailList) {
        javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel();
        model.addColumn("Mã");
        model.addColumn("Mã đơn hàng");
        model.addColumn("Mã sản phẩm");
        model.addColumn("Tên sản phẩm");
        model.addColumn("Giá sản phẩm");
        model.addColumn("Số lượng xuất");

        while (tbOrderDetails.getRowCount() > 0) {
            ((javax.swing.table.DefaultTableModel) tbOrderDetails.getModel()).removeRow(0);
        }

        if (orderDetailList != null && !orderDetailList.isEmpty()) {
            for (OrderDetail orderDetail : orderDetailList) {
                model.addRow(new Object[]{
                    orderDetail.getId(), 
                    orderDetail.getOrderId(), 
                    orderDetail.getProductId(), 
                    orderDetail.getProductName(), 
                    CurrencyFormatter.formatCurrency(orderDetail.getProductPrice()), 
                    orderDetail.getProductQuantity()
                });
            }
        }
        
        tbOrderDetails.setModel(model);
        tbOrderDetails.revalidate();
        tbOrderDetails.repaint();
    }

    public void setTxtId(int id) {
        txtId.setText(IdGenerator.generateOrderDetailId());
    }

    public void clear(int id) {
        txtId.setText(IdGenerator.generateOrderDetailId());
        txtQuantity.setText("");
        tbOrderDetails.clearSelection();
        cbProductId.setSelectedIndex(-1);
    }
    public String getCbProductId() {
        return Objects.requireNonNull(cbProductId.getSelectedItem()).toString();
    }

    public void setProductDetail(Product p) {
        this.currentProduct = p;
    }

    public String getRemaining() {
        return "0"; 
    }

    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbProductId;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private ModernTable tbOrderDetails;
    private javax.swing.JTextField txtId;
    private javax.swing.JTextField txtOrderId;
    private javax.swing.JTextField txtQuantity;
}
