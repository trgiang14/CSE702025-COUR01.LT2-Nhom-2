package com.example.qlkho.controller;

import com.example.qlkho.dao.StatisticsDao;
import com.example.qlkho.view.StatisticsView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

public class StatisticsController {
    private StatisticsView statisticsView;
    private StatisticsDao statisticsDao;
    
    public StatisticsController(StatisticsView statisticsView) {
        this.statisticsView = statisticsView;
        this.statisticsDao = new StatisticsDao();
        
        initController();
        initYearCombo();
        loadInitialData();
    }
    
    private void initController() {
        statisticsView.setRefreshButtonListener(new RefreshListener());
        
        statisticsView.setChartTypeComboListener(new ChartTypeChangeListener());
        
        statisticsView.setYearComboListener(new YearChangeListener());
    }
    
    private void initYearCombo() {
        List<String> years = statisticsDao.getAvailableYears();
        if (!years.isEmpty()) {
            DefaultComboBoxModel<String> model = new DefaultComboBoxModel<>();
            years.forEach(model::addElement);
            statisticsView.getYearCombo().setModel(model);
        }
    }
    
    private void loadInitialData() {
        String selectedYear = (String) statisticsView.getYearCombo().getSelectedItem();
        if (selectedYear != null) {
            loadOverviewData(selectedYear);
            loadInitialChart(selectedYear);
        }
    }
    
    private void loadOverviewData(String selectedYear) {
        try {
            Map<String, Object> overviewData = statisticsDao.getOverviewStatistics(selectedYear);
            statisticsView.updateOverviewData(overviewData);
        } catch (Exception e) {
            showErrorMessage("Lỗi khi tải dữ liệu tổng quan: " + e.getMessage());
        }
    }
    
    private void loadInitialChart(String selectedYear) {
        String firstChartType = (String) statisticsView.getChartTypeCombo().getItemAt(0);
        loadChartData(firstChartType, selectedYear);
    }
    
    private void loadChartData(String chartType, String selectedYear) {
        try {
            Map<String, ? extends Number> chartData = null;
            
            switch (chartType) {
                case "Sản phẩm theo danh mục":
                    chartData = statisticsDao.getProductCountByCategory();
                    break;
                case "Sản phẩm theo nhà cung cấp":
                    chartData = statisticsDao.getProductCountBySupplier();
                    break;
                case "Giá trị theo danh mục":
                    chartData = statisticsDao.getValueByCategory();
                    break;
                case "Doanh thu theo tháng":
                    chartData = statisticsDao.getRevenueByMonth(selectedYear);
                    break;
                case "Chi phí nhập hàng theo tháng":
                    chartData = statisticsDao.getImportCostByMonth(selectedYear);
                    break;
                case "Top 5 sản phẩm":
                    chartData = statisticsDao.getTop5ProductsByQuantity();
                    break;
            }
            
            if (chartData != null && !chartData.isEmpty()) {
                statisticsView.updateChart(chartType, chartData);
            } else {
                showInfoMessage("Không có dữ liệu để hiển thị cho loại biểu đồ này.");
            }
            
        } catch (Exception e) {
            showErrorMessage("Lỗi khi tải dữ liệu biểu đồ: " + e.getMessage());
        }
    }
    
    private void refreshAllData() {
        String selectedYear = (String) statisticsView.getYearCombo().getSelectedItem();
        if (selectedYear != null) {
            loadOverviewData(selectedYear);
            String selectedChartType = (String) statisticsView.getChartTypeCombo().getSelectedItem();
            if (selectedChartType != null) {
                loadChartData(selectedChartType, selectedYear);
            }
        }
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            statisticsView,
            message,
            "Lỗi",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    private void showInfoMessage(String message) {
        JOptionPane.showMessageDialog(
            statisticsView,
            message,
            "Thông tin",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
    
    class RefreshListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshAllData();
        }
    }
    
    class ChartTypeChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String selectedChartType = (String) statisticsView.getChartTypeCombo().getSelectedItem();
            String selectedYear = (String) statisticsView.getYearCombo().getSelectedItem();
            if (selectedChartType != null && selectedYear != null) {
                loadChartData(selectedChartType, selectedYear);
            }
        }
    }
    
    class YearChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            refreshAllData();
        }
    }
} 