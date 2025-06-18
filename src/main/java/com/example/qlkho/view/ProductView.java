
package com.example.qlkho.view;

import com.example.qlkho.entity.Product;
import com.example.qlkho.utils.CalendarUtils;
import com.example.qlkho.utils.IdGenerator;
import com.example.qlkho.utils.CurrencyFormatter;
import com.example.qlkho.view.components.*;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;


public class ProductView extends JPanel {
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private ActionListener radioButtonListener;

 
    public ProductView() {
        initComponents();

        dpExpired.setDateFormatString("dd-MM-yyyy");
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(rCategory);
        buttonGroup.add(rSupplie);
        buttonGroup.add(rNone);

        rNone.setSelected(true);

        cbFilter.removeAllItems();

        txtProductId.setEditable(false);

        CalendarUtils.disableDateChooserTextEditing(dpExpired);
    }

    public void setRadioButtonListener(ActionListener actionListener) {
        this.radioButtonListener = actionListener;
        rCategory.addActionListener(radioButtonListener);
        rSupplie.addActionListener(radioButtonListener);
        rNone.addActionListener(radioButtonListener);
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(240, 240, 240));
        
        jLabel1 = new JLabel();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        jLabel5 = new JLabel();
        jLabel6 = new JLabel();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        jLabel9 = new JLabel();
        jLabel10 = new JLabel();
        
        txtProductId = new ModernTextField("Nhập mã sản phẩm");
        txtProductName = new ModernTextField("Nhập tên sản phẩm");
        txtProductCategory = new ModernTextField("Nhập loại sản phẩm");
        txtSupplier = new ModernTextField("Nhập nhà cung cấp");
        txtQuantity = new ModernTextField("Nhập số lượng");
        txtPrice = new CurrencyTextField("Nhập giá");
        txtSearch = new ModernTextField("Tìm kiếm sản phẩm");
        
        dpExpired = new JDateChooser();
        dpExpired.setPreferredSize(new Dimension(200, 35));
        dpExpired.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        btnAdd = new ModernButton("Thêm sản phẩm", true);
        btnUpdate = new ModernButton("Cập nhật");
        btnDelete = new ModernButton("Xoá");
        btnRefresh = new ModernButton("Làm mới");
        btnSearch = new ModernButton("Tìm kiếm");
        
        cbFilter = new JComboBox<>();
        cbFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cbFilter.setPreferredSize(new Dimension(200, 35));
        
        rCategory = new JRadioButton();
        rNone = new JRadioButton();
        rSupplie = new JRadioButton();
        
        tbProducts = new ModernTable();
        jScrollPane1 = new JScrollPane(tbProducts);

