package com.example.qlkho.view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.Map;

public class StatisticsView extends JPanel {
    private final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    private final Color CARD_COLOR = Color.WHITE;
    
    private JPanel overviewPanel;
    private JPanel chartPanel;
    private JComboBox<String> chartTypeCombo;
    private JComboBox<String> yearCombo;
    private JButton refreshButton;
    
    private JLabel lblTotalProducts;
    private JLabel lblTotalQuantity;
    private JLabel lblTotalValue;
    private JLabel lblTotalOrders;
    private JLabel lblTotalRevenue;
    
    public StatisticsView() {
        initComponents();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BACKGROUND_COLOR);
        
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        overviewPanel = createOverviewPanel();
        add(overviewPanel, BorderLayout.CENTER);
        
        chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(PRIMARY_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        
        JLabel titleLabel = new JLabel("Thống kê & Báo cáo");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);
        
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.setBackground(PRIMARY_COLOR);
        
        JLabel lblYear = new JLabel("Năm:");
        lblYear.setForeground(Color.WHITE);
        lblYear.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        controlPanel.add(lblYear);
        
        yearCombo = new JComboBox<>();
        yearCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        yearCombo.setPreferredSize(new Dimension(100, 30));
        controlPanel.add(yearCombo);
        
        JLabel lblChartType = new JLabel("Loại biểu đồ:");
        lblChartType.setForeground(Color.WHITE);
        lblChartType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        controlPanel.add(lblChartType);
        
        chartTypeCombo = new JComboBox<>(new String[]{
            "Sản phẩm theo danh mục",
            "Sản phẩm theo nhà cung cấp", 
            "Giá trị theo danh mục",
            "Doanh thu theo tháng",
            "Chi phí nhập hàng theo tháng",
            "Top 5 sản phẩm"
        });
        chartTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        chartTypeCombo.setPreferredSize(new Dimension(200, 30));
        controlPanel.add(chartTypeCombo);
        
        refreshButton = new JButton("Làm mới");
        refreshButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        refreshButton.setBackground(Color.WHITE);
        refreshButton.setForeground(PRIMARY_COLOR);
        refreshButton.setBorderPainted(false);
        refreshButton.setFocusPainted(false);
        refreshButton.setPreferredSize(new Dimension(80, 30));
        refreshButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        controlPanel.add(refreshButton);
        
