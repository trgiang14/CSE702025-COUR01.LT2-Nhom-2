package com.example.qlkho.controller;

import com.example.qlkho.dao.OrderDetailDao;
import com.example.qlkho.dao.ProductDao;
import com.example.qlkho.entity.OrderDetail;
import com.example.qlkho.entity.Product;
import com.example.qlkho.view.OrderDetailView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderDetailController {
    private final OrderDetailView orderDetailView;
    private final OrderDetailDao orderDetailDao;
    private final ProductDao productDao;
    private String id;

    public OrderDetailController(OrderDetailView orderDetailView, String id) {
        this.orderDetailView = orderDetailView;
        this.orderDetailDao = new OrderDetailDao();
        this.productDao = new ProductDao();
        this.id = id;
        

        List<String> allProductIds = productDao.getAllProductsId();
        
        List<String> addedProductIds = orderDetailDao.getAddedProductIds(id);
        
        List<String> availableProductIds = new ArrayList<>();
        for (String productId : allProductIds) {
            if (!addedProductIds.contains(productId)) {
                availableProductIds.add(productId);
            }
        }
        
        orderDetailView.setDataToTable(orderDetailDao.getProductByOrderId(id));
        orderDetailView.setCbProductId(availableProductIds);
        orderDetailView.setTxtOrderId(this.id);
        orderDetailView.setTxtId(orderDetailDao.getOrderDetailList().size());

        orderDetailView.setBtnAddActionListener(new AddOrderDetailListener());
        orderDetailView.setBtnDeleteActionListener(new DeleteOrderDetailListener());
        orderDetailView.setBtnRefreshActionListener(new RefreshOrderDetailListener());
        orderDetailView.setCbProductIdActionListener(new CbProductIdListener());
        orderDetailView.setBtnUpdateActionListener(new UpdateDetailListener());
    }

    public void showFrm() {
        orderDetailView.setVisible(true);
    }

    class AddOrderDetailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                OrderDetail orderDetail = orderDetailView.getOrderDetailInput();
                Product product = productDao.getProductById(orderDetail.getProductId());
                
                if (product != null) {
                    int availableQuantity = Integer.parseInt(product.getQuantity());
                    int requestedQuantity = Integer.parseInt(orderDetail.getProductQuantity());
                    
                    if (requestedQuantity <= 0) {
                        JOptionPane.showMessageDialog(orderDetailView, 
                            "Số lượng xuất phải lớn hơn 0",
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    if (requestedQuantity > availableQuantity) {
                        JOptionPane.showMessageDialog(orderDetailView, 
                            "Số lượng sản phẩm trong kho không đủ.\nSố lượng còn lại: " + availableQuantity,
                            "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    
                    // Add the order detail (this will also decrease the product quantity)
                    orderDetailDao.add(orderDetail);
                    
                    // Refresh data with latest from database
                    List<OrderDetail> updatedList = orderDetailDao.getProductByOrderId(id);
                    orderDetailView.setDataToTable(updatedList);
                    orderDetailView.clear(orderDetailDao.getOrderDetailList().size());
                    
                    JOptionPane.showMessageDialog(orderDetailView, 
                        String.format("Thêm thành công!\nĐã trừ %d sản phẩm từ kho\nSố lượng còn lại: %d", 
                            requestedQuantity, 
                            availableQuantity - requestedQuantity),
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(orderDetailView, 
                    ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }


    class DeleteOrderDetailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           try{
               Optional<OrderDetail> orderDetailInput = Optional.ofNullable(orderDetailView.getOrderDetailInput());
                if(orderDetailInput.isPresent()){
                     int result = JOptionPane.showConfirmDialog(orderDetailView, "Bạn có chắc chắn muốn xóa không?");
                     if(result == JOptionPane.YES_OPTION){
                          orderDetailDao.delete(orderDetailInput.get());
                          JOptionPane.showMessageDialog(orderDetailView, "Xóa thành công");
                          orderDetailView.setDataToTable(orderDetailDao.getProductByOrderId(id));
                          orderDetailView.clear(orderDetailDao.getOrderDetailList().size());
                     }
                }

           }catch (IllegalArgumentException ex){
               JOptionPane.showMessageDialog(orderDetailView, ex.getMessage());
           }
        }
    }

    class RefreshOrderDetailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            List<String> allProductIds = productDao.getAllProductsId();
            
            List<String> addedProductIds = orderDetailDao.getAddedProductIds(id);
            
            List<String> availableProductIds = new ArrayList<>();
            for (String productId : allProductIds) {
                if (!addedProductIds.contains(productId)) {
                    availableProductIds.add(productId);
                }
            }
            
            orderDetailView.clear(orderDetailDao.getOrderDetailList().size());
            orderDetailView.setCbProductId(availableProductIds);
            orderDetailView.setDataToTable(orderDetailDao.getProductByOrderId(id));
        }
    }

    class UpdateDetailListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Optional<OrderDetail> orderDetailInput = Optional.ofNullable(orderDetailView.getOrderDetailInput());

                if(orderDetailInput.isPresent()){
                    orderDetailDao.update(orderDetailInput.get());
                    JOptionPane.showMessageDialog(orderDetailView, "Cập nhật thành công");
                    orderDetailView.setDataToTable(orderDetailDao.getProductByOrderId(id));
                    orderDetailView.clear(orderDetailDao.getOrderDetailList().size());
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(orderDetailView, ex.getMessage());
            }
        }
    }

    class CbProductIdListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                String productId = orderDetailView.getCbProductId();
                Product p = productDao.getProductById(productId);
                if (p != null) {
                    orderDetailView.setProductDetail(p);
                }
            } catch (Exception ex) {
            }
        }
    }
}