        String[] columnNames = {"Mã SP", "Tên SP", "Loại SP", "Nhà cung cấp", "Hạn sử dụng", "Số lượng", "Giá"};
        tbProducts.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][] {},
            columnNames
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });

        tbProducts.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = tbProducts.getSelectedRow();
                if (row >= 0) {
                    txtProductId.setText(tbProducts.getValueAt(row, 0).toString());
                    txtProductName.setText(tbProducts.getValueAt(row, 1).toString());
                    txtProductCategory.setText(tbProducts.getValueAt(row, 2).toString());
                    txtSupplier.setText(tbProducts.getValueAt(row, 3).toString());
                    try {
                        java.util.Date date = new java.text.SimpleDateFormat("dd-MM-yyyy").parse(tbProducts.getValueAt(row, 4).toString());
                        dpExpired.setDate(date);
                    } catch (Exception e) {
                        dpExpired.setDate(null);
                    }
                    txtQuantity.setText(tbProducts.getValueAt(row, 5).toString());
                    txtPrice.setCurrencyValue(CurrencyFormatter.parseCurrency(tbProducts.getValueAt(row, 6).toString()));
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.anchor = GridBagConstraints.WEST;
        formGbc.insets = new Insets(5, 5, 5, 5);

        setupLabel(jLabel1, "Mã sản phẩm:");
        setupLabel(jLabel2, "Tên sản phẩm:");
        setupLabel(jLabel3, "Loại sản phẩm:");
        setupLabel(jLabel4, "Nhà cung cấp:");
        setupLabel(jLabel5, "Hạn sử dụng:");
        setupLabel(jLabel6, "Số lượng:");
        setupLabel(jLabel7, "Giá:");

        addFormRow(formPanel, formGbc, jLabel1, txtProductId, 0);
        addFormRow(formPanel, formGbc, jLabel2, txtProductName, 1);
        addFormRow(formPanel, formGbc, jLabel3, txtProductCategory, 2);
        addFormRow(formPanel, formGbc, jLabel4, txtSupplier, 3);
        addFormRow(formPanel, formGbc, jLabel5, dpExpired, 4);
        addFormRow(formPanel, formGbc, jLabel6, txtQuantity, 5);
        addFormRow(formPanel, formGbc, jLabel7, txtPrice, 6);

        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints buttonGbc = new GridBagConstraints();
        buttonGbc.insets = new Insets(5, 5, 5, 5);

        buttonPanel.add(btnAdd, buttonGbc);
        buttonGbc.gridx = 1;
        buttonPanel.add(btnUpdate, buttonGbc);
        buttonGbc.gridx = 0;
        buttonPanel.add(btnDelete, buttonGbc);
        buttonGbc.gridx = 1;
        buttonPanel.add(btnRefresh, buttonGbc);

        formGbc.gridx = 0;
        formGbc.gridy = 7;
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

        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(new Color(240, 240, 240));
        GridBagConstraints searchGbc = new GridBagConstraints();
        searchGbc.insets = new Insets(0, 5, 0, 5);

        txtSearch.setPreferredSize(new Dimension(400, 35));
        searchGbc.gridx = 0;
        searchGbc.gridy = 0;
        searchGbc.fill = GridBagConstraints.HORIZONTAL;
        searchGbc.weightx = 1.0;
        searchPanel.add(txtSearch, searchGbc);

        btnSearch.setPreferredSize(new Dimension(100, 35));
        searchGbc.gridx = 1;
        searchGbc.weightx = 0.0;
        searchGbc.fill = GridBagConstraints.NONE;
        searchPanel.add(btnSearch, searchGbc);

        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        filterPanel.setBackground(new Color(240, 240, 240));

        rNone = new JRadioButton("Không lọc");
        rCategory = new JRadioButton("Loại sản phẩm");
        rSupplie = new JRadioButton("Nhà cung cấp");

        Font radioFont = new Font("Segoe UI", Font.PLAIN, 14);
        rNone.setFont(radioFont);
        rCategory.setFont(radioFont);
        rSupplie.setFont(radioFont);
        rNone.setBackground(new Color(240, 240, 240));
        rCategory.setBackground(new Color(240, 240, 240));
        rSupplie.setBackground(new Color(240, 240, 240));

        ButtonGroup filterGroup = new ButtonGroup();
        filterGroup.add(rNone);
        filterGroup.add(rCategory);
        filterGroup.add(rSupplie);
        rNone.setSelected(true);

        filterPanel.add(rNone);
        filterPanel.add(rCategory);
        filterPanel.add(rSupplie);

        cbFilter = new JComboBox<>();
        cbFilter.setPreferredSize(new Dimension(200, 35));
        cbFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        filterPanel.add(cbFilter);

        searchGbc.gridx = 0;
        searchGbc.gridy = 1;
        searchGbc.gridwidth = 2;
        searchGbc.fill = GridBagConstraints.HORIZONTAL;
        searchGbc.insets = new Insets(10, 5, 0, 5);
        searchPanel.add(filterPanel, searchGbc);

        tableGbc.gridx = 0;
        tableGbc.gridy = 0;
        tableGbc.fill = GridBagConstraints.HORIZONTAL;
        tableGbc.weightx = 1.0;
        tableGbc.insets = new Insets(0, 0, 10, 0);
        tablePanel.add(searchPanel, tableGbc);

        jScrollPane1.setPreferredSize(new Dimension(600, 500));
        tableGbc.gridy = 1;
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
        
        JLabel titleLabel = new JLabel("Quản lý sản phẩm");
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

    private void tbProductsMouseClicked(java.awt.event.MouseEvent evt) {
        Product product = getProductSelected();
        if (product == null) {
            return;
        }

        txtProductId.setText(product.getId());
        txtProductName.setText(product.getProductName());
        txtProductCategory.setText(product.getProductCategory());
        txtSupplier.setText(product.getProductSupplier());
        dpExpired.setDate(CalendarUtils.convertStringToCalendar(product.getProductExpireDate()).getTime());
        txtQuantity.setText(product.getQuantity());
        txtPrice.setCurrencyValue(product.getPrice());
    }

    public void setDataToTable(List<Product> products) {
        DefaultTableModel model = (DefaultTableModel) tbProducts.getModel();
        model.setRowCount(0);
        for (Product product : products) {
            model.addRow(new Object[]{
                product.getId(), 
                product.getProductName(), 
                product.getProductCategory(), 
                product.getProductSupplier(), 
                product.getProductExpireDate(), 
                product.getQuantity(), 
                CurrencyFormatter.formatCurrency(product.getPrice())
            });
        }
    }

    public Product getProductSelected() {
        int row = tbProducts.getSelectedRow();
        if (row == -1) {
            return null;
        }

        String id = tbProducts.getValueAt(row, 0).toString();
        String productName = tbProducts.getValueAt(row, 1).toString();
        String productCategory = tbProducts.getValueAt(row, 2).toString();
        String productSupplier = tbProducts.getValueAt(row, 3).toString();
        String expireDate = tbProducts.getValueAt(row, 4).toString();
        String quantity = tbProducts.getValueAt(row, 5).toString();
        String priceFormatted = tbProducts.getValueAt(row, 6).toString();
        String price = String.valueOf(CurrencyFormatter.parseCurrency(priceFormatted));

        Calendar calendar = CalendarUtils.convertStringToCalendar(expireDate);
        return new Product(id, productName, productCategory, productSupplier, calendar, quantity, price);
    }

    public Product getProductInput() {
        String id = txtProductId.getText();
        String productName = txtProductName.getText();
        String productCategory = txtProductCategory.getText();
        String productSupplier = txtSupplier.getText();
        String quantity = txtQuantity.getText();
        String expireDate;

        if (id.isEmpty() || productName.isEmpty() || productCategory.isEmpty() || productSupplier.isEmpty() || quantity.isEmpty() || txtPrice.getText().isEmpty()) {
            return null;
        }

        try {
            int quantityInt = Integer.parseInt(quantity);
            double priceDouble = txtPrice.getCurrencyValue();

            if (quantityInt < 0 || priceDouble < 0) {
                throw new IllegalArgumentException("Số lượng và giá sản phẩm phải lớn hơn 0.");
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Số lượng và giá sản phẩm phải là số.");
        }

        try {
            expireDate = dateFormat.format(dpExpired.getDate());
        } catch (Exception e) {
            return null;
        }

        String price = String.valueOf(txtPrice.getCurrencyValue());
        Calendar calendar = CalendarUtils.convertStringToCalendar(expireDate);
        return new Product(id, productName, productCategory, productSupplier, calendar, quantity, price);
    }

    public void setBtnAddActionListener(java.awt.event.ActionListener actionListener) {
        btnAdd.addActionListener(actionListener);
    }

    public void setBtnUpdateActionListener(java.awt.event.ActionListener actionListener) {
        btnUpdate.addActionListener(actionListener);
    }

    public void setBtnDeleteActionListener(java.awt.event.ActionListener actionListener) {
        btnDelete.addActionListener(actionListener);
    }

    public void setBtnRefreshActionListener(java.awt.event.ActionListener actionListener) {
        btnRefresh.addActionListener(actionListener);
    }

    public void setBtnSearchActionListener(java.awt.event.ActionListener actionListener) {
        btnSearch.addActionListener(actionListener);
    }

    public void setCbFilterActionListener(java.awt.event.ActionListener actionListener) {
        cbFilter.addActionListener(actionListener);
    }

    public void setProductId(int id) {
        txtProductId.setText(IdGenerator.generateProductId());
    }

    public String getSearchText() {
        return txtSearch.getText().toLowerCase().trim();
    }

    public Optional<String> getFilter() {
        if (cbFilter.getSelectedIndex() == -1) {
            return Optional.empty();
        }
        return Optional.of(cbFilter.getSelectedItem().toString());
    }

    public String getRadioSelected() {
        if (rCategory.isSelected()) {
            return "category";
        } else if (rSupplie.isSelected()) {
            return "supplier";
        } else {
            return "none";
        }
    }

    public void setCbFilter(List<String> listFilter) {
        cbFilter.removeAllItems();
        for (String filter : listFilter) {
            cbFilter.addItem(filter);
        }
    }


    public void clear(int id) {
        txtProductId.setText(IdGenerator.generateProductId());
        txtProductName.setText("");
        txtProductCategory.setText("");
        txtSupplier.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
        dpExpired.setDate(null);
        cbFilter.setSelectedIndex(-1);
        rNone.setSelected(true);
        tbProducts.clearSelection();
    }

    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnRefresh;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JComboBox<String> cbFilter;
    private com.toedter.calendar.JDateChooser dpExpired;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rCategory;
    private javax.swing.JRadioButton rNone;
    private javax.swing.JRadioButton rSupplie;
    private javax.swing.JTable tbProducts;
    private javax.swing.JTextField txtProductCategory;
    private javax.swing.JTextField txtProductId;
    private javax.swing.JTextField txtProductName;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSupplier;
    private CurrencyTextField txtPrice;
}