        panel.add(controlPanel, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createOverviewPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 5, 10, 10));
        panel.setBackground(BACKGROUND_COLOR);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        
        panel.add(createOverviewCard("Tổng sản phẩm", "0", "items", PRIMARY_COLOR, 
                                   lblTotalProducts = new JLabel()));
        
        panel.add(createOverviewCard("Tồn kho", "0", "units", new Color(46, 204, 113), 
                                   lblTotalQuantity = new JLabel()));
        
        panel.add(createOverviewCard("Giá trị kho", "0 VND", "", new Color(241, 196, 15), 
                                   lblTotalValue = new JLabel()));
        
        panel.add(createOverviewCard("Đơn hàng", "0", "orders", new Color(155, 89, 182), 
                                   lblTotalOrders = new JLabel()));
        
        panel.add(createOverviewCard("Doanh thu", "0 VND", "", new Color(231, 76, 60), 
                                   lblTotalRevenue = new JLabel()));
        
        return panel;
    }
    
    private JPanel createOverviewCard(String title, String value, String unit, Color color, JLabel valueLabel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JPanel iconPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        iconPanel.setBackground(CARD_COLOR);
        JLabel iconLabel = new JLabel("●");
        iconLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        iconLabel.setForeground(color);
        iconPanel.add(iconLabel);
        
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(CARD_COLOR);
        
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        titleLabel.setForeground(new Color(100, 100, 100));
        contentPanel.add(titleLabel, BorderLayout.NORTH);
        
        valueLabel.setText(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        valueLabel.setForeground(new Color(50, 50, 50));
        contentPanel.add(valueLabel, BorderLayout.CENTER);
        
        if (!unit.isEmpty()) {
            JLabel unitLabel = new JLabel(unit);
            unitLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            unitLabel.setForeground(new Color(150, 150, 150));
            contentPanel.add(unitLabel, BorderLayout.SOUTH);
        }
        
        card.add(iconPanel, BorderLayout.WEST);
        card.add(contentPanel, BorderLayout.CENTER);
        
        return card;
    }
    
    private JPanel createChartPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(CARD_COLOR);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                "Biểu đồ thống kê",
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(70, 70, 70)
            ),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setPreferredSize(new Dimension(800, 400));
        
        JLabel emptyLabel = new JLabel("Chọn loại biểu đồ để xem thống kê", SwingConstants.CENTER);
        emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
        emptyLabel.setForeground(new Color(150, 150, 150));
        panel.add(emptyLabel, BorderLayout.CENTER);
        
        return panel;
    }
    
    public void updateOverviewData(Map<String, Object> data) {
        DecimalFormat numberFormat = new DecimalFormat("#,###");
        
        lblTotalProducts.setText(String.valueOf(data.get("totalProducts")));
        lblTotalQuantity.setText(numberFormat.format(data.get("totalQuantity")));
        lblTotalValue.setText(formatCurrency((Double) data.get("totalValue")));
        lblTotalOrders.setText(String.valueOf(data.get("totalOrders")));
        lblTotalRevenue.setText(formatCurrency((Double) data.get("totalRevenue")));
    }
    
   
    private String formatCurrency(double amount) {
        if (amount >= 1_000_000_000_000L) {
            double value = amount / 1_000_000_000_000L;
            if (value == (long) value) {
                return String.format("%.0f nghìn tỷ", value);
            } else {
                return String.format("%.1f nghìn tỷ", value);
            }
        } else if (amount >= 1_000_000_000) {
            double value = amount / 1_000_000_000;
            if (value == (long) value) {
                return String.format("%.0f tỷ", value);
            } else {
                return String.format("%.1f tỷ", value);
            }
        } else if (amount >= 1_000_000) {
            double value = amount / 1_000_000;
            if (value == (long) value) {
                return String.format("%.0f triệu", value);
            } else {
                return String.format("%.1f triệu", value);
            }
        } else if (amount >= 1_000) {
            double value = amount / 1_000;
            if (value == (long) value) {
                return String.format("%.0f nghìn", value);
            } else {
                return String.format("%.1f nghìn", value);
            }
        } else {
            DecimalFormat format = new DecimalFormat("#,###");
            return format.format(amount);
        }
    }
    
    public void updateChart(String chartType, Map<String, ? extends Number> data) {
        chartPanel.removeAll();
        
        JFreeChart chart = null;
        
        switch (chartType) {
            case "Sản phẩm theo danh mục":
            case "Sản phẩm theo nhà cung cấp":
            case "Top 5 sản phẩm":
                chart = createPieChart(chartType, data);
                break;
            case "Giá trị theo danh mục":
            case "Doanh thu theo tháng":
            case "Chi phí nhập hàng theo tháng":
                chart = createBarChart(chartType, data);
                break;
        }
        
        if (chart != null) {
            ChartPanel chartPanelComponent = new ChartPanel(chart);
            chartPanelComponent.setPreferredSize(new Dimension(750, 350));
            chartPanel.add(chartPanelComponent, BorderLayout.CENTER);
        }
        
        chartPanel.revalidate();
        chartPanel.repaint();
    }
    
    private JFreeChart createPieChart(String title, Map<String, ? extends Number> data) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        
        for (Map.Entry<String, ? extends Number> entry : data.entrySet()) {
            dataset.setValue(entry.getKey(), entry.getValue());
        }
        
        JFreeChart chart = ChartFactory.createPieChart(
            title,
            dataset,
            true,
            true,
            false
        );
        
        chart.setBackgroundPaint(Color.WHITE);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setOutlineVisible(false);
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        
        return chart;
    }
    
    private JFreeChart createBarChart(String title, Map<String, ? extends Number> data) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
        for (Map.Entry<String, ? extends Number> entry : data.entrySet()) {
            dataset.addValue(entry.getValue(), "Giá trị", entry.getKey());
        }
        
        JFreeChart chart = ChartFactory.createBarChart(
            title,
            "Danh mục",
            "Giá trị",
            dataset,
            PlotOrientation.VERTICAL,
            true,
            true,
            false
        );
        
        chart.setBackgroundPaint(Color.WHITE);
        CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinesVisible(false);
        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(new Color(220, 220, 220));
        
        CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        domainAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
        
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setLabelFont(new Font("Segoe UI", Font.PLAIN, 11));
        rangeAxis.setTickLabelFont(new Font("Segoe UI", Font.PLAIN, 10));
        
        return chart;
    }
    
    public JComboBox<String> getChartTypeCombo() {
        return chartTypeCombo;
    }
    
    public void setRefreshButtonListener(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }
    
    public void setChartTypeComboListener(ActionListener listener) {
        chartTypeCombo.addActionListener(listener);
    }
    
    public JComboBox<String> getYearCombo() {
        return yearCombo;
    }
    
    public void setYearComboListener(ActionListener listener) {
        yearCombo.addActionListener(listener);
    }
} 