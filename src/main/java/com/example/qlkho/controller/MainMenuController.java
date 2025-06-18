package com.example.qlkho.controller;

import com.example.qlkho.view.ImportOrderView;
import com.example.qlkho.view.Login;
import com.example.qlkho.view.MainMenuView;
import com.example.qlkho.view.OrderView;
import com.example.qlkho.view.ProductView;
import com.example.qlkho.view.StatisticsView;
import com.example.qlkho.entity.User;


import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainMenuController {
    private final MainMenuView mainMenuView;
    private ProductView productView;
    private OrderView orderView;
    private ImportOrderView importOrderView;
    private StatisticsView statisticsView;
    private ProductController productController;
    private OrderController orderController;
    private ImportOrderController importOrderController;
    private StatisticsController statisticsController;
    private final User currentUser;

    public MainMenuController(MainMenuView mainMenuView, String userName,User currentUser) {
        this.mainMenuView = mainMenuView;
        this.currentUser = currentUser;
        
        mainMenuView.setUserName(userName);

        mainMenuView.setBtnProduct(new ProductListener());
        mainMenuView.setBtnExport(new ExporterListener());
        mainMenuView.setBtnImport(new ImportListener());
        mainMenuView.setBtnStatistics(new StatisticsListener());
        mainMenuView.setBtnLogout(new LogoutListener());

        showProductView();
    }

    public void showMainMenuView() {
        mainMenuView.setVisible(true);
    }

    private void showProductView() {
        productView = new ProductView();
        productController = new ProductController(productView,currentUser);
        mainMenuView.setContent(productView);
    }

    private void showOrderView() {
        orderView = new OrderView();
        orderController = new OrderController(orderView);
        mainMenuView.setContent(orderView);
    }

    private void showImportOrderView() {
        importOrderView = new ImportOrderView();
        importOrderController = new ImportOrderController(importOrderView);
        mainMenuView.setContent(importOrderView);
    }

    class LogoutListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int result = JOptionPane.showConfirmDialog(mainMenuView, 
                "Bạn có chắc chắn muốn đăng xuất không?", 
                "Xác nhận", 
                JOptionPane.YES_NO_OPTION);
                
            if(result != JOptionPane.YES_OPTION) {
                return;
            }

            LoginController loginController = new LoginController(new Login());
            loginController.showLoginView();
            mainMenuView.dispose();
        }
    }

    class ProductListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showProductView();
        }
    }

    class ExporterListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showOrderView();
        }
    }

    class ImportListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showImportOrderView();
        }
    }

    class StatisticsListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            showStatisticsView();
        }
    }

    private void showStatisticsView() {
        statisticsView = new StatisticsView();
        statisticsController = new StatisticsController(statisticsView);
        mainMenuView.setContent(statisticsView);
    }
} 