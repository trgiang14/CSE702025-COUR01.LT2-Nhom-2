package com.example.qlkho.utils;

import com.example.qlkho.entity.ImportOrderDetail;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.List;

public class ExportUtil {

    public static <T> void export(List<T> list) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Excel File");
        fileChooser.setSelectedFile(new File("data.xlsx"));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try {
                exportToExcel(list, filePath);
                JOptionPane.showMessageDialog(null, "Xuất file thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Xuất file thất bại!");
            }
        }
    }

    public static void exportImport(List<ImportOrderDetail> list) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Import Report Excel File");
        fileChooser.setSelectedFile(new File("import_report.xlsx"));

        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel Files", "xlsx");
        fileChooser.setFileFilter(filter);

        int userSelection = fileChooser.showSaveDialog(null);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.endsWith(".xlsx")) {
                filePath += ".xlsx";
            }

            try {
                exportImportToExcel(list, filePath);
                JOptionPane.showMessageDialog(null, "Xuất báo cáo nhập hàng thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Xuất báo cáo nhập hàng thất bại!");
            }
        }
    }

    public static <T> void exportToExcel(List<T> dataList, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        try {
            Sheet sheet = workbook.createSheet("Data");

            if (!dataList.isEmpty()) {
                T firstObject = dataList.get(0);

                Row headerRow = sheet.createRow(0);
                Field[] fields = firstObject.getClass().getDeclaredFields();
                for (int i = 0; i < fields.length; i++) {
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(fields[i].getName());
                }

                for (int rowIndex = 0; rowIndex < dataList.size(); rowIndex++) {
                    Row dataRow = sheet.createRow(rowIndex + 1);
                    T obj = dataList.get(rowIndex);
                    for (int cellIndex = 0; cellIndex < fields.length; cellIndex++) {
                        Field field = fields[cellIndex];
                        field.setAccessible(true);
                        Object value = field.get(obj);
                        Cell cell = dataRow.createCell(cellIndex);
                        if (value != null) {
                            if (value instanceof String) {
                                cell.setCellValue((String) value);
                            } else if (value instanceof Number) {
                                cell.setCellValue(((Number) value).doubleValue());
                            } else if (value instanceof Boolean) {
                                cell.setCellValue((Boolean) value);
                            } else {
                                cell.setCellValue(value.toString());
                            }
                        }
                    }
                }
            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void exportImportToExcel(List<ImportOrderDetail> dataList, String filePath) {
        Workbook workbook = new XSSFWorkbook();
        try {
            Sheet sheet = workbook.createSheet("Import Report");

            Row headerRow = sheet.createRow(0);
            String[] headers = {"Mã Chi Tiết", "Mã Đơn Nhập", "Mã Sản Phẩm", "Tên Sản Phẩm", "Số Lượng", "Đơn Giá", "Thành Tiền"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            for (int rowIndex = 0; rowIndex < dataList.size(); rowIndex++) {
                Row dataRow = sheet.createRow(rowIndex + 1);
                ImportOrderDetail detail = dataList.get(rowIndex);
                
                dataRow.createCell(0).setCellValue(detail.getId());
                dataRow.createCell(1).setCellValue(detail.getImportOrderId());
                dataRow.createCell(2).setCellValue(detail.getProductId());
                dataRow.createCell(3).setCellValue(detail.getProductName());
                dataRow.createCell(4).setCellValue(detail.getQuantity());
                dataRow.createCell(5).setCellValue(detail.getUnitPrice());
                dataRow.createCell(6).setCellValue(detail.getTotalPrice());
            }

            try (FileOutputStream outputStream = new FileOutputStream(filePath)) {
                workbook.write(outputStream);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
