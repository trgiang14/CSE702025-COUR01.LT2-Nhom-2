package com.example.qlkho.controller;

import com.example.qlkho.dao.ProductDao;
import com.example.qlkho.entity.Product;
import com.example.qlkho.view.ProductView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

public class ProductController {
    private final ProductView productView;
    private final ProductDao productDao;

    public ProductController(ProductView productView) {
        this.productView = productView;

        productDao = new ProductDao();

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

    class AddProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Optional<Product> productInput = Optional.ofNullable(productView.getProductInput());

                if (productInput.isPresent()) {
                    try {
                        productDao.addProduct(productInput.get());
                        JOptionPane.showMessageDialog(productView, "Thêm sản phẩm thành công.");
                        productView.setDataToTable(productDao.getProducts());
                        productView.clear(productDao.getProducts().size());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(productView, ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(productView, "Vui lòng nhập thông tin sản phẩm.");
                }
            }catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(productView, ex.getMessage());
            }
        }
    }

    class UpdateProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {

            try {
                Optional<Product> productInput = Optional.ofNullable(productView.getProductInput());
                Optional<Product> productSelected = Optional.ofNullable(productView.getProductSelected());
                if (productInput.isPresent() && productSelected.isPresent()) {
                    try {
                        productDao.updateProduct(productSelected.get().getId(), productInput.get());
                        JOptionPane.showMessageDialog(productView, "Cập nhật sản phẩm thành công.");
                        productView.setDataToTable(productDao.getProducts());
                        productView.clear(productDao.getProducts().size());
                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(productView, ex.getMessage());
                    }
                } else {
                    JOptionPane.showMessageDialog(productView, "Vui lòng chọn sản phẩm cần cập nhật.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(productView, ex.getMessage());

            }
        }
    }

    class DeleteProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
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

    class RefreshProductListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            productView.clear(productDao.getProducts().size());
            productView.setDataToTable(productDao.getProducts());
        }
    }

    class RadioButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String selectedRadioButton = productView.getRadioSelected();
            productView.setCbFilter(productDao.getListByType(selectedRadioButton));
            if (selectedRadioButton.equals("none")) {
                productView.setDataToTable(productDao.getProducts());

            }
        }
    }

    class SearchButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String keyword = productView.getSearchText();
            productView.setDataToTable(productDao.getProductsById(keyword));


        }
    }

    class ComboBoxListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            Optional<String> selectedComboBox = productView.getFilter();

            selectedComboBox.ifPresent(string -> productView.setDataToTable(productDao.getProductBy(string)));
        }
    }
}
