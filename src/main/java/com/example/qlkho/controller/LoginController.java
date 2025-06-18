package com.example.qlkho.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Optional;

import com.example.qlkho.dao.UserDao;
import com.example.qlkho.entity.User;
import com.example.qlkho.view.Login;
import com.example.qlkho.view.MainMenuView;

import javax.swing.*;

public class LoginController {
    private final UserDao userDao;
    private final Login loginView;


    public LoginController(Login view) {
        this.loginView = view;
        this.userDao = new UserDao();
        view.addLoginListener(new LoginListener());
    }

    public void showLoginView() {
        loginView.setVisible(true);
    }


    class LoginListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try {
                Optional<User> getUser = Optional.ofNullable(loginView.getUserInput());
                if (getUser.isPresent()) {
                    User user = getUser.get();
                    if(userDao.checkUser(user)){
                        JOptionPane.showMessageDialog(loginView, "Đăng nhập thành công!");
                        MainMenuView mainMenuView = new MainMenuView();
                        MainMenuController mainMenuController = new MainMenuController(mainMenuView, user.getUsername());
                        mainMenuController.showMainMenuView();
                        loginView.dispose();
                    } else {
                        JOptionPane.showMessageDialog(loginView, "Tài khoản hoặc mật khẩu không đúng!");
                    }
                }
            } catch (IllegalArgumentException ex){
                JOptionPane.showMessageDialog(loginView, ex.getMessage());
            }
        }
    }
}
