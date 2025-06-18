package com.example.qlkho.controller;

import com.example.qlkho.dao.ImportOrderDao;
import com.example.qlkho.dao.ImportOrderDetailDao;
import com.example.qlkho.entity.ImportOrder;
import com.example.qlkho.entity.ImportOrderDetail;
import com.example.qlkho.utils.ExportUtil;
import com.example.qlkho.view.ImportOrderDetailView;
import com.example.qlkho.view.ImportOrderView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Optional;

public class ImportOrderController {
    private final ImportOrderView importOrderView;
    private final ImportOrderDao importOrderDao;
    private final ImportOrderDetailDao importOrderDetailDao;

    public ImportOrderController(ImportOrderView importOrderView) {
        this.importOrderView = importOrderView;

        importOrderDao = new ImportOrderDao();
        importOrderDetailDao = new ImportOrderDetailDao();

        importOrderView.setBtnAddActionListener(new AddButtonListener());
        importOrderView.setBtnDeleteActionListener(new DeleteButtonListener());
        importOrderView.setBtnRefreshActionListener(new RefreshButtonListener());
        importOrderView.setDetailActionListener(new DetailButtonListener());
        importOrderView.setBtnImportActionListener(new ImportListener());

        importOrderView.setDataToTable(importOrderDao.getImportOrders());
        importOrderView.setId(importOrderDao.getImportOrders().size());
    }

    public void showFrm() {
        importOrderView.setVisible(true);
    }

    class AddButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Optional<ImportOrder> importOrderInput = Optional.ofNullable(importOrderView.getImportOrderInput());

                if(importOrderInput.isPresent()){
                    importOrderDao.addImportOrder(importOrderInput.get());
                    JOptionPane.showMessageDialog(importOrderView, "Thêm mới thành công");
                    importOrderView.clear(importOrderDao.getImportOrders().size());
                    importOrderView.setDataToTable(importOrderDao.getImportOrders());
                }

            } catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(importOrderView, ex.getMessage());
            }
        }
    }

    class DeleteButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
           try {
               Optional<ImportOrder> importOrderInput = Optional.ofNullable(importOrderView.getImportOrderInput());

               if(importOrderInput.isPresent()) {
                   int result = JOptionPane.showConfirmDialog(importOrderView, 
                       "Bạn có chắc chắn muốn xóa phiếu nhập này không?",
                       "Xác nhận xóa",
                       JOptionPane.YES_NO_OPTION,
                       JOptionPane.WARNING_MESSAGE);
                       
                   if (result == JOptionPane.YES_OPTION) {
                       importOrderDao.deleteImportOrder(importOrderInput.get());
                       JOptionPane.showMessageDialog(importOrderView, "Xóa thành công");
                       importOrderView.setDataToTable(importOrderDao.getImportOrders());
                       importOrderView.clear(importOrderDao.getImportOrders().size());
                   }
               } else {
                   JOptionPane.showMessageDialog(importOrderView, "Vui lòng chọn phiếu nhập cần xóa");
               }
           } catch (IllegalArgumentException ex) {
               JOptionPane.showMessageDialog(importOrderView, ex.getMessage());
           }
        }
    }

    class RefreshButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            importOrderView.clear(importOrderDao.getImportOrders().size());
            importOrderView.setDataToTable(importOrderDao.getImportOrders());
        }
    }

    class DetailButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try{
                Optional<ImportOrder> importOrderInput = Optional.ofNullable(importOrderView.getImportOrderInput());

                if(importOrderInput.isPresent()){
                    ImportOrderDetailController importOrderDetailController = new ImportOrderDetailController(new ImportOrderDetailView(), importOrderInput.get().getId());
                    importOrderDetailController.showFrm();
                }
            }catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(importOrderView, "Vui lòng chọn đơn nhập hàng cần xem chi tiết");
            }
        }
    }

    class ImportListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Optional<ImportOrder> importOrderInput = Optional.ofNullable(importOrderView.getImportOrderInput());

                if(importOrderInput.isPresent()){
                    List<ImportOrderDetail> list = importOrderDetailDao.getProductByImportOrderId(importOrderInput.get().getId());
                    if(list.isEmpty() || list == null) {
                        JOptionPane.showMessageDialog(importOrderView, "Đơn nhập hàng không có sản phẩm");
                    } else{
                        ExportUtil.exportImport(list);
                    }
                }
            }catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(importOrderView, "Vui lòng chọn đơn nhập hàng cần xuất file");
            }
        }
    }

} 