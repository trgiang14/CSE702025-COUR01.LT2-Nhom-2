package com.example.qlkho.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class MainMenuView extends javax.swing.JFrame {

    private final Color MENU_BG = new Color(204, 179, 255);     
    private final Color SELECTED_BG = new Color(124, 77, 255);  
    private final Color HOVER_BG = new Color(178, 153, 255);     
    private JButton selectedButton;

    public MainMenuView() {
        initComponents();
        setupMenuButtons();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        mainPanel = new javax.swing.JPanel();
        menuPanel = new javax.swing.JPanel();
        contentPanel = new javax.swing.JPanel();

        btnProduct = new javax.swing.JButton();
        btnExport = new javax.swing.JButton();
        btnImport = new javax.swing.JButton();
        btnStatistics = new javax.swing.JButton();
        btnLogout = new javax.swing.JButton();
        lblUserInfo = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Quản lý kho");
        setResizable(false);
        setPreferredSize(new Dimension(1200, 700));

        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(255, 255, 255));

        menuPanel.setLayout(new GridBagLayout());
        menuPanel.setBackground(MENU_BG);
        menuPanel.setPreferredSize(new Dimension(200, 700));
        GridBagConstraints menuGbc = new GridBagConstraints();
        menuGbc.gridx = 0;
        menuGbc.fill = GridBagConstraints.HORIZONTAL;
        menuGbc.insets = new Insets(5, 10, 5, 10);

        lblUserInfo.setText("Xin chào, Admin");
        lblUserInfo.setForeground(Color.WHITE);
        lblUserInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        menuGbc.gridy = 0;
        menuGbc.insets = new Insets(20, 10, 30, 10);
        menuPanel.add(lblUserInfo, menuGbc);

        btnProduct.setText("Quản lý sản phẩm");
        btnProduct.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnProduct.setForeground(Color.WHITE);
        btnProduct.setBackground(MENU_BG);
        btnProduct.setBorderPainted(false);
        btnProduct.setFocusPainted(false);
        btnProduct.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnProduct.setHorizontalAlignment(SwingConstants.LEFT);
        btnProduct.setPreferredSize(new Dimension(180, 40));
        menuGbc.gridy = 1;
        menuGbc.insets = new Insets(5, 10, 5, 10);
        menuPanel.add(btnProduct, menuGbc);

        btnExport.setText("Xuất sản phẩm");
        btnExport.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnExport.setForeground(Color.WHITE);
        btnExport.setBackground(MENU_BG);
        btnExport.setBorderPainted(false);
        btnExport.setFocusPainted(false);
        btnExport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExport.setHorizontalAlignment(SwingConstants.LEFT);
        btnExport.setPreferredSize(new Dimension(180, 40));
        menuGbc.gridy = 2;
        menuPanel.add(btnExport, menuGbc);

        btnImport.setText("Nhập sản phẩm");
        btnImport.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnImport.setForeground(Color.WHITE);
        btnImport.setBackground(MENU_BG);
        btnImport.setBorderPainted(false);
        btnImport.setFocusPainted(false);
        btnImport.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnImport.setHorizontalAlignment(SwingConstants.LEFT);
        btnImport.setPreferredSize(new Dimension(180, 40));
        menuGbc.gridy = 3;
        menuPanel.add(btnImport, menuGbc);

        btnStatistics.setText("Thống kê");
        btnStatistics.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnStatistics.setForeground(Color.WHITE);
        btnStatistics.setBackground(MENU_BG);
        btnStatistics.setBorderPainted(false);
        btnStatistics.setFocusPainted(false);
        btnStatistics.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnStatistics.setHorizontalAlignment(SwingConstants.LEFT);
        btnStatistics.setPreferredSize(new Dimension(180, 40));
        menuGbc.gridy = 4;
        menuGbc.weighty = 0.0;
        menuGbc.anchor = GridBagConstraints.NORTH;
        menuGbc.insets = new Insets(5, 10, 5, 10);
        menuPanel.add(btnStatistics, menuGbc);

        btnLogout.setText("Đăng xuất");
        btnLogout.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBackground(MENU_BG);
        btnLogout.setBorderPainted(false);
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogout.setHorizontalAlignment(SwingConstants.LEFT);
        btnLogout.setPreferredSize(new Dimension(180, 40));
        menuGbc.gridy = 5;
        menuGbc.weighty = 1.0;
        menuGbc.anchor = GridBagConstraints.SOUTH;
        menuGbc.insets = new Insets(5, 10, 20, 10);
        menuPanel.add(btnLogout, menuGbc);

        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(new Color(240, 240, 240));

        mainPanel.add(menuPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        getContentPane().add(mainPanel);

        pack();
        setLocationRelativeTo(null);
    }

    private void setupMenuButtons() {
        styleMenuButton(btnProduct);
        styleMenuButton(btnExport);
        styleMenuButton(btnImport);
        styleMenuButton(btnStatistics);
        styleMenuButton(btnLogout);

        setSelectedButton(btnProduct);
    }

    private void styleMenuButton(JButton button) {
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(MENU_BG);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setPreferredSize(new Dimension(180, 40));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if (button != selectedButton) {
                    button.setBackground(HOVER_BG);
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != selectedButton) {
                    button.setBackground(MENU_BG);
                }
            }
        });
    }

    private void setSelectedButton(JButton button) {
        if (selectedButton != null) {
            selectedButton.setBackground(MENU_BG);
        }

        selectedButton = button;
        selectedButton.setBackground(SELECTED_BG);
    }

    public void setContent(JPanel panel) {
        contentPanel.removeAll();
        contentPanel.add(panel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public void setBtnProduct(ActionListener actionListener) {
        btnProduct.addActionListener(e -> {
            setSelectedButton(btnProduct);
            actionListener.actionPerformed(e);
        });
    }

    public void setBtnExport(ActionListener actionListener) {
        btnExport.addActionListener(e -> {
            setSelectedButton(btnExport);
            actionListener.actionPerformed(e);
        });
    }

    public void setBtnImport(ActionListener actionListener) {
        btnImport.addActionListener(e -> {
            setSelectedButton(btnImport);
            actionListener.actionPerformed(e);
        });
    }

    public void setBtnStatistics(ActionListener actionListener) {
        btnStatistics.addActionListener(e -> {
            setSelectedButton(btnStatistics);
            actionListener.actionPerformed(e);
        });
    }

    public void setBtnLogout(ActionListener actionListener) {
        btnLogout.addActionListener(e -> {
            setSelectedButton(btnLogout);
            actionListener.actionPerformed(e);
        });
    }

    public void setUserName(String userName) {
        lblUserInfo.setText("Xin chào, " + userName);
    }

    private javax.swing.JPanel mainPanel;
    private javax.swing.JPanel menuPanel;
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton btnProduct;
    private javax.swing.JButton btnExport;
    private javax.swing.JButton btnImport;
    private javax.swing.JButton btnStatistics;
    private javax.swing.JButton btnLogout;
    private javax.swing.JLabel lblUserInfo;
}
