package com.example.qlkho.view.components;

import javax.swing.*;
import java.awt.*;

public class GradientPanel extends JPanel {
    private Color color1 = new Color(66, 139, 202);
    private Color color2 = new Color(51, 51, 51);
    private boolean horizontal;

    public GradientPanel() {
        this(false);
    }

    public GradientPanel(boolean horizontal) {
        this.horizontal = horizontal;
        setOpaque(false);
    }

    public void setGradientColors(Color color1, Color color2) {
        this.color1 = color1;
        this.color2 = color2;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        int w = getWidth();
        int h = getHeight();
        
        GradientPaint gp;
        if (horizontal) {
            gp = new GradientPaint(0, 0, color1, w, 0, color2);
        } else {
            gp = new GradientPaint(0, 0, color1, 0, h, color2);
        }
        
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
        g2d.dispose();
    }
} 