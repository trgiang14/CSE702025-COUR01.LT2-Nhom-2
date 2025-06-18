package com.example.qlkho.view.components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class ModernTextField extends JTextField {
    private Color focusBorderColor = new Color(66, 139, 202);
    private Color defaultBorderColor = new Color(200, 200, 200);
    private String placeholder;
    private Color placeholderColor = new Color(150, 150, 150);

    public ModernTextField() {
        this("");
    }

    public ModernTextField(String placeholder) {
        super();
        this.placeholder = placeholder;
        setupTextField();
    }

    private void setupTextField() {
        setFont(new Font("Segoe UI", Font.PLAIN, 14));
        setPreferredSize(new Dimension(200, 35));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(defaultBorderColor),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(focusBorderColor),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(defaultBorderColor),
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (placeholder != null && placeholder.length() > 0 && getText().length() == 0) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g2.setColor(placeholderColor);
            g2.setFont(getFont());
            Insets insets = getInsets();
            g2.drawString(placeholder, insets.left, g.getFontMetrics().getMaxAscent() + insets.top);
            g2.dispose();
        }
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }

    public String getPlaceholder() {
        return placeholder;
    }
} 