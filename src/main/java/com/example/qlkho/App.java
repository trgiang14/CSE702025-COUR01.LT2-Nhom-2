package com.example.qlkho;

import com.example.qlkho.controller.LoginController;
import com.example.qlkho.view.Login;
import com.example.qlkho.view.ProductView;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ProductView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            Login view = new Login();
            LoginController controller = new LoginController(view);
            controller.showLoginView();
        });
    }
}
