package com.example.qlkho.controller;

import com.example.qlkho.dao.ImportOrderDetailDao;
import com.example.qlkho.dao.ProductDao;
import com.example.qlkho.entity.ImportOrderDetail;
import com.example.qlkho.entity.Product;
import com.example.qlkho.view.ImportOrderDetailView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImportOrderDetailController {
    private final ImportOrderDetailView importOrderDetailView;
    private final ImportOrderDetailDao importOrderDetailDao;
    private final ProductDao productDao;
    private String id;

    public ImportOrderDetailController(ImportOrderDetailView importOrderDetailView, String id) {
        this.importOrderDetailView = importOrderDetailView;
        this.importOrderDetailDao = new ImportOrderDetailDao();
        this.productDao = new ProductDao();
        this.id = id;
        
        List<String> allProductIds = productDao.getAllProductsId();
        
        List<String> addedProductIds = importOrderDetailDao.getAddedProductIds(id);
        
        List<String> availableProductIds = new ArrayList<>();
        for (String productId : allProductIds) {
            if (!addedProductIds.contains(productId)) {
                availableProductIds.add(productId);
            }
        }
        
        importOrderDetailView.setDataToTable(importOrderDetailDao.getProductByImportOrderId(id));
        importOrderDetailView.setCbProductId(availableProductIds);
        importOrderDetailView.setTxtImportOrderId(this.id);
        importOrderDetailView.setTxtId(importOrderDetailDao.getImportOrderDetails().size());

        importOrderDetailView.setBtnAddActionListener(new AddImportOrderDetailListener());
        importOrderDetailView.setBtnDeleteActionListener(new DeleteImportOrderDetailListener());
        importOrderDetailView.setBtnRefreshActionListener(new RefreshImportOrderDetailListener());
        importOrderDetailView.setCbProductIdActionListener(new CbProductIdListener());
        importOrderDetailView.setBtnUpdateActionListener(new UpdateDetailListener());
    }

    public void showFrm() {
        importOrderDetailView.setVisible(true);
    }

    class AddImportOrderDetailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                ImportOrderDetail importOrderDetail = importOrderDetailView.getImportOrderDetailInput();
                Product product = productDao.getProductById(importOrderDetail.getProductId());
                
                if (product != null) {
                    int requestedQuantity = Integer.parseInt(importOrderDetail.getQuantity());
                    
                    if (requestedQuantity <= 0) {
                        JOptionPane.showMessageDialog(importOrderDetailView, 
                            "Số lượng nhập phải lớn hơn 0",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    importOrderDetailDao.addImportOrderDetail(importOrderDetail);
                    
                    productDao.increaseProductQuantity(importOrderDetail.getProductId(), requestedQuantity);
                    
                    importOrderDetailDao.calculateTotalMoney(id);
                    
                    List<ImportOrderDetail> updatedList = importOrderDetailDao.getProductByImportOrderId(id);
                    importOrderDetailView.setDataToTable(updatedList);
                    importOrderDetailView.clear(importOrderDetailDao.getImportOrderDetails().size());
                    
                    JOptionPane.showMessageDialog(importOrderDetailView, 
                        String.format("Thêm thành công!\nĐã thêm %d sản phẩm vào kho\nSố lượng trong kho hiện tại: %d", 
                            requestedQuantity, 
                            Integer.parseInt(productDao.getProductById(importOrderDetail.getProductId()).getQuantity())),
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(importOrderDetailView, 
                    ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    class DeleteImportOrderDetailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           try{
               Optional<ImportOrderDetail> importOrderDetailInput = Optional.ofNullable(importOrderDetailView.getImportOrderDetailInput());
                if(importOrderDetailInput.isPresent()){
                     int result = JOptionPane.showConfirmDialog(importOrderDetailView, 
                         "Bạn có chắc chắn muốn xóa không?\nLưu ý: Số lượng sản phẩm trong kho sẽ giảm tương ứng.",
                         "Xác nhận xóa",
                         JOptionPane.YES_NO_OPTION,
                         JOptionPane.WARNING_MESSAGE);
                     if(result == JOptionPane.YES_OPTION){
                          ImportOrderDetail detail = importOrderDetailInput.get();
                          int quantityToDecrease = Integer.parseInt(detail.getQuantity());
                          
                          Product product = productDao.getProductById(detail.getProductId());
                          if (product != null && Integer.parseInt(product.getQuantity()) >= quantityToDecrease) {
                              importOrderDetailDao.deleteImportOrderDetail(detail);
                              
                              productDao.decreaseProductQuantity(detail.getProductId(), quantityToDecrease);
                              
                              importOrderDetailDao.calculateTotalMoney(id);
                              
                              JOptionPane.showMessageDialog(importOrderDetailView, 
                                  String.format("Xóa thành công!\nĐã trừ %d sản phẩm khỏi kho", quantityToDecrease));
                          } else {
                              JOptionPane.showMessageDialog(importOrderDetailView, 
                                  "Không thể xóa: Số lượng sản phẩm trong kho không đủ để trừ!",
                                  "Lỗi",
                                  JOptionPane.ERROR_MESSAGE);
                              return;
                          }
                          
                          importOrderDetailView.setDataToTable(importOrderDetailDao.getProductByImportOrderId(id));
                          importOrderDetailView.clear(importOrderDetailDao.getImportOrderDetails().size());
                     }
                }
           }catch (IllegalArgumentException ex){
               JOptionPane.showMessageDialog(importOrderDetailView, ex.getMessage());
           }
        }
    }

    class RefreshImportOrderDetailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> allProductIds = productDao.getAllProductsId();
            
            List<String> addedProductIds = importOrderDetailDao.getAddedProductIds(id);
            
            List<String> availableProductIds = new ArrayList<>();
            for (String productId : allProductIds) {
                if (!addedProductIds.contains(productId)) {
                    availableProductIds.add(productId);
                }
            }
            
            importOrderDetailView.clear(importOrderDetailDao.getImportOrderDetails().size());
            importOrderDetailView.setCbProductId(availableProductIds);
            importOrderDetailView.setDataToTable(importOrderDetailDao.getProductByImportOrderId(id));
        }
    }

    class UpdateDetailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Optional<ImportOrderDetail> importOrderDetailInput = Optional.ofNullable(importOrderDetailView.getImportOrderDetailInput());

                if(importOrderDetailInput.isPresent()){
                    ImportOrderDetail newDetail = importOrderDetailInput.get();
                    
                    List<ImportOrderDetail> existingDetails = importOrderDetailDao.getProductByImportOrderId(id);
                    ImportOrderDetail oldDetail = null;
                    for (ImportOrderDetail detail : existingDetails) {
                        if (detail.getId().equals(newDetail.getId())) {
                            oldDetail = detail;
                            break;
                        }
                    }
                    
                    if (oldDetail != null) {
                        int oldQuantity = Integer.parseInt(oldDetail.getQuantity());
                        int newQuantity = Integer.parseInt(newDetail.getQuantity());
                        int quantityDifference = newQuantity - oldQuantity;
                        
                        if (quantityDifference != 0) {
                            if (quantityDifference > 0) {
                                productDao.increaseProductQuantity(newDetail.getProductId(), quantityDifference);
                            } else {
                                Product product = productDao.getProductById(newDetail.getProductId());
                                if (product != null && Integer.parseInt(product.getQuantity()) >= Math.abs(quantityDifference)) {
                                    productDao.decreaseProductQuantity(newDetail.getProductId(), Math.abs(quantityDifference));
                                } else {
                                    JOptionPane.showMessageDialog(importOrderDetailView, 
                                        "Không thể cập nhật: Số lượng sản phẩm trong kho không đủ!",
                                        "Lỗi",
                                        JOptionPane.ERROR_MESSAGE);
                                    return;
                                }
                            }
                        }
                    }
                    
                    importOrderDetailDao.updateImportOrderDetail(newDetail);
                    importOrderDetailDao.calculateTotalMoney(id);
                    
                    JOptionPane.showMessageDialog(importOrderDetailView, "Cập nhật thành công");
                    importOrderDetailView.setDataToTable(importOrderDetailDao.getProductByImportOrderId(id));
                    importOrderDetailView.clear(importOrderDetailDao.getImportOrderDetails().size());
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(importOrderDetailView, ex.getMessage());
            }
        }
    }

    class CbProductIdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String productId = importOrderDetailView.getCbProductId();
                Product p = productDao.getProductById(productId);
                if (p != null) {
                    importOrderDetailView.setProductDetail(p);
                }
            } catch (Exception ex) {
            }
        }
    }
} 