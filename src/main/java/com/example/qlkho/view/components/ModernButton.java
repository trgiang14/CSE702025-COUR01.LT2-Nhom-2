package com.example.qlkho.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModernButton extends JButton {
    private Color defaultBackground = new Color(66, 139, 202);
    private Color hoverBackground = new Color(51, 122, 183);
    private Color pressedBackground = new Color(40, 96, 144);
    private boolean isMainAction = false;

    public ModernButton(String text) {
        this(text, false);
    }

    public ModernButton(String text, boolean isMainAction) {
        super(text);
        this.isMainAction = isMainAction;
        if (!isMainAction) {
            defaultBackground = new Color(108, 117, 125);
            hoverBackground = new Color(90, 98, 104);
            pressedBackground = new Color(73, 80, 87);
        }
        setupButton();
    }

    private void setupButton() {
        setFont(new Font("Segoe UI", Font.BOLD, 13));
        setForeground(Color.WHITE);
        setBackground(defaultBackground);
        setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setPreferredSize(new Dimension(130, 35));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(hoverBackground);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(defaultBackground);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(pressedBackground);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isEnabled()) {
                    setBackground(hoverBackground);
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isPressed()) {
            g2.setColor(pressedBackground);
        } else if (getModel().isRollover()) {
            g2.setColor(hoverBackground);
        } else {
            g2.setColor(getBackground());
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 8, 8);
        g2.dispose();

        super.paintComponent(g);
    }
} 