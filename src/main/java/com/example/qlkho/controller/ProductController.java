package com.example.qlkho.controller;

import com.example.qlkho.dao.ProductDao;
import com.example.qlkho.entity.Product;
import com.example.qlkho.entity.User;
import com.example.qlkho.view.ProductView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class ProductController {
    private final ProductView productView;
    private final ProductDao productDao;
    private final User currentUser;

    public ProductController(ProductView productView, User user) {
        this.productView = productView;
        this.currentUser = user;
        this.productDao = new ProductDao();

        System.out.println("Current user role: " + currentUser.getRole());


        // Gán các action listener cho nút
        productView.setDataToTable(productDao.getProducts());
        productView.setBtnAddActionListener(new AddProductListener());
        productView.setBtnUpdateActionListener(new UpdateProductListener());
        productView.setBtnDeleteActionListener(new DeleteProductListener());
        productView.setBtnRefreshActionListener(new RefreshProductListener());
        productView.setRadioButtonListener(new RadioButtonListener());
        productView.setBtnSearchActionListener(new SearchButtonListener());
        productView.setCbFilterActionListener(new ComboBoxListener());

        productView.setProductId(productDao.getProducts().size());
    }

    public void showFrm() {
        productView.setVisible(true);
    }

    // Thêm sản phẩm
    class AddProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Optional<Product> productInput = Optional.ofNullable(productView.getProductInput());

                if (productInput.isPresent()) {
                    productDao.addProduct(productInput.get());
                    JOptionPane.showMessageDialog(productView, "Thêm sản phẩm thành công.");
                    productView.setDataToTable(productDao.getProducts());
                    productView.clear(productDao.getProducts().size());
                } else {
                    JOptionPane.showMessageDialog(productView, "Vui lòng nhập thông tin sản phẩm.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(productView, ex.getMessage());
            }
        }
    }

    // Cập nhật sản phẩm
    class UpdateProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Optional<Product> productInput = Optional.ofNullable(productView.getProductInput());
                Optional<Product> productSelected = Optional.ofNullable(productView.getProductSelected());

                if (productInput.isPresent() && productSelected.isPresent()) {
                    productDao.updateProduct(productSelected.get().getId(), productInput.get());
                    JOptionPane.showMessageDialog(productView, "Cập nhật sản phẩm thành công.");
                    productView.setDataToTable(productDao.getProducts());
                    productView.clear(productDao.getProducts().size());
                } else {
                    JOptionPane.showMessageDialog(productView, "Vui lòng chọn sản phẩm cần cập nhật.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(productView, ex.getMessage());
            }
        }
    }

    // Xóa sản phẩm (chỉ admin)
    class DeleteProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
          if (!"admin".equalsIgnoreCase(currentUser.getRole())) {
             JOptionPane.showMessageDialog(productView, "Bạn không có quyền xóa sản phẩm.");
              return;
              }



            Optional<Product> productInput = Optional.ofNullable(productView.getProductInput());

            if (productInput.isPresent()) {
                productDao.deleteProduct(productInput.get());
                JOptionPane.showMessageDialog(productView, "Xóa sản phẩm thành công.");
                productView.setDataToTable(productDao.getProducts());
                productView.clear(productDao.getProducts().size());
            } else {
                JOptionPane.showMessageDialog(productView, "Vui lòng chọn sản phẩm cần xóa.");
            }
        }
    }

    // Làm mới
    class RefreshProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            productView.clear(productDao.getProducts().size());
            productView.setDataToTable(productDao.getProducts());
        }
    }

    // Lọc theo loại
    class RadioButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedRadioButton = productView.getRadioSelected();
            productView.setCbFilter(productDao.getListByType(selectedRadioButton));
            if ("none".equals(selectedRadioButton)) {
                productView.setDataToTable(productDao.getProducts());
            }
        }
    }

    // Tìm kiếm
    class SearchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String keyword = productView.getSearchText();
            productView.setDataToTable(productDao.getProductsById(keyword));
        }
    }

    // Lọc theo combobox
    class ComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Optional<String> selectedComboBox = productView.getFilter();
            selectedComboBox.ifPresent(value -> productView.setDataToTable(productDao.getProductBy(value)));
        }
    }
}
