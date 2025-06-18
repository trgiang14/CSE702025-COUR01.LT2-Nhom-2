package com.example.qlkho.dao;

import com.example.qlkho.entity.ImportOrder;
import com.example.qlkho.entity.ImportOrderDetail;
import com.example.qlkho.entity.XML.ImportOrderXML;
import com.example.qlkho.utils.FileUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ImportOrderDao {
    private final String FILE_PATH = "src/main/resources/importOrders.xml";

    private List<ImportOrder> importOrders;

    private ImportOrderDetailDao importOrderDetailDao;
    private ProductDao productDao;
    
    public ImportOrderDao() {
        importOrders = readXML();
        if (importOrders == null) {
            importOrders = new ArrayList<>();
        }
        System.out.println(importOrders);
    }

    public List<ImportOrder> getImportOrders() {
        if (importOrders != null && !importOrders.isEmpty()) {
            importOrders.clear();
            importOrders = readXML();
        }

        return importOrders;
    }

    private List<ImportOrder> readXML() {
        List<ImportOrder> result = new ArrayList<>();

        ImportOrderXML importOrderXML = (ImportOrderXML) FileUtils.readXMLFile(FILE_PATH, ImportOrderXML.class);

        if (importOrderXML != null) {
            result = importOrderXML.getImportOrderList();
        }

        return result;
    }

    public void writeXML() {
        ImportOrderXML importOrderXML = new ImportOrderXML();
        importOrderXML.setImportOrderList(importOrders);
        FileUtils.writeXMLtoFile(FILE_PATH, importOrderXML);
    }

    private boolean checkDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try {
            LocalDate importDate = LocalDate.parse(date, formatter);

            LocalDate currentDate = LocalDate.now();

            return importDate.isAfter(currentDate);
        } catch (Exception e) {
            System.out.println("Invalid date format: " + e.getMessage());
            return false;
        }
    }

    private boolean isImportOrderExist(ImportOrder importOrder) {
        return importOrders.stream().anyMatch(o -> Objects.equals(o.getId(), importOrder.getId()));
    }

    public void addImportOrder(ImportOrder importOrder) {
        if (!checkDate(importOrder.getImportDate())) {
            throw new IllegalArgumentException("Ngày nhập hàng phải sau ngày hiện tại!");
        }

        if (isImportOrderExist(importOrder)) {
            throw new IllegalArgumentException("Mã đơn nhập hàng đã tồn tại!");
        }

        importOrders.add(importOrder);
        writeXML();
    }

    public void updateImportOrder(ImportOrder importOrder) {
        if (!checkDate(importOrder.getImportDate())) {
            throw new IllegalArgumentException("Ngày nhập hàng phải sau ngày hiện tại!");
        }

        for (ImportOrder o : importOrders) {
            if (Objects.equals(o.getId(), importOrder.getId())) {
                importOrders.set(importOrders.indexOf(o), importOrder);
            }
        }

        writeXML();
    }

    public void deleteImportOrder(ImportOrder importOrder) {
        importOrderDetailDao = new ImportOrderDetailDao();
        productDao = new ProductDao();
        
        List<ImportOrderDetail> detailsToDelete = importOrderDetailDao.getProductByImportOrderId(importOrder.getId());
        
        for (ImportOrderDetail detail : detailsToDelete) {
            try {
                int quantityToDecrease = Integer.parseInt(detail.getQuantity());
                productDao.decreaseProductQuantity(detail.getProductId(), quantityToDecrease);
            } catch (IllegalArgumentException e) {
                System.out.println("Warning: Could not decrease quantity for product " + detail.getProductId() + ": " + e.getMessage());
            }
        }
        
        importOrders.removeIf(order -> Objects.equals(order.getId(), importOrder.getId()));
        writeXML();
        
        importOrderDetailDao.deleteImportOrderDetailByImportOrderId(importOrder.getId());
    }
} 