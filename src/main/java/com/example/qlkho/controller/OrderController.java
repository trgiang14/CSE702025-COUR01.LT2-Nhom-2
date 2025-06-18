package com.example.qlkho.controller;

import com.example.qlkho.dao.OrderDao;
import com.example.qlkho.dao.OrderDetailDao;
import com.example.qlkho.entity.Order;
import com.example.qlkho.entity.OrderDetail;
import com.example.qlkho.utils.ExportUtil;
import com.example.qlkho.view.OrderDetailView;
import com.example.qlkho.view.OrderView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class OrderController {
    private final OrderView orderView;
    private final OrderDao orderDao;
    private final OrderDetailDao orderDetailDao;

    public OrderController(OrderView orderView) {
        this.orderView = orderView;

        orderDao = new OrderDao();
        orderDetailDao = new OrderDetailDao();

        orderView.setBtnAddActionListener(new AddButtonListener());
        orderView.setBtnDeleteActionListener(new DeleteButtonListener());
        orderView.setBtnRefreshActionListener(new RefreshButtonListener());
        orderView.setDetailActionListener(new DetailButtonListener());
        orderView.setBtnExportActionListener(new ExportListener());

        orderView.setDataToTable(orderDao.getOrders());
        orderView.setId(orderDao.getOrders().size());
    }


    public void showFrm() {
        orderView.setVisible(true);
    }

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Optional<Order> orderInput = Optional.ofNullable(orderView.getOrderInput());

                if(orderInput.isPresent()){
                    orderDao.addOrder(orderInput.get());
                    JOptionPane.showMessageDialog(orderView, "Thêm mới thành công");
                    orderView.clear(orderDao.getOrders().size());
                    orderView.setDataToTable(orderDao.getOrders());
                }

            } catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(orderView, ex.getMessage());
            }
        }
    }

    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           try {
               Optional<Order> orderInput = Optional.ofNullable(orderView.getOrderInput());

               if(orderInput.isPresent()) {
                   int result = JOptionPane.showConfirmDialog(orderView, 
                       "Bạn có chắc chắn muốn xóa phiếu xuất này không?",
                       "Xác nhận xóa",
                       JOptionPane.YES_NO_OPTION,
                       JOptionPane.WARNING_MESSAGE);
                       
                   if (result == JOptionPane.YES_OPTION) {
                       orderDao.deleteOrder(orderInput.get());
                       JOptionPane.showMessageDialog(orderView, "Xóa thành công");
                       orderView.setDataToTable(orderDao.getOrders());
                       orderView.clear(orderDao.getOrders().size());
                   }
               } else {
                   JOptionPane.showMessageDialog(orderView, "Vui lòng chọn phiếu xuất cần xóa");
               }
           } catch (IllegalArgumentException ex) {
               JOptionPane.showMessageDialog(orderView, ex.getMessage());
           }
        }
    }

    class RefreshButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            orderView.clear(orderDao.getOrders().size());
            orderView.setDataToTable(orderDao.getOrders());
        }
    }

    class DetailButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                Optional<Order> orderInput = Optional.ofNullable(orderView.getOrderInput());

                if(orderInput.isPresent()){
                    OrderDetailController orderDetailController = new OrderDetailController(new OrderDetailView(), orderInput.get().getId());
                    orderDetailController.showFrm();
                }
            }catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(orderView, "Vui lòng chọn đơn hàng cần xem chi tiết");
            }
        }
    }

    class ExportListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Optional<Order> orderInput = Optional.ofNullable(orderView.getOrderInput());

                if(orderInput.isPresent()){
                    List<OrderDetail> list = orderDetailDao.getProductByOrderId(orderInput.get().getId());
                    if(list.isEmpty() || list == null) {
                        JOptionPane.showMessageDialog(orderView, "Đơn hàng không có sản phẩm");
                    } else{
                        ExportUtil.export(list);
                    }
                }
            }catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(orderView, "Vui lòng chọn đơn hàng cần xuất file");
            }
        }
    }

}
