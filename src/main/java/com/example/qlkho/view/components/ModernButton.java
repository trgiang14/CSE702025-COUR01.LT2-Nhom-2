package com.example.qlkho.view.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ModernButton extends JButton {
    private Color defaultBackground = new Color(180, 160, 220);
    private Color hoverBackground = new Color(150, 130, 200);
    private Color pressedBackground = new Color(120, 100, 170);
    private boolean isMainAction = false;

    public ModernButton(String text) {
        this(text, false);
    }

    public ModernButton(String text, boolean isMainAction) {
        super(text);
        this.isMainAction = isMainAction;
        if (!isMainAction) {
            defaultBackground = new Color(200, 180, 230);
            hoverBackground = new Color(180, 160, 220);
            pressedBackground = new Color(160, 140, 210);
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
                    if (contains(e.getPoint())) {
                        setBackground(hoverBackground);
                    } else {
                        setBackground(defaultBackground);
                    }
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