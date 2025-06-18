package com.example.qlkho.view;

import com.example.qlkho.entity.User;

import java.awt.event.ActionListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Dimension;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Login extends javax.swing.JFrame {

    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        JPanel mainPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                int w = getWidth();
                int h = getHeight();
                // Màu tím nhạt thay cho màu xanh cũ
                Color color1 = new Color(179, 157, 219);  // tím nhạt
                Color color2 = new Color(124, 77, 255);   // tím đậm hơn
                GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, w, h);
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Đăng nhập");
        setPreferredSize(new Dimension(400, 500));

        getContentPane().add(mainPanel);

        jLabel3.setFont(new Font("Segoe UI", Font.BOLD, 28));
        jLabel3.setForeground(Color.WHITE);
        jLabel3.setText("ĐĂNG NHẬP");
        jLabel3.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(40, 20, 40, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(jLabel3, gbc);

        jLabel1.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel1.setForeground(Color.WHITE);
        jLabel1.setText("Tài khoản");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 50, 5, 50);
        mainPanel.add(jLabel1, gbc);

        txtUsername.setPreferredSize(new Dimension(300, 35));
        txtUsername.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsername.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 50, 20, 50);
        mainPanel.add(txtUsername, gbc);

        jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jLabel2.setForeground(Color.WHITE);
        jLabel2.setText("Mật khẩu");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(0, 50, 5, 50);
        mainPanel.add(jLabel2, gbc);

        txtPassword.setPreferredSize(new Dimension(300, 35));
        txtPassword.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtPassword.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200)),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 50, 30, 50);
        mainPanel.add(txtPassword, gbc);

        btnLogin.setText("ĐĂNG NHẬP");
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setBackground(new Color(179, 157, 219)); // màu tím nhạt
        btnLogin.setPreferredSize(new Dimension(300, 40));
        btnLogin.setBorder(BorderFactory.createEmptyBorder());
        btnLogin.setFocusPainted(false);
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        btnLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(124, 77, 255)); // tím đậm khi hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLogin.setBackground(new Color(179, 157, 219)); // tím nhạt khi rời chuột
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 50, 40, 50);
        mainPanel.add(btnLogin, gbc);

        addTextFieldFocusListener(txtUsername);
        addTextFieldFocusListener(txtPassword);

        pack();
        setLocationRelativeTo(null);
    }

    public User getUserInput() throws IllegalArgumentException{
        String userName = txtUsername.getText();
        String password = new String(txtPassword.getPassword());

        if(userName.isEmpty() || password.isEmpty()){
            throw new IllegalArgumentException("Vui lòng nhập đầy đủ thông tin");
        }

        return new User(userName, password);
    }

    public void addLoginListener(ActionListener listener) {
        btnLogin.addActionListener(listener);
    }

    private void addTextFieldFocusListener(javax.swing.JTextField textField) {
        textField.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(124, 77, 255)), // màu tím đậm khi focus
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200)),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
        });
    }

    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;

}
